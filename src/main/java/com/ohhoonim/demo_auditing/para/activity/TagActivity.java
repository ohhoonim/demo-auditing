package com.ohhoonim.demo_auditing.para.activity;

import java.util.Set;

import com.ohhoonim.demo_auditing.para.Page;
import com.ohhoonim.demo_auditing.para.Tag;

public interface TagActivity {
    public Set<Tag> findTagsLimit20PerPage(String tag, Page page);
}
