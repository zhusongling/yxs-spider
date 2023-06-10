package com.yunxiaosheng.spider.utils;

import com.yunxiaosheng.spider.dto.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DetailUtils {

    private static final String IMAGE_URL_PREFIX = "https://hnou.open.ha.cn";
    private static final String NONE_IMAGE_URL = "https://hnou.open.ha.cn/templates_site/zzvuit/main/img/icon_logo.svg";
    private static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public Article obtainContent(String baseUrl, String categoryId) {

        Document document = ElementUtil.getDocument(baseUrl);

        String releaseTime = document.getElementsByClass("news_show_top").select("span.info-l").text().replace("发布时间：", "").replaceAll("/", "-");

        String title = document.getElementsByClass("news-title").text();

        document.getElementsByClass("news_show_top").remove();
        Element element = document.getElementsByClass("content").first();

        String content = element.toString();

        // 替换图片路径
        content = content.replaceAll("src=\"/upload", "src=\"https://hnou.open.ha.cn/upload");
        String headImageUrl = null;
        try {
            // 获取文章中的第一张图片
            headImageUrl = element.select("img").first().attr("src");
            headImageUrl = IMAGE_URL_PREFIX +headImageUrl;
        } catch (Exception e) {
            headImageUrl = NONE_IMAGE_URL;
        }
        return obtainArticleDto(title, headImageUrl, categoryId, content, releaseTime);
    }

    public Article obtainArticleDto(String title, String imageUrl, String categoryId, String content, String textTime) {
        Date releaseTime;
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D_H_M_S);
        try {
            releaseTime = format.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
            releaseTime = new Date();
        }
        Article article = new Article();
        String articleId = UUIDUtil.uuid32();
        article.setArticleId(articleId);
        article.setCategoryId(categoryId);
        article.setTitle(title);
        article.setImageUrl(imageUrl);
        article.setReleaseTime(releaseTime);
        article.setPageView(0);
        article.setContent(content);
        article.setDelete(false);
        article.setBeginTime(releaseTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(releaseTime);//设置起时间
        cal.add(Calendar.YEAR, 2);//增加两年
        article.setEndTime(cal.getTime());
        article.setCollegeId("13565");
        return article;
    }
}
