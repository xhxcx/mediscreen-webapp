package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenPatient;
import com.mediscreen.webapp.proxy.PatientApiProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public MediScreenPatient createPatient(MediScreenPatient patient) {
        try {
            ResponseEntity<MediScreenPatient> response = patientApiProxy.createPatient(patient);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody();
            }
        } catch (Exception apiException) {
            System.out.println(apiException.getMessage());
        }
        return null;
    }
}
