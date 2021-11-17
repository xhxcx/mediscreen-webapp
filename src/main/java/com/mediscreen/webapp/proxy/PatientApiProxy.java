package com.mediscreen.webapp.proxy;

import com.mediscreen.webapp.model.MediScreenPatient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patientApi", url = "${patientApi.url}:8081")
public interface PatientApiProxy {

    @GetMapping("/")
    ResponseEntity<List<MediScreenPatient>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<MediScreenPatient> findPatient(@PathVariable("id") int id);

}
