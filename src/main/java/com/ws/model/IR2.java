package com.ws.model;

import com.ws.util.JsonUtils;

/**
 * Created by laowang on 16-10-1.
 */
public class IR2 {
    private int sdbsno;
    private String head;
    private String tile;

    public String getTileTitle() {
        return tileTitle;
    }

    public void setTileTitle(String tileTitle) {
        this.tileTitle = tileTitle;
    }

    private String tileTitle;

    public int getSdbsno() {
        return sdbsno;
    }

    public void setSdbsno(int sdbsno) {
        this.sdbsno = sdbsno;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }
    public String toJson(){
        return JsonUtils.toJson(this);
    }
}
