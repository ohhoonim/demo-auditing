package com.ohhoonim.demo_auditing.para;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.MasterCode;

public enum ProjectStatus implements MasterCode{
    Backlog("Backlog","backlog"), 
    Ready("Ready","ready"), 
    Inprogress("Inprogress","inprogress"), 
    Done("Done","done") ;

    private String masterCode;
    private String langCode;

    private ProjectStatus(String masterCode, String langCode){
        this.masterCode = masterCode;
        this.langCode = langCode;
    }
    @Override
    public String groupCode() {
        return "PROJECT_STATUS";
    }

    @Override
    public String masterCode() {
        return this.masterCode;
    }

    @Override
    public String langCode() {
        return this.langCode;
    }
}
