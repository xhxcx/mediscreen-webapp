package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenPatient;
import com.mediscreen.webapp.proxy.PatientApiProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientApiProxy patientApiProxy;

    @Override
    public List<MediScreenPatient> getAllPatients() {
        return patientApiProxy.getAll().getBody();
    }

    @Override
    public MediScreenPatient findPatient(int patientId) {
        try {
            ResponseEntity<MediScreenPatient> response = patientApiProxy.findPatient(patientId);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public MediScreenPatient savePatient(MediScreenPatient patient) {
        try {
            // if patient.id is null then we are creating a new patient otherwise, the patient already have an id
            ResponseEntity<MediScreenPatient> response =
                    patient.getId() == null ? patientApiProxy.createPatient(patient) : patientApiProxy.updatePatient(patient.getId(), patient);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception apiException) {
            System.out.println(apiException.getMessage());
        }
        return null;
    }
}
