package com.qmwl.zyjx.bean;

/**
 * Created by Administrator on 2017/8/14.
 */

public class MessageBean {
    //                "message_id": 3236,
//                        "uid": 140,
//                        "notice": "",
//                        "avatar": null,
//                        "message": "通知565768",
//                        "type": 1,
//                        "is_read": 0,
//                        "send_time": 1502630996,
//                        "data": "0"
    private int type;
    private int is_read;
    private long message_id;
    private long send_time;
    private String uid;
    private String avatar;
    private String message;
    private String data;
    private String notice;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public long getSend_time() {
        return send_time;
    }

    public void setSend_time(long send_time) {
        this.send_time = send_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
