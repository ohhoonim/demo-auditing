package com.ohhoonim.demo_auditing.para;

public class Tag {
    private Long tagId;
    private String tag;

    public Tag() {
    }

    public Tag(Long tagId, String tag) {
        this.tagId = tagId;
        this.tag = tag;
    }
    public Long getTagId() {
        return tagId;
    }
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    
}