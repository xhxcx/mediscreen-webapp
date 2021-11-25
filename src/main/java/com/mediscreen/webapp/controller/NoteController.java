package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.model.MediScreenNote;
import com.mediscreen.webapp.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

    @GetMapping("/notes")
    public String showAllNotes(Model model) {
        LOGGER.info("GET /notes");
        model.addAttribute("allNotesList", noteService.getAll());
        model.addAttribute("activePage", "allNotesList");
        return "allNotes";
    }

    @GetMapping("/addNote")
    public String showAddNoteForm(@RequestParam int patientId, Model model){
        LOGGER.info("GET /addNote patientId : " + patientId);
        MediScreenNote newNote = new MediScreenNote();
        newNote.setPatientId(patientId);
        model.addAttribute("currentNote", newNote);
        return "noteForm";
    }

    @GetMapping("/note")
    public String showEditForm(@RequestParam String noteId, Model model){
        LOGGER.info("GET /note noteId: " + noteId);
        model.addAttribute("currentNote", noteService.findNote(noteId));
        return "noteForm";
    }

    @PostMapping(value = {"/addNote","/note"})
    public String saveNote(@ModelAttribute("noteToSave") MediScreenNote note, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model){
        LOGGER.info("POST " + request.getServletPath() + " noteToSave : " + note);
        MediScreenNote savedNote = noteService.saveNote(note);
        if(savedNote != null) {
            redirectAttributes.addFlashAttribute("showNoteSuccessMessage", request.getServletPath().equalsIgnoreCase("/note") ? "Note successfully updated" : "Note successfully added to the patient");
            return "redirect:/patient?id=" + savedNote.getPatientId();
        }
        model.addAttribute("errorMessage", "An error occurs creating the note");
        model.addAttribute("currentNote", note);
        return "noteForm";
    }
}
