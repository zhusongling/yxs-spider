package com.yunxiaosheng.spider.service.impl;

import com.yunxiaosheng.spider.dao.ArticleDao;
import com.yunxiaosheng.spider.dto.Article;
import com.yunxiaosheng.spider.service.SpiderService;
import com.yunxiaosheng.spider.utils.DetailUtils;
import com.yunxiaosheng.spider.utils.ElementUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsFocusImpl implements SpiderService {

    private static final String PREFIX_URL = "https://hnou.open.ha.cn";

    @Autowired
    ArticleDao articleDao;
    @Autowired
    DetailUtils detailUtils;

    @Override
    public void obtainContent(String baseUrl, String categoryId) {
        long startTime = System.currentTimeMillis();
        System.out.println("=============开始=============: " + startTime);
        getNextPage(baseUrl, categoryId);
        long endTime = System.currentTimeMillis();
        System.out.println("=============结束=============: " + endTime);
        System.out.println("=============耗时=============: " + (endTime - startTime) + " ms");
    }

    /**
     * 获取下一页
     *
     * @param url
     * @return
     */
    public void getNextPage(String url, String categoryId) {
        Document document = ElementUtil.getDocument(url);
        Elements elements = document.getElementsByClass("content-r content-r-list content-r-cc").select("li");
        for (Element element : elements) {
            String articleUrl = element.select("a").attr("href");
            Article article = this.detailUtils.obtainContent(PREFIX_URL + articleUrl, categoryId);
            this.articleDao.insertSelective(article);
        }
        String nextPage = document.getElementsByClass("page_list").select("a").last().attr("href");

        if (nextPage != null && nextPage != "") {
            if (nextPage.contains("10.html")) {
                return;
            }
            getNextPage(PREFIX_URL + nextPage, categoryId);
        }
        return;
    }
}
