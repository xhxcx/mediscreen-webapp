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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NoteControllerTests {

    @MockBean
    private NoteService noteServiceMock;

    @Autowired
    private MockMvc mockMvc;

    private static final MediScreenNote note = new MediScreenNote();

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

}
