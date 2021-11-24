package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.model.MediScreenPatient;
import com.mediscreen.webapp.service.PatientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PatientControllerTests {

    @MockBean
    private PatientService patientServiceMock;

    @Autowired
    private MockMvc mockMvc;

    private static final MediScreenPatient patient = new MediScreenPatient();

    @BeforeAll
    static void setUp(){
        patient.setId(1);
        patient.setFirstName("firstName");
        patient.setLastName("lastName");
        patient.setBirthDate(LocalDate.of(2021, 11, 15));
        patient.setGender("X");
        patient.setAddress("address");
        patient.setPhone("phone");
    }

    @Test
    public void viewHomeShouldDisplayIndexTpl() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void viewAllPatientsShouldDisplayPatientsTplAndAddToModelPatientList() throws Exception {

        Mockito.when(patientServiceMock.getAllPatients()).thenReturn(Collections.singletonList(patient));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patientList"));
    }

    @Test
    public void findPatientByIdShouldDisplaypatientInformationsTplAndAddToModelPatientAndNotes() throws Exception {
        Mockito.when(patientServiceMock.findPatient(1)).thenReturn(patient);

        mockMvc.perform(get("/patient").param("id", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("patientInformations"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("notes"));
    }

    @Test
    public void findPatientByIdShouldRedirectToPatientListTpl() throws Exception {

        Mockito.when(patientServiceMock.findPatient(1)).thenReturn(null);

        mockMvc.perform(get("/patient").param("id", String.valueOf(1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    public void createPatientShouldDisplayAddPatientFormAndAddToModelNewPatient() throws Exception {
        mockMvc.perform(get("/addPatient"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPatientForm"))
                .andExpect(model().attribute("newPatient", new MediScreenPatient()));
    }

    @Test
    public void validatePatientCreationShouldRedirectToPatientListTpl() throws Exception {
        Mockito.when(patientServiceMock.savePatient(any(MediScreenPatient.class))).thenReturn(patient);

        patient.setId(null);
        mockMvc.perform(post("/addPatient").flashAttr("patientToSave", patient).servletPath("/addPatient"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    public void validatePatientCreationShouldDisplayAddForm() throws Exception {
        Mockito.when(patientServiceMock.savePatient(any(MediScreenPatient.class))).thenReturn(null);

        mockMvc.perform(post("/addPatient").flashAttr("patientToSave", patient).servletPath("/addPatient"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPatientForm"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    public void validatePatientUpdateShouldRedirectToPatientInfoTplAndShowSuccessMessage() throws Exception {
        Mockito.when(patientServiceMock.savePatient(any(MediScreenPatient.class))).thenReturn(patient);

        mockMvc.perform(post("/patient").flashAttr("patientToSave", patient).servletPath("/patient"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient?id=" + patient.getId()))
                .andExpect(flash().attributeExists("showSuccessUpdateMessage"));
    }
}
