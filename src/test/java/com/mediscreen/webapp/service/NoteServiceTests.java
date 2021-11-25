package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenNote;
import com.mediscreen.webapp.proxy.NotesApiProxy;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@SpringBootTest
public class NoteServiceTests {

    @Mock
    private NotesApiProxy notesApiProxyMock;

    @InjectMocks
    private NoteServiceImpl noteService;

    private static final MediScreenNote note = new MediScreenNote();

    @BeforeAll
    static void setUp(){
        note.setId("noteId");
        note.setPatientId(1);
        note.setNote("note content");
    }

    @Test
    public void getAllTest(){
        Mockito.when(notesApiProxyMock.getAll()).thenReturn(new ResponseEntity<>(Collections.singletonList(note), HttpStatus.OK));

        List<MediScreenNote> result = noteService.getAll();

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(note.getId(), result.get(0).getId());
    }

    @Test
    public void getNotesByPatientTest(){
        Mockito.when(notesApiProxyMock.getNotesForPatientId(1)).thenReturn(new ResponseEntity<>(Collections.singletonList(note), HttpStatus.OK));

        List<MediScreenNote> result = noteService.getNotesByPatient(1);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(note.getId(), result.get(0).getId());
    }

    @Test
    public void saveNoteTest(){
        Mockito.when(notesApiProxyMock.saveNote(note)).thenReturn(new ResponseEntity<>(note,HttpStatus.CREATED));

        MediScreenNote result = noteService.saveNote(note);

        Assert.assertEquals(note.getId(), result.getId());
    }

    @Test
    public void saveNoteKOTest(){
        Mockito.when(notesApiProxyMock.saveNote(note)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        MediScreenNote result = noteService.saveNote(note);

        Assert.assertNull(result);
    }
}
