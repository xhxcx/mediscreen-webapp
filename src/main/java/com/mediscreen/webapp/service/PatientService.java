package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenPatient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {

    List<MediScreenPatient> getAllPatients();
    MediScreenPatient findPatient(int patientId);
    MediScreenPatient createPatient(MediScreenPatient patient);
}
