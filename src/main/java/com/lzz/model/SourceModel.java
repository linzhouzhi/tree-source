package com.lzz.model;

import net.sf.json.JSONObject;

/**
 * Created by lzz on 2018/3/22.
 */
public class SourceModel {
    private String address;
    private String type;
    private String showName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String serialize(){
        JSONObject jsonObject = JSONObject.fromObject( this );
        return jsonObject.toString();
    }

    public static SourceModel unSerialize(String sourceStr){
        JSONObject jsonObject = JSONObject.fromObject( sourceStr );
        SourceModel sourceModel = (SourceModel) JSONObject.toBean(jsonObject, SourceModel.class);
        return sourceModel;
    }
}
