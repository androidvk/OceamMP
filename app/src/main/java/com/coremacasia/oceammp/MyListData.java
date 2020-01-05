package com.coremacasia.oceammp;

class MyListData {
    private String description;
    private int type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int imgId;

    public MyListData(String description, int type, int imgId) {
        this.description = description;
        this.type = type;
        this.imgId = imgId;
    }
}
