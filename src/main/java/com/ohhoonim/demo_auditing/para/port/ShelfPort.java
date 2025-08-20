package com.ohhoonim.demo_auditing.para.port;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ohhoonim.demo_auditing.component.id.Id;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.Page;
import com.ohhoonim.demo_auditing.para.Para;
import com.ohhoonim.demo_auditing.para.Para.Shelf;

public interface ShelfPort {

    List<Shelf> findShelves(String searchString, Page page);

    void addShelf(Shelf s, Id newParaId);

    Optional<Para> getShelf(Id newParaId);

    void registNote(Id noteId, Shelf s);

    List<Note> notes(Shelf para);

    void removeNote(Id noteId, Shelf s);

    void removeShelf(Shelf s);

    void modifyShelf(Shelf s);

    void moveToPara(Para origin, Class<? extends Shelf> targetPara);

    Set<Para> findShelfInNote(Id noteId);

}
