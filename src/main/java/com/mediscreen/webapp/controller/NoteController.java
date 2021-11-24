package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public String showAllNotes(Model model) {
        model.addAttribute("allNotesList", noteService.getAll());
        model.addAttribute("activePage", "allNotesList");
        return "allNotes";
    }
}
