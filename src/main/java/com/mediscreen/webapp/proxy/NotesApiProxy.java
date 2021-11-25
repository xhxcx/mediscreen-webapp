package com.mediscreen.webapp.proxy;

import com.mediscreen.webapp.model.MediScreenNote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "notesApi", url = "${notesApi.url}:8082")
public interface NotesApiProxy {
    @GetMapping("/")
    ResponseEntity<List<MediScreenNote>> getAll();

    @GetMapping("/patient/{id}")
    ResponseEntity<List<MediScreenNote>> getNotesForPatientId(@PathVariable("id") int id);

    @GetMapping("/{id}")
    ResponseEntity<MediScreenNote> findNote(@PathVariable("id") String id);

    @PostMapping("/")
    ResponseEntity<MediScreenNote> saveNote(@Valid @RequestBody MediScreenNote note);

    @PutMapping("/{id}")
    ResponseEntity<MediScreenNote> updateNote(@Valid @RequestBody MediScreenNote noteToUpdate);
}
