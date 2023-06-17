package com.yunxiaosheng.spider.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * tb_ha_enroll_score
 * @author 
 */
@Data
public class EnrollScore implements Serializable {
    /**
     * 逻辑主键
     */
    private String id;

    /**
     * 院校id
     */
    private String collegeId;

    /**
     * 招生id
     */
    private String enrollId;

    /**
     * 招生名称
     */
    private String enrollName;

    /**
     * 录取年份
     */
    private Integer year;

    /**
     * 科目类别（文理科）
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
     * 投档类型
     */
    private Integer deliverType;

    /**
     * 公布计划
     */
    private Integer planNum;

    /**
     * 录取人数
     */
    private Integer actualNum;

    /**
     * 录取最高分
     */
    private Double maxScore;

    /**
     * 录取最低分
     */
    private Double minScore;

    /**
     * 录取平均分
     */
    private Double avgScore;

    /**
     * 录取最高分位次
     */
    private Integer maxRank;

    /**
     * 录取最低分位次
     */
    private Integer minRank;

    /**
     * 录取平均分位次
     */
    private Integer avgRank;

    private static final long serialVersionUID = 1L;
}