package com.ohhoonim.demo_auditing.para.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ohhoonim.demo_auditing.component.id.Id;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.Page;
import com.ohhoonim.demo_auditing.para.ParaReq;
import com.ohhoonim.demo_auditing.para.Tag;
import com.ohhoonim.demo_auditing.para.api.NoteController.NoteRequest;
import com.ohhoonim.demo_auditing.para.service.NoteService;
import com.ohhoonim.demo_auditing.para.service.TagService;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    NoteService noteService;

    @MockitoBean
    TagService tagService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void findNote() throws JsonProcessingException {
        var noteReq = new NoteRequest("", new Page());
        var reqData = objectMapper.writeValueAsString(noteReq);
        mockMvcTester.post()
                .uri("/note/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqData) // post method는 여기에 담아서 넘긴다
                .assertThat()
                .apply(print()) 
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code") // JsonPath로 동작 시작
                .isEqualTo("SUCCESS");
    }

    @Test
    public void makeUuid() {
        System.out.println("=================================" + new Id().toString());
    }

    @Test
    public void getNote() {
        mockMvcTester.get()
                .uri("/note/524NN3ZV6J9HBR804FT3JZV8P6")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.message")
                .isEqualTo("note가 존재하지 않습니다");
    }

    @Test
    public void addNote() throws JsonProcessingException {
        var noteId = new Id();
        var stubNote = new Note(noteId, "economic", "micoro");
        when(noteService.addNote(any())).thenReturn(Optional.of(stubNote));

        var note = new Note(null, "economic", "micro");
        var newNoteString = objectMapper.writeValueAsString(note);
        mockMvcTester.post()
                .uri("/note/addNote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNoteString)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.noteId")
                .isEqualTo(noteId.toString());
    }

    @Test
    public void modifyNote() throws JsonProcessingException {
        var noteId = new Id();
        var stubNote = new Note(noteId, "economic", "micoro");
        when(noteService.modifyNote(any())).thenReturn(Optional.of(stubNote));

        var note = new Note(null, "economic", "micro");
        var newNoteString = objectMapper.writeValueAsString(note);
        mockMvcTester.post()
                .uri("/note/modifyNote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNoteString)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.noteId")
                .isEqualTo(noteId.toString());

    }

    @Test
    public void removeNote() throws JsonProcessingException {
        var noteId = new Id();
        mockMvcTester.post()
                .uri("/note/" + noteId.toString() + "/removeNote")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

        verify(noteService, times(1)).removeNote(any());
    }

    @Test
    public void tags() {
        var noteId = new Id();
        mockMvcTester.get()
                .uri("/note/" + noteId + "/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    public void registTag() throws JsonProcessingException {
        var newTag = new Tag(null, "java");
        var noteId = new Id();
        mockMvcTester.post()
                .uri("/note/" + noteId + "/registTag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTag))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    public void removeTag() throws JsonProcessingException {
        var targetTag = new Tag(1L, "java");
        var noteId = new Id();
        mockMvcTester.post()
                .uri("/note/" + noteId + "/removeTag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(targetTag))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    public void paras() {
        var noteId = new Id();
        mockMvcTester.get()
                .uri("/note/" + noteId + "/paras")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void registPara() throws JsonProcessingException {

        var para = new ParaReq(new Id().toString(), "project");
        var noteId = new Id().toString();

        var json = objectMapper.writeValueAsString(para);
        String category = JsonPath.read(json, "$.category"); 
        assertThat(category).isEqualTo("project");

        mockMvcTester.post()
                .uri("/note/" + noteId + "/registPara")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    public void removePara() throws JsonProcessingException {
        var para = new ParaReq(new Id().toString(), "project");
        var noteId = new Id();
        mockMvcTester.post()
                .uri("/note/" + noteId + "/removePara")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    public void findTagsLimit20PerPageTest() throws JsonProcessingException {
        var noteReq = new NoteRequest(
                "",
                new Page());
        mockMvcTester.post()
                .uri("/note/searchTags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteReq))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

}
