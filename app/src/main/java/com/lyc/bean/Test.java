package com.lyc.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class Test {

    private String title;
    private List<List<String>> datas;

    public Test() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<String>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<String>> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "Test{" +
                "title='" + title + '\'' +
                ", datas=" + datas +
                '}';
    }
}
