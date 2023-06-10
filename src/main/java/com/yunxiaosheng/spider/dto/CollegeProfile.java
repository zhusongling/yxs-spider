package com.yunxiaosheng.spider.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CollegeProfile {
    private String id;
    private String collegeId;
    private String collegeName;
    private Integer year;
    private String title;
    private String content;
    private Integer originPageView;
    private Integer pageView;
    private Date createTime;
}
