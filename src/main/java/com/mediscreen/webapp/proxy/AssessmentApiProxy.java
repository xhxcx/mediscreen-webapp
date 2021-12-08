package com.mediscreen.webapp.proxy;

import com.mediscreen.webapp.model.MediScreenRiskLevel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "assessmentApi", url = "${assessmentApi.url}:8083")
public interface AssessmentApiProxy {

    @GetMapping("/")
    ResponseEntity<MediScreenRiskLevel> getRiskForPatient(@RequestParam int patientId);
}
