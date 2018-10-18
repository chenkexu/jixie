package com.qmwl.zyjx.bean;

import java.util.List;

/**
 * Created by User on 2017/11/8.
 * 帖子信息的javabean
 */

public class TieZiBean {
    private String title;
    private String content;
    private List<String> imageList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
