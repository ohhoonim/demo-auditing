package com.ohhoonim.demo_auditing.para.activity.port;

import java.util.Set;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.component.container.Page;
import com.ohhoonim.demo_auditing.para.Tag;

public interface TagPort {

    Set<Tag> findTags(String tag, Page page);

    void addTagInNote(Id noteId, Tag tag);

    Set<Tag> tagsInNote(Id noteId);

    void removeTagInNote(Id noteId, Tag tag);
}
