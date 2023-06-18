package com.yunxiaosheng.spider.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnrollPlan implements Serializable {
    /**
     * 逻辑主键
     */
    private String id;

    /**
     * 院校id
     */
    private String collegeId;

    /**
     * 招生代码
     */
    private String enrollId;

    /**
     * 招生名称
     */
    private String enrollName;

    /**
     * 招生专业代码
     */
    private String majorCode;

    /**
     * 招生专业名称
     */
    private String majorName;

    /**
     * 高考年份
     */
    private Integer year;

    /**
     * 科类代码（文理科）
     */
    private String subjectCode;

    /**
     * 科类名称
     */
    private String subjectName;

    /**
     * 批次代码
     */
    private String batchCode;

    /**
     * 批次名称
     */
    private String batchName;

    /**
     * 计划人数
     */
    private Integer planNum;

    /**
     * 学费标准（元/年）
     */
    private Integer tuition;

    /**
     * 学制
     */
    private Integer duration;

    /**
     * 学历层次
     */
    private String educationLevel;

    /**
     * 备注说明
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}