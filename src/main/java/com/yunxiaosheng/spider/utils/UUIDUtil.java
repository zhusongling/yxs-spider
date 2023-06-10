package com.yunxiaosheng.spider.utils;

import java.util.UUID;

public class UUIDUtil {

    public static final String uuid() {
        return UUID.randomUUID().toString();
    }

    public static final String uuid32() {
        return uuid().replace("-", "");
    }

}
