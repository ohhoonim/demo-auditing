package com.ohhoonim.demo_auditing.para.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.ohhoonim.demo_auditing.component.id.Id;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.Page;
import com.ohhoonim.demo_auditing.para.Para;
import com.ohhoonim.demo_auditing.para.Para.Project;
import com.ohhoonim.demo_auditing.para.Para.Shelf;
import com.ohhoonim.demo_auditing.para.Tag;
import com.ohhoonim.demo_auditing.para.activity.NoteActivity;
import com.ohhoonim.demo_auditing.para.port.NotePort;
import com.ohhoonim.demo_auditing.para.port.ProjectPort;
import com.ohhoonim.demo_auditing.para.port.ShelfPort;
import com.ohhoonim.demo_auditing.para.port.TagPort;

@Service
public class NoteService implements NoteActivity {

    private final NotePort notePort;
    private final ProjectPort projectPort;
    private final ShelfPort shelfPort;
    private final TagPort tagPort;

    public NoteService(NotePort notePort,
            ProjectPort projectPort,
            ShelfPort shelfPort,
            TagPort tagPort) {
        this.notePort = notePort;
        this.projectPort = projectPort;
        this.shelfPort = shelfPort;
        this.tagPort = tagPort;
    }

    @Override
    public Optional<Note> getNote(Id noteId) {
        var note = notePort.getNote(noteId);
        if (note.isEmpty()) {
            throw new RuntimeException("노트가 존재하지 않습니다.");
        }
        Set<Tag> tags = tagPort.tagsInNote(noteId);
        return Optional.of(new Note(note.get(), tags));
    }

    @Override
    public void removeNote(Id noteId) {
        notePort.removeNote(noteId);
    }

    @Override
    public Optional<Note> modifyNote(Note modifiedNote) {
        notePort.modifyNote(modifiedNote);
        return this.getNote(modifiedNote.getNoteId());
    }

    @Override
    public Optional<Note> addNote(Note newNote) {
        var newNoteId = new Id();
        notePort.addNote(newNote, newNoteId);
        return this.getNote(newNoteId);
    }

    @Override
    public Set<Tag> tags(Id noteId) {
        return tagPort.tagsInNote(noteId);
    }

    @Override
    public Set<Tag> registTag(Id noteId, Tag tag) {
        tagPort.addTagInNote(noteId, tag);
        return tagPort.tagsInNote(noteId);
    }

    @Override
    public Set<Tag> removeTag(Id noteId, Tag tag) {
        tagPort.removeTagInNote(noteId, tag);
        return tagPort.tagsInNote(noteId);
    }

    @Override
    public void registPara(Id noteId, Para para) {
        if (para == null || para.getParaId() == null) {
            throw new RuntimeException("id는 필수 입니다.");
        }
        if (para instanceof Project project) {
            projectPort.registNote(noteId, project);
        } else {
            shelfPort.registNote(noteId, (Shelf)para);
        }
    }

    @Override
    public Set<Para> paras(Id noteId) {
        Set<Para> projects = projectPort.findProjectInNote(noteId);
        Set<Para> shelves = shelfPort.findShelfInNote(noteId);
        
        // TODO virtual thread로 바꿔보기

        return Stream.of(projects, shelves)
                .flatMap(p -> p != null ? p.stream() : null)
                .collect(Collectors.toSet());
    }

    @Override
    public void removePara(Id noteId, Para para) {
        switch(para) {
            case Project p -> projectPort.removeNote(noteId, p);
            case Shelf s -> shelfPort.removeNote(noteId, s);
        }
    }

    @Override
    public List<Note> findNote(String searchString, Page page) {
        return notePort.findNote(searchString, page);
    }
}


