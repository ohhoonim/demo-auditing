package com.ohhoonim.demo_auditing.para.activity.port;

import java.util.List;
import java.util.Optional;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.component.container.Page;
import com.ohhoonim.demo_auditing.para.Note;

public interface NotePort {

    void addNote(Note newNote, Id newNoteId);

    Optional<Note> getNote(Id noteId);

    void modifyNote(Note modifiedNote);

    void removeNote(Id noteId);

    List<Note> findNote(String searchString, Page page);
    
}
