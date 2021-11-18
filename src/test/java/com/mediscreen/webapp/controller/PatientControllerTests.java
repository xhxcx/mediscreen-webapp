package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.model.MediScreenPatient;
import com.mediscreen.webapp.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PatientControllerTests {

    @MockBean
    private PatientService patientServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void viewHomeShouldDisplayIndexTpl() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void viewAllPatientsShouldDisplayPatientsTplAndAddToModelPatientList() throws Exception {
        MediScreenPatient patient = new MediScreenPatient();
        patient.setId(1);
        patient.setFirstName("firstName");
        patient.setLastName("lastName");
        patient.setBirthDate(LocalDate.of(2021, 11, 15));
        patient.setGender("X");
        patient.setAddress("address");
        patient.setPhone("phone");

        Mockito.when(patientServiceMock.getAllPatients()).thenReturn(Collections.singletonList(patient));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patientList"));
    }

    @Test
    public void findPatientByIdShouldDisplaypatientInformationsTplAndAddToModelPatient() throws Exception {
        MediScreenPatient patient = new MediScreenPatient();
        patient.setId(1);
        patient.setFirstName("firstName");
        patient.setLastName("lastName");
        patient.setBirthDate(LocalDate.of(2021, 11, 15));
        patient.setGender("X");
        patient.setAddress("address");
        patient.setPhone("phone");

        Mockito.when(patientServiceMock.findPatient(1)).thenReturn(patient);

        mockMvc.perform(get("/patient").param("id", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("patientInformations"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    public void findPatientByIdShouldRedirectToPatientListTpl() throws Exception {

        Mockito.when(patientServiceMock.findPatient(1)).thenReturn(null);

        mockMvc.perform(get("/patient").param("id", String.valueOf(1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }
}
