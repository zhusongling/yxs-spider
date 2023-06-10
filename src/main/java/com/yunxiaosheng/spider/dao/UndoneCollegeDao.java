package com.yunxiaosheng.spider.dao;

import com.yunxiaosheng.spider.dto.UndoneCollege;

import java.util.List;

public interface UndoneCollegeDao {

    void insertCollege(UndoneCollege undoneCollege);

    List<UndoneCollege> selectClollegeList();

}
