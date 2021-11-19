package com.mediscreen.webapp.proxy;

import com.mediscreen.webapp.model.MediScreenPatient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "patientApi", url = "${patientApi.url}:8081")
public interface PatientApiProxy {

    @GetMapping("/")
    ResponseEntity<List<MediScreenPatient>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<MediScreenPatient> findPatient(@PathVariable("id") int id);

    @PostMapping(value = "/")
    ResponseEntity<MediScreenPatient> createPatient(@Valid @RequestBody MediScreenPatient patient);

    @PutMapping(value = "/{id}")
    ResponseEntity<MediScreenPatient> updatePatient(@PathVariable("id") int id, @Valid @RequestBody MediScreenPatient patient);

}
