package com.ohhoonim.demo_auditing.para;

public class Search<T> {

    private T para;
    private Page page;
    public Search(T para, Page page) {
        this.para = para;
        this.page = page;
    }
    public Search() {
    }
    public T getPara() {
        return para;
    }
    public void setPara(T para) {
        this.para = para;
    }
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }

}