package com.ohhoonim.demo_auditing.para;

import com.ohhoonim.demo_auditing.component.id.Id;

public class Tag {
    private Id tagId;
    private String tag;

    public Tag() {
    }

    public Tag(Id tagId, String tag) {
        this.tagId = tagId;
        this.tag = tag;
    }
    public Id getTagId() {
        return tagId;
    }
    public void setTagId(Id tagId) {
        this.tagId = tagId;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    
}