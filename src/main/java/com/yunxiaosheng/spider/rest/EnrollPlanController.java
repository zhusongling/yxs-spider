package com.yunxiaosheng.spider.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yunxiaosheng.spider.config.RestTemplateConfig;
import com.yunxiaosheng.spider.dao.EnrollPlanDao;
import com.yunxiaosheng.spider.dto.EnrollPlan;
import com.yunxiaosheng.spider.utils.UUIDUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("enroll")
public class EnrollPlanController {

    @Autowired
    private EnrollPlanDao enrollPlanDao;

    @GetMapping("plan")
    public Map plan(
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("kldm") String kldm,
            @RequestParam("pcdm") String pcdm,
            @RequestParam("pcmc") String pcmc) {

        this.queryInfo(kldm, pcdm, pageIndex, pcmc);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", "成功");
        return map;
    }

    /**
     * 查询详情
     */
    public void queryInfo(String kldm, String pcdm, Integer pageIndex, String pcmc) {
        String uri = "https://datacenter.haeea.cn/PagePZQuery/ShowPZJH.aspx/QueryInfo";
        HttpHeaders httpHeaders = obtainHeader(uri);

        ObjectNode params = obtainObjectMapper();
        params.put("l_KLDM", kldm);
        params.put("l_PCDM", pcdm);
        params.put("l_PageIndex", pageIndex);
        params.put("l_SSID", "");
        params.put("l_YXMC", "");
        params.put("l_ZYMC", "");

        try {
            JsonNode data = obtainJsonNode(httpHeaders, params, uri);

            Integer totalCount = data.get("TotalCount").asInt(0);
            double ceil = (int) Math.ceil((double) totalCount / 20);
            Integer pageSize = (int) ceil;

            String showInfo = data.get("YX").asText();
            Document document = Jsoup.parse(showInfo);
            document.select("tr").first().remove();

            Elements elements = document.select("tr");

            for (Element element : elements) {

                Element tdyx = element.select("td[class=tdyx]").first(); // [1105] 北京大学
                Element tdyxjhrs = element.select("td[class=tdyxjhrs]").first(); // 57
                if (tdyx == null || tdyxjhrs == null) {
                    continue;
                }
                String[] yx = tdyx.text().replace("[", "").replace("]", "").split(" ");
                if (yx.length != 2) {
                    continue;
                }
                String yxdh = yx[0];
                String yxmc = yx[1];
                this.queryZYInfo(kldm, pcdm, yxdh, yxmc, pcmc);
            }
            Thread.sleep(10000);

            if (pageIndex < pageSize) {
                this.queryInfo(kldm, pcdm, pageIndex + 1, pcmc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询专业信息
     */
    public void queryZYInfo(String kldm, String pcdm, String yxdh, String yxmc, String pcmc) {

        String uri = "https://datacenter.haeea.cn/PagePZQuery/ShowPZJH.aspx/QueryZYInfo";

        HttpHeaders headers = obtainHeader(uri);
        ObjectNode params = obtainObjectMapper();
        params.put("l_KLDM", kldm);
        params.put("l_PCDM", pcdm);
        params.put("l_YXDH", yxdh);
        params.put("l_ZYMC", "");

        try {
            JsonNode jsonNode = obtainJsonNode(headers, params, uri);
            JsonNode data = jsonNode.get("ZY");

            for (JsonNode item : data) {
                String zydh = item.get("ZYDH") == null ? null : item.get("ZYDH").asText(); // 专业代号
                String zymc = item.get("ZYMC") == null ? null : item.get("ZYMC").asText(); // 专业名称
                String jhrs = item.get("JHRS") == null ? null : item.get("JHRS").asText(); // 计划人数
                String sfbz = item.get("SFBZ") == null ? null : item.get("SFBZ").asText(); // 收费标准
                String subjectCode = item.get("KLDM") == null ? null : item.get("KLDM").asText(); // 科类代码
                String subjectName = item.get("KLMC") == null ? null : item.get("KLMC").asText(); // 科类名称
                String bz = item.get("BZ") == null ? null : item.get("BZ").asText(); // 科类代码

                EnrollPlan enrollPlan = new EnrollPlan();
                enrollPlan.setId(UUIDUtil.uuid32());
                enrollPlan.setEnrollId(yxdh.trim());
                enrollPlan.setEnrollName(yxmc);
                enrollPlan.setMajorCode(zydh);
                enrollPlan.setMajorName(zymc);
                enrollPlan.setYear(2022);
                enrollPlan.setSubjectCode(subjectCode);
                enrollPlan.setSubjectName(subjectName);
                enrollPlan.setBatchCode(pcdm);
                enrollPlan.setBatchName(pcmc);
                enrollPlan.setPlanNum(jhrs == null ? null : Integer.parseInt(jhrs));
                enrollPlan.setTuition(sfbz == null ? null : Integer.parseInt(sfbz));
                enrollPlan.setRemark(bz);
                if (!kldm.equals(subjectCode)) {
                    continue;
                }
                enrollPlanDao.insertEnrollPlan(enrollPlan);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取请求header信息
     *
     * @param uri
     * @return
     */
    public HttpHeaders obtainHeader(String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Referer", uri);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        headers.set("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    public ObjectNode obtainObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.createObjectNode();
    }

    /**
     * 获取请求元素
     *
     * @param headers
     * @param params
     * @param uri
     * @return
     */
    public JsonNode obtainJsonNode(HttpHeaders headers, ObjectNode params, String uri) {
        JsonNode data = null;
        try {
            HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
            RestTemplate restTemplate = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
            String response = restTemplate.postForObject(uri, request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            data = jsonNode.get("d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
