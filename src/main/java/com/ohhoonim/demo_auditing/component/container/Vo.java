package com.ohhoonim.demo_auditing.component.container;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Created;
import com.ohhoonim.demo_auditing.component.auditing.dataBy.Modified;

public final class Vo<T> implements Container {

    private T record;
    private Page page;
    private Created creator;
    private Modified modifier;

    public Vo() {
    }

    public Vo(T record, Page page, Created creator, Modified modifier) {
        this.record = record;
        this.page = page;
        this.creator = creator;
        this.modifier = modifier;
    }

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Created getCreator() {
        return creator;
    }

    public void setCreator(Created creator) {
        this.creator = creator;
    }

    public Modified getModifier() {
        return modifier;
    }

    public void setModifier(Modified modifier) {
        this.modifier = modifier;
    }

    
}
