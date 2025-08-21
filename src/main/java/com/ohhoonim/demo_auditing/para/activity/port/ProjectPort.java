package com.ohhoonim.demo_auditing.para.activity.port;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.component.container.Page;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.Para;
import com.ohhoonim.demo_auditing.para.Para.Project;

public interface ProjectPort {

    List<Project> findProjects(String searchString, Page page);

    void registNote(Id noteId, Project project);

    void addProject(Project project, Id newParaId);

    Optional<Para> getProject(Id paraId);

    List<Note> notes(Project para);

    void removeNote(Id noteId, Project p);

    void removeProject(Project p);

    void modifyProject(Project p);

    Set<Para> findProjectInNote(Id noteId);
    
}
