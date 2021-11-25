package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.model.MediScreenNote;
import com.mediscreen.webapp.service.NoteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NoteControllerTests {

    @MockBean
    private NoteService noteServiceMock;

    @Autowired
    private MockMvc mockMvc;

    private static final MediScreenNote note = new MediScreenNote();
    private static final String NOTE_AS_JSON = "{\"id\":\"noteId\",\"patientId\":1,\"note\":\"note content\"}";

    @BeforeAll
    static void setUp(){
        note.setId("noteId");
        note.setPatientId(1);
        note.setNote("note content");
    }

    @Test
    public void showAllNotesShouldReturnListOfAllNotesAndDisplayAllNotesTPL() throws Exception {
        Mockito.when(noteServiceMock.getAll()).thenReturn(Collections.singletonList(note));

        mockMvc.perform(get("/notes"))
                .andExpect(view().name("allNotes"))
                .andExpect(model().attributeExists("allNotesList"));
    }

    @Test
    public void showAddNoteFormShouldDisplayAddNoteFormTPLAndAddNoteToModel() throws Exception {
        mockMvc.perform(get("/addNote").param("patientId",String.valueOf(1)))
                .andExpect(view().name("addNoteForm"))
                .andExpect(model().attributeExists("newNote"));
    }

    @Test
    public void saveNoteShouldRedirectToPatientInformationsTPLAndAddSuccessMessage() throws Exception {
        Mockito.when(noteServiceMock.saveNote(any(MediScreenNote.class))).thenReturn(note);
        String expectedRedirectPath = "/patient?id=" + note.getPatientId();

        mockMvc.perform(post("/addNote").contentType(MediaType.APPLICATION_JSON).content(NOTE_AS_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(expectedRedirectPath))
                .andExpect(flash().attributeExists("showNoteSuccessMessage"));
    }

    @Test
    public void saveNoteShouldDisplayAddFormTPLAndAddErrorMessage() throws Exception {
        Mockito.when(noteServiceMock.saveNote(any(MediScreenNote.class))).thenReturn(null);

        mockMvc.perform(post("/addNote").contentType(MediaType.APPLICATION_JSON).content(NOTE_AS_JSON))
                .andExpect(view().name("addNoteForm"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("newNote"));
    }

}
