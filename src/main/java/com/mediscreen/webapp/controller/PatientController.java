package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.model.MediScreenPatient;
import com.mediscreen.webapp.service.AssessmentService;
import com.mediscreen.webapp.service.NoteService;
import com.mediscreen.webapp.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
public class PatientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/")
    public String viewHome(){
        return "index";
    }

    @GetMapping("/patients")
    public String viewAllPatients(Model model){
        LOGGER.info("GET /patients");
        model.addAttribute("activePage", "patientList");
        model.addAttribute("patientList", patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/patient")
    public String findPatientById(@RequestParam int id, @RequestParam(required = false, defaultValue = "false")boolean isEdit, Model model, RedirectAttributes redirectAttrs){
        LOGGER.info("GET /patient patientId = " + id);
        MediScreenPatient patient = patientService.findPatient(id);
        if (patient == null) {
            LOGGER.debug("patient not found");
            redirectAttrs.addFlashAttribute("patientNotFound", true);
            return "redirect:/patients";
        }
        model.addAttribute("patient", patientService.findPatient(id));
        model.addAttribute("isEditMode", isEdit);
        model.addAttribute("notes", noteService.getNotesByPatient(id));
        model.addAttribute("riskLevel", assessmentService.getRiskLevel(id));
        return "patientInformations";
    }

    @GetMapping("/addPatient")
    public String createPatient(Model model) {
        LOGGER.info("GET /addPatient");
        LocalDateTime now = LocalDateTime.now();
        String today = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();
        model.addAttribute("today", today);
        model.addAttribute("newPatient", new MediScreenPatient());
        return "addPatientForm";
    }

    @PostMapping(value={"/addPatient", "/patient"})
    public String validatePatientSave(@ModelAttribute("patientToSave") MediScreenPatient patient, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
        LOGGER.info("POST " + request.getServletPath() + " patient: " + patient.toString());
        if (patientService.savePatient(patient) != null) {
            if (request.getServletPath().equalsIgnoreCase("/addPatient")) {
                redirectAttributes.addFlashAttribute("showSuccessAddMessage", true);
                return "redirect:/patients";
            }
            redirectAttributes.addFlashAttribute("showSuccessUpdateMessage", true);
            return "redirect:/patient?id=" + patient.getId();
        }
        model.addAttribute("newPatient", new MediScreenPatient());
        model.addAttribute("errorMessage", "Patient not saved, please verify informations and try again");
        return "addPatientForm";
    }
}
