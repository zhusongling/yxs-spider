package com.yunxiaosheng.spider.dao;

import com.yunxiaosheng.spider.dto.Article;

public interface ArticleDao {

    int insertSelective(Article article);
}
