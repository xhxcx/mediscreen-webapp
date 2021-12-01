package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenRiskLevel;
import com.mediscreen.webapp.proxy.AssessmentApiProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentServiceImpl implements AssessmentService{

    @Autowired
    private AssessmentApiProxy assessmentApiProxy;

    @Override
    public MediScreenRiskLevel getRiskLevel(int patientId) {
        return assessmentApiProxy.getRiskForPatient(patientId).getBody();
    }
}
