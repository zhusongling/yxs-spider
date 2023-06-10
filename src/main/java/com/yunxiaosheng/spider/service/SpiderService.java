package com.yunxiaosheng.spider.service;

public interface SpiderService {

    /**
     * 获取内容
     *
     * @param baseUrl
     * @param categoryId
     */
    void obtainContent(String baseUrl, String categoryId);

}
