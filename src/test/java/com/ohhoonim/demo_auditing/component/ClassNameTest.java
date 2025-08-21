package com.ohhoonim.demo_auditing.component;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.api.NoteController;

public class ClassNameTest {
    
    @Test
    public void classNameTest() {
        var note = new Note();
        assertThat(note.getClass().getName())
            .isEqualTo("com.ohhoonim.demo_auditing.para.Note");
    }

    @Test
    public void simpleNameTest() {
        var note = new Note();
        assertThat(note.getClass().getSimpleName().toLowerCase())
            .isEqualTo("note");
    }

    @Test
    public void translateCaseTest() {
        var entityType = Id.entityType(NoteController.class);
        assertThat(entityType).isEqualTo("note_controller");
    }
}
