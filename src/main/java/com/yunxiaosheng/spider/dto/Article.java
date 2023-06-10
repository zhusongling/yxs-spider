package com.yunxiaosheng.spider.dto;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 文章分类ID
     */
    private String categoryId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 封面图片
     */
    private String imageUrl;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 浏览量
     */
    private Integer pageView;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 展示开始日期
     */
    private Date beginTime;

    /**
     * 展示结束日期
     */
    private Date endTime;

    /**
     * 院校id
     */
    private String collegeId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", releaseTime=" + releaseTime +
                ", pageView=" + pageView +
                ", content='" + content + '\'' +
                ", isDelete=" + isDelete +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", collegeId='" + collegeId + '\'' +
                '}';
    }
}
