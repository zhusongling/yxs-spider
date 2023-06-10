package com.yunxiaosheng.spider.dao;

import com.yunxiaosheng.spider.dto.CollegePage;
import com.yunxiaosheng.spider.dto.UndoneCollege;

import java.util.List;

public interface CollegePageDao {

    void insertCollegePage(CollegePage collegePage);

    List<CollegePage> selectCollegePageList();

}
