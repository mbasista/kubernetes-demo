package pl.demo.noteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    public String readNote(@PathVariable("id") final Long id) {
        return noteService.findJson(id);
    }

    @PostMapping
    public Long saveNote(@RequestBody final String content) {
        return noteService.saveJson(content);
    }
}
