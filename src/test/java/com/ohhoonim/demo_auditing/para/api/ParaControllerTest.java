package com.ohhoonim.demo_auditing.para.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;
import com.ohhoonim.demo_auditing.component.container.Page;
import com.ohhoonim.demo_auditing.component.container.Search;
import com.ohhoonim.demo_auditing.para.Para;
import com.ohhoonim.demo_auditing.para.Para.ParaEnum;
import com.ohhoonim.demo_auditing.para.Para.Shelf.Area;
import com.ohhoonim.demo_auditing.para.activity.service.ParaService;

@WebMvcTest(ParaController.class)
public class ParaControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    ParaService paraService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void makeUuid() {
        System.out.println(new Id());
    }

    @Test
    public void notes() {
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";
        mockMvcTester.get()
                .uri("/para/" + paraId + "/notes")
                .param("category", "project")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    public void registNote() throws JsonProcessingException {
        var paraReq = new ParaNoteReq("project", new Id().toString());
        var body = objectMapper.writeValueAsString(paraReq);
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";
        mockMvcTester.post()
                .uri("/para/" + paraId + "/registNote")
                .param("category", "project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    public void removeNote() throws JsonProcessingException {
        var paraReq = new ParaNoteReq("project", new Id().toString());
        var body = objectMapper.writeValueAsString(paraReq);
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";
        mockMvcTester.post()
                .uri("/para/" + paraId + "/removeNote")
                .param("category", "area")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    public void moveToPara() throws JsonProcessingException {
        var paraReq = new ParaNoteReq("area", new Id().toString(), "archive");
        var body = objectMapper.writeValueAsString(paraReq);
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";

        when(paraService.moveToPara(any(), any()))
                .thenReturn(Optional.of(
                        Para.of(Id.valueOf(paraId), Area.class)));

        mockMvcTester.post()
                .uri("/para/" + paraId + "/moveTo")
                .param("category", "area")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    // @GetMapping("/para/{paraId}")
    @Test
    public void getPara() {
        var paraId = new Id();
        when(paraService.getPara(any()))
                .thenReturn(Optional.of(Para.of(paraId, Area.class)));
        mockMvcTester.get()
                .uri("/para/" + paraId)
                .param("category", "area")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.data.paraId")
                .isEqualTo(paraId.toString());
    }

    @Test
    public void addPara() throws JsonProcessingException {
        var newPara = new ParaReq(null, "area", "economic", "micro ecomonimc");
        when(paraService.addPara(any()))
                .thenReturn(Optional.of(Para.of(new Id(), Area.class)));
        mockMvcTester.post()
                .uri("/para/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPara))
                .assertThat().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    public void modifyPara() throws JsonProcessingException {
        var paraId = new Id().toString();
        var para = new ParaReq(paraId, "area", "economic", "micro ecomonimc");
        when(paraService.modifyPara(any()))
                .thenReturn(Optional.of(Para.of(new Id(), Area.class)));
        mockMvcTester.post()
                .uri("/para/" + paraId + "/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat().apply(print()).bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    public void removePara() throws JsonProcessingException {
        var paraId = new Id().toString();
        var para = new ParaReq(paraId, null, null, null);
        mockMvcTester.post()
                .uri("/para/" + paraId + "/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat().apply(print()).bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
        verify(paraService, times(1)).removePara(any());
    }

    @Test
    public void findProject() throws JsonProcessingException {
        var project = new ParaReq(null, ParaEnum.Project.toString(), null, null);
        var search = new Search<ParaReq> (project, new Page());
        mockMvcTester.post()
            .uri("/para/searchProjects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(search))
            .assertThat().apply(print()).bodyJson()
            .extractingPath("$.code")
            .isEqualTo("SUCCESS");
            
    }

    @Test
    public void findShelves() throws JsonProcessingException {
        var area = new ParaReq(null, ParaEnum.Area.toString(),null, null);
        var search = new Search<ParaReq> (area, new Page());
        mockMvcTester.post()
            .uri("/para/searchShelves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(search))
            .assertThat().apply(print()).bodyJson()
            .extractingPath("$.code")
            .isEqualTo("SUCCESS");

    }

}
