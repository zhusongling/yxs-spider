package com.yunxiaosheng.spider.rest;

import com.yunxiaosheng.spider.dao.CollegePageDao;
import com.yunxiaosheng.spider.dao.CollegeProfileDao;
import com.yunxiaosheng.spider.dao.UndoneCollegeDao;
import com.yunxiaosheng.spider.dto.CollegeProfile;
import com.yunxiaosheng.spider.dto.UndoneCollege;
import com.yunxiaosheng.spider.utils.ElementUtil;
import com.yunxiaosheng.spider.utils.UUIDUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("demo")
public class DemoController {

    private final static String BASE_URL = "https://gaokao.chsi.com.cn";

    @Autowired
    UndoneCollegeDao undoneCollegeDao;
    @Autowired
    CollegeProfileDao collegeProfileDao;

    @Autowired
    CollegePageDao collegePageDao;

    public static String[] page() {
        String[] str = new String[]{
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-0.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-100.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-200.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-300.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-400.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-500.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-600.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-700.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-800.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-900.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-100.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1100.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1200.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1300.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1400.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1500.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1600.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1700.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1800.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-1900.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2000.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2100.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2200.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2300.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2400.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2500.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2600.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2700.dhtml",
                "https://gaokao.chsi.com.cn/zsgs/zhangcheng/listVerifedZszc--method-index,ssdm-,yxls-,xlcc-,zgsx-,yxjbz-,start-2800.dhtml"
        };
        return str;
    }

    @GetMapping("parse")
    public void parse() {
        String[] page = page();

        for (int i = 0; i < page.length; i++) {
            String url = page[i];
            System.out.println("===========" + url);
//            getContent(url);
        }
    }


    public void getContent(String url) {
        Document document = ElementUtil.getDocument(url);
        Elements elements = document.getElementsByClass("sch-item");
        for (Element element : elements) {
            String collegeName = element.getElementsByClass("sch-title").select("a").text();
            String href = element.getElementsByClass("zszc-link text-decoration-none").select("a").attr("href");
            if (element.getElementsByClass("zszc-link text-decoration-none").toString().contains(":class=\"{'no-info':true}\"")) {
                UndoneCollege undoneCollege = new UndoneCollege();
                undoneCollege.setId(UUIDUtil.uuid32());
                undoneCollege.setCollegeName(collegeName);
                undoneCollege.setHref(href);
                undoneCollegeDao.insertCollege(undoneCollege);
            } else {
                String content = details(BASE_URL + href);

                // 保存操作
                CollegeProfile collegeProfile = new CollegeProfile();
                collegeProfile.setId(UUIDUtil.uuid32());
                collegeProfile.setCollegeId(null);
                collegeProfile.setCollegeName(collegeName);
                collegeProfile.setYear(2023);
                collegeProfile.setTitle(2023 + collegeName + "招生章程");
                collegeProfile.setContent(content);
                collegeProfile.setOriginPageView(0);
                collegeProfile.setPageView(0);
                collegeProfile.setCreateTime(new Date());
                collegeProfileDao.insertCollege(collegeProfile);
            }
        }
    }


    private String details(String url) {
        Document document = ElementUtil.getDocument(url);
        String detailUrl = document.getElementsByClass("zszcdel-item").select("a").attr("href");
        return zhangcheng(BASE_URL + detailUrl);
    }

    private String zhangcheng(String url) {
        Document document = ElementUtil.getDocument(url);
        Elements containerZszc = document.getElementsByClass("content zszc-content UEditor");

        Elements table = containerZszc.select("table");
        if (table.size() > 0) {
            containerZszc.select("table").remove();
        }
        return containerZszc.toString();
    }
}
