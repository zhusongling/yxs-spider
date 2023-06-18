package com.yunxiaosheng.spider.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yunxiaosheng.spider.config.RestTemplateConfig;
import com.yunxiaosheng.spider.dao.EnrollScoreDao;
import com.yunxiaosheng.spider.dto.EnrollScore;
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
public class EnrollScoreController {

    private final static String BASE_URL = "https://datacenter.haeea.cn/PagePZQuery/ShowPZLQTJ.aspx";

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EnrollScoreDao enrollScoreDao;

    @GetMapping("score")
    public Map<String, Object> score(@RequestParam("pageIndex") int pageIndex,
                                     @RequestParam("kl") String kl,
                                     @RequestParam("pc") String pc,
                                     @RequestParam("subjectCode") String subjectCode,
                                     @RequestParam("subjectName") String subjectName,
                                     @RequestParam("batchCode") String batchCode,
                                     @RequestParam("batchName") String batchName) {
        String url = BASE_URL + "/QueryInfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        headers.set("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("Accept-Language", "zh-CN,zh;q=0.9");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Length", "82");
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Cookie", "Hm_lvt_718056a8a88d31be5b2ee94e9980b3a4=1686366582,1686386639,1686917305,1686974592; Hm_lpvt_718056a8a88d31be5b2ee94e9980b3a4=1686974602");
        headers.set("Host", "datacenter.haeea.cn");
        headers.set("Origin", "https://datacenter.haeea.cn");
        headers.set("Referer", "https://datacenter.haeea.cn/PagePZQuery/ShowPZLQTJ.aspx");
        headers.set("Sec-Ch-Ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
        headers.set("Sec-Ch-Ua-Mobile", "?0");
        headers.set("Sec-Ch-Ua-Platform", "\"Windows\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        headers.set("X-Requested-With", "XMLHttpRequest");

        ObjectNode params = this.objectMapper.createObjectNode();
        params.put("l_K", kl);
        params.put("l_P", pc);
        params.put("l_PI", pageIndex);
        params.put("l_QT", "");
        params.put("l_T", "2");
        params.put("l_Y", "2020");

        try {
            HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
            RestTemplate restTemplate = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
            String response = restTemplate.postForObject(url, request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            JsonNode data = jsonNode.get("d");
            Integer totalCount = data.get("TotalCount").asInt(0);
            double ceil = (int) Math.ceil((double) totalCount / 20);
            Integer pageSize = (int) ceil;

            String showInfo = data.get("ShowInfo").asText();

            Document document = Jsoup.parse(showInfo);

            document.select("tr").first().remove();

            Elements elements = document.select("tr");

            for (Element element : elements) {
                Elements tds = element.select("td");

                String college = tds.select("td").get(0).text();
//                String collegesubjectName = tds.select("td").get(1).text().trim();
                String planNum = tds.select("td").get(2).text().trim();
                String actualNum = tds.select("td").get(3).text().trim();
                String maxScore = tds.select("td").get(4).text().trim();
                String minScore = tds.select("td").get(5).text().trim();
                String avgScore = tds.select("td").get(6).text().trim();

                String[] collegeInfo = college.replace("[", "").replace("]", "").split(" ");
                String enrollId = null;
                String enrollName = null;
                if (collegeInfo.length != 2) {
                    enrollId = collegeInfo[0].trim();
                    enrollName = collegeInfo[2].trim();
                } else {
                    enrollId = collegeInfo[0].trim();
                    enrollName = collegeInfo[1].trim();
                }

                EnrollScore enrollScore = new EnrollScore();
                enrollScore.setId(UUIDUtil.uuid32());
                enrollScore.setEnrollId(enrollId);
                enrollScore.setEnrollName(enrollName);
                enrollScore.setYear(2020);
                enrollScore.setSubjectCode(subjectCode);
                enrollScore.setSubjectName(subjectName);
                enrollScore.setBatchCode(batchCode);
                enrollScore.setBatchName(batchName);
                enrollScore.setPlanNum(Integer.valueOf(planNum));
                enrollScore.setActualNum(Integer.valueOf(actualNum));
                enrollScore.setMaxScore(Double.valueOf(maxScore));
                enrollScore.setMinScore(Double.valueOf(minScore));
                enrollScore.setAvgScore(Double.valueOf(avgScore));
                enrollScoreDao.insertEnrollScore(enrollScore);
            }

            if (pageIndex < pageSize) {
                this.score(pageIndex + 1, kl, pc, subjectCode, subjectName, batchCode, batchName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", "成功");
        return map;
    }
}
