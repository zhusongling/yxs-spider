package com.yunxiaosheng.spider.rest;

import com.yunxiaosheng.spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spider")
public class NewsFocusController {

    @Autowired
    SpiderService spiderService;

    @GetMapping("/news/focus")
    public void newsFocus(String baseUrl, String categoryId) {
        this.spiderService.obtainContent(baseUrl, categoryId);
    }
}
