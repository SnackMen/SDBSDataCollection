package com.ws.model;

import com.ws.util.JsonUtils;

/**
 * Created by laowang on 16-9-18.
 */
public class MS {
    private int sdbsno;
    private String topText;
    private String underText;
    private String picUrl;

    public int getSdbsno() {
        return sdbsno;
    }

    public void setSdbsno(int sdbsno) {
        this.sdbsno = sdbsno;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getUnderText() {
        return underText;
    }

    public void setUnderText(String underText) {
        this.underText = underText;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String toJson(){
        return JsonUtils.toJson(this);
    }
}
