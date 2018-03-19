package com.lzz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzz on 2018/3/18.
 */
public class Tnode {
    private String id;
    private String text;
    private String type;
    private SourceType sourceType;
    private List<Tnode> children = new ArrayList<>();

    public Tnode(){

    }
    public Tnode(String id, String text, String type) {
        this.id = id;
        this.text = text;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public List<Tnode> getChildren() {
        return children;
    }

    public void setChildren(List<Tnode> children) {
        this.children = children;
    }
}
