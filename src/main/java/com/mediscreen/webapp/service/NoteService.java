package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenNote;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    List<MediScreenNote> getAll();
    List<MediScreenNote> getNotesByPatient(int patientId);

}
