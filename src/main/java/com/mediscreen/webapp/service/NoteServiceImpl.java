package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenNote;
import com.mediscreen.webapp.proxy.NotesApiProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NotesApiProxy notesApiProxy;

    @Override
    public List<MediScreenNote> getAll() {
        return notesApiProxy.getAll().getBody();
    }

    @Override
    public List<MediScreenNote> getNotesByPatient(int patientId) {
        return notesApiProxy.getNotesForPatientId(patientId).getBody();
    }
}
