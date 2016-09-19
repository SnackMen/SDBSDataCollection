package com.ws.model;

import com.ws.util.JsonUtils;

/**
 * Created by laowang on 16-9-18.
 */
public class RAMAN {
    private int sdbsno;
    private String picUrl;

    public int getSdbsno() {
        return sdbsno;
    }

    public void setSdbsno(int sdbsno) {
        this.sdbsno = sdbsno;
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
