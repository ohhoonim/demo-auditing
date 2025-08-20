package com.ohhoonim.demo_auditing.para.api;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ohhoonim.demo_auditing.component.id.Id;
import com.ohhoonim.demo_auditing.component.response.Response;
import com.ohhoonim.demo_auditing.component.response.Response.Success;
import com.ohhoonim.demo_auditing.component.response.ResponseCode;
import com.ohhoonim.demo_auditing.para.Note;
import com.ohhoonim.demo_auditing.para.Page;
import com.ohhoonim.demo_auditing.para.Para;
import com.ohhoonim.demo_auditing.para.ParaReq;
import com.ohhoonim.demo_auditing.para.Tag;
import com.ohhoonim.demo_auditing.para.service.NoteService;
import com.ohhoonim.demo_auditing.para.service.TagService;

@RestController
public class NoteController {

    private final NoteService noteService;
    private final TagService tagService;

    public NoteController(
            NoteService noteService, TagService tagService) {
        this.noteService = noteService;
        this.tagService = tagService;
    }

    @PostMapping("/note/list")
    public List<Note> findNote(@RequestBody NoteRequest noteReq) {
        return noteService.findNote(noteReq.searchString(),
                noteReq.page());
    }

    public record NoteRequest(
            String searchString,
            Page page) {
    }

    @GetMapping("/note/{noteId}")
    public Note getNote(@PathVariable("noteId") Id noteId) {
        return noteService.getNote(noteId)
                .orElseThrow(() -> new RuntimeException("note가 존재하지 않습니다"));
    }

    @PostMapping("/note/addNote")
    public Note addNote(@RequestBody Note newNote) {
        return noteService.addNote(newNote).orElseThrow(
                () -> new RuntimeException("저장되지 않았습니다."));
    }

    @PostMapping("/note/modifyNote")
    public Note modifyNote(@RequestBody Note modifiedNote) {
        return noteService.modifyNote(modifiedNote)
            .orElseThrow(() -> new RuntimeException("수정되지 않았습니다"));
    }

    @PostMapping("/note/{noteId}/removeNote")
    public Response removeNote(@PathVariable Id noteId) {
        noteService.removeNote(noteId);
        return new Success(ResponseCode.SUCCESS, noteId);
    }

    @GetMapping("/note/{noteId}/tags")
    public Set<Tag> tags(@PathVariable("noteId") Id noteId) {
        return noteService.tags(noteId);
    }

    @PostMapping("/note/{noteId}/registTag")
    public Set<Tag> registTag(@PathVariable("noteId") Id noteId,
            @RequestBody Tag tag) {
        return noteService.registTag(noteId, tag);
    }

    @PostMapping("/note/{noteId}/removeTag")
    public Set<Tag> removeTag(@PathVariable("noteId") Id noteId,
            @RequestBody Tag tag) {
        return noteService.removeTag(noteId, tag);
    }

    @GetMapping("/note/{noteId}/paras")
    public Set<Para> paras(@PathVariable("noteId") Id noteId) {
        return noteService.paras(noteId);
    }

    @PostMapping("/note/{noteId}/registPara")
    public Response registPara(@PathVariable("noteId") Id noteId,
            @RequestBody ParaReq para) {
        noteService.registPara(noteId, 
                Para.getParaInstance(Id.valueOf(para.getNoteId()), para.getCategory()));
        return new Success(ResponseCode.SUCCESS, noteId);
    }

    @PostMapping("/note/{noteId}/removePara")
    public Response removePara(@PathVariable("noteId") Id noteId,
            @RequestBody ParaReq para) {
        noteService.removePara(noteId, 
            Para.getParaInstance(Id.valueOf(para.getNoteId()), para.getCategory()));
        return new Success(ResponseCode.SUCCESS, noteId);
    }


    @PostMapping("/note/searchTags")
    public Set<Tag> findTagsLimit20PerPage(
            @RequestBody NoteRequest noteReq) {
        return tagService.findTagsLimit20PerPage(
                noteReq.searchString(),
                noteReq.page());
    }

}
