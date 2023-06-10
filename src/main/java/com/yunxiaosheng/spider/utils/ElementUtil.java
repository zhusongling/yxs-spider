package com.yunxiaosheng.spider.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ElementUtil {
    /**
     * 解析URL获取文档
     *
     * @param url
     * @return
     */
    public static Document getDocument(String url) {
        Document document = null;
        try {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3514.0 Safari/537.36");
            document = Jsoup
                    .connect(url)
                    .headers(headerMap)
                    .userAgent("UA")
                    .validateTLSCertificates(false)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 文章子标题
     *
     * @param text
     * @return
     */
    public static Element subTitle(String text) {
        Element el_p = new Element("p");
        el_p.attr("style", "text-align: center; text-indent: 0em; line-height: 1.5em;");
        Element el_span = new Element("span");
        el_span.appendTo(el_p);
        el_span.attr("style", "font-size: 14px; font-family: arial, helvetica, sans-serif; color: rgb(127, 127, 127);");
        el_span.html(text);
        return el_p;
    }

    /**
     * 空行
     *
     * @return
     */
    public static Element blankLine() {
        Element el_p = new Element("p");
        el_p.attr("style", "text-align:justify; line-height: 1.5em;");
        Element el_span = new Element("span");
        el_span.attr("style", "font-size: 14px; font-family: arial, helvetica, sans-serif; color: rgb(0, 0, 0);");
        el_p.appendChild(el_span);
        el_span.append("<br/>");
        return el_p;
    }

    /**
     * 文章来源
     *
     * @return
     */
    public static Element articleFrom(String collegeName) {
        Element el_p = new Element("p");
        el_p.attr("style", "text-align: center; text-indent: 0em; line-height: 1.5em;");
        Element el_span = new Element("span");
        el_span.appendTo(el_p);
        el_span.attr("style", "font-size: 12px; font-family: arial, helvetica, sans-serif; color: rgb(127, 127, 127);");
        el_span.text("新闻来源：" + collegeName + "官网");
        return el_p;
    }

}
