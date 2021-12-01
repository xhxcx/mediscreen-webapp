package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenRiskLevel;
import org.springframework.stereotype.Service;

@Service
public interface AssessmentService {
    MediScreenRiskLevel getRiskLevel(int patientId);
}
