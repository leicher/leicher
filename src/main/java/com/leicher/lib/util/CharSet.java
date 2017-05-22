package com.leicher.lib.util;

/**
 * Created by Administrator on 2017/5/16.
 */

public enum  CharSet {
    UTF8("UTF-8");

    private final String value;

    CharSet(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
