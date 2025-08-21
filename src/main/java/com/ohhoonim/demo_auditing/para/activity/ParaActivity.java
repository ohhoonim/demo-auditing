package com.ohhoonim.demo_auditing.para.activity;

import java.util.List;
import java.util.Optional;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.component.container.Page;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.Para;
import com.ohhoonim.demo_auditing.para.Para.Project;
import com.ohhoonim.demo_auditing.para.Para.Shelf;

public interface ParaActivity {

    public List<Note> notes(Para paraId);

    public List<Note> registNote(Para paraId, Id noteId);

    public List<Note> removeNote(Para paraId, Id noteId);

    public Optional<Para> moveToPara(Para origin, Class<? extends Shelf> targetPara);

    public Optional<Para> getPara(Para para);

    public Optional<Para> addPara(Para para);

    public Optional<Para> modifyPara(Para para);

    public void removePara(Para para);

    public List<Shelf> findShelves(String searchString, Page page);

    public List<Project> findProjects(String searchString, Page page);
}
