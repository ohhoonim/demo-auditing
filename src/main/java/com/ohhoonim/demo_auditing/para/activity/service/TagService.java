package com.ohhoonim.demo_auditing.para.activity.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.ohhoonim.demo_auditing.component.container.Page;
import com.ohhoonim.demo_auditing.para.Tag;
import com.ohhoonim.demo_auditing.para.activity.TagActivity;
import com.ohhoonim.demo_auditing.para.activity.port.TagPort;

@Service
public class TagService implements TagActivity {

    private final TagPort tagPort;

    public TagService(TagPort tagPort) {
        this.tagPort = tagPort;
    }

    @Override
    public Set<Tag> findTagsLimit20PerPage(String tag, Page page) {
        return tagPort.findTags(tag, page);
    }
    
}
