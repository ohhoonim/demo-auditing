package com.ohhoonim.demo_auditing.component.container;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.activity.port.NotePort;
import com.ohhoonim.demo_auditing.para.activity.service.NoteService;

@ExtendWith(MockitoExtension.class)
public class VoContainerTest {

    @InjectMocks
    NoteService noteService;

    @Mock
    NotePort notePort; 

    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }


    @Test
    public void valuedObjectTest() throws JsonProcessingException {
        List<Note> noteList = List.of(
            new Note(new Id(), "book1"), 
            new Note(new Id(), "book2")
        );
        when(notePort.findNote(any(), any())).thenReturn(noteList);
        var valuedObject = noteService.findNote("some title", new Page());

        var json = objectMapper.writeValueAsString(valuedObject);

        var title = JsonPath.read(json, "$.record[0].title");
        assertThat(title).isEqualTo("book1");

        var limitPerPage = JsonPath.read(json, "$.page.limit");
        assertThat(limitPerPage).isEqualTo(10);
    }
}
















