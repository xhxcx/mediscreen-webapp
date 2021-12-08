package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenRiskLevel;
import com.mediscreen.webapp.proxy.AssessmentApiProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AssessmentServiceImpl implements AssessmentService{

    @Autowired
    private AssessmentApiProxy assessmentApiProxy;

    @Override
    public MediScreenRiskLevel getRiskLevel(int patientId) {
        ResponseEntity<MediScreenRiskLevel> response = assessmentApiProxy.getRiskForPatient(patientId);
        
        if (!response.hasBody()) {
            return MediScreenRiskLevel.NOT_APPLICABLE;
        }
        return assessmentApiProxy.getRiskForPatient(patientId).getBody();
    }
}
