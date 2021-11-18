package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.model.MediScreenPatient;
import com.mediscreen.webapp.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class PatientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

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
    public String findPatientById(@RequestParam int id, Model model, RedirectAttributes redirectAttrs){
        LOGGER.info("GET /patient patientId = " + id);
        MediScreenPatient patient = patientService.findPatient(id);
        if (patient == null) {
            LOGGER.debug("patient not found");
            redirectAttrs.addFlashAttribute("patientNotFound", true);
            return "redirect:/patients";
        }
        model.addAttribute("patient", patientService.findPatient(id));
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

    @PostMapping("/addPatient")
    public String validatePatientCreation(@ModelAttribute("newPatient") MediScreenPatient patient, RedirectAttributes redirectAttributes, Model model) {
        LOGGER.info("POST /addPatient patient: " + patient.toString());
        if (patientService.createPatient(patient) != null) {
            redirectAttributes.addFlashAttribute("showSuccessAddMessage", true);
            return "redirect:/patients";
        }
        model.addAttribute("errorMessage", "Patient not created, please verify informations and try again");
        return "addPatientForm";
    }
}
