package com.ohhoonim.demo_auditing.component.container;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.para.activity.NoteActivity;
import com.ohhoonim.demo_auditing.para.activity.service.TagService;
import com.ohhoonim.demo_auditing.para.api.NoteController;
import com.ohhoonim.demo_auditing.para.api.NoteController.NoteRequest;

@WebMvcTest(NoteController.class)
public class SearchContainerTest {
    
    @Autowired
    MockMvcTester mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    NoteActivity noteService;

    @MockitoBean
    TagService tagService;

    @Test
    public void searchContainerTest() throws JsonProcessingException {
        NoteRequest req = new NoteRequest("some tiitle"); 
        Page page = new Page(new Id()); // lastSeenKey
        Search<NoteRequest> noteReq = new Search<>(req, page);

        String searchJsonContent = objectMapper.writeValueAsString(noteReq);

        mockMvc.post().uri("/note/list")
            .contentType(MediaType.APPLICATION_JSON)
            .content(searchJsonContent)
            .assertThat().apply(print())
            .hasStatusOk()
            .bodyJson().extractingPath("$.code")
            .isEqualTo("SUCCESS")
            ;
    }
}
