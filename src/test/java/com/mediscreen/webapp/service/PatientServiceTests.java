package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenPatient;
import com.mediscreen.webapp.proxy.PatientApiProxy;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class PatientServiceTests {

    @Mock
    private PatientApiProxy patientApiProxyMock;

    @InjectMocks
    private PatientServiceImpl patientService;

    private static final MediScreenPatient patient = new MediScreenPatient();

    @BeforeAll
    public static void setUp(){
        patient.setId(1);
        patient.setFirstName("firstName");
        patient.setLastName("lastName");
        patient.setBirthDate(LocalDate.of(2021, 11, 15));
        patient.setGender("X");
        patient.setAddress("address");
        patient.setPhone("phone");
    }

    @Test
    public void getAllPatientsTest(){
        Mockito.when(patientApiProxyMock.getAll()).thenReturn(new ResponseEntity<>(Collections.singletonList(patient), HttpStatus.OK));

        List<MediScreenPatient> resultList = patientService.getAllPatients();

        Assert.assertEquals(1,resultList.size());
        Assert.assertEquals(patient.getId(), resultList.get(0).getId());
    }

    @Test
    public void findPatientTest(){
        Mockito.when(patientApiProxyMock.findPatient(1)).thenReturn(new ResponseEntity<>(patient, HttpStatus.OK));

        Assert.assertEquals(patient.getId(), patientService.findPatient(1).getId());
    }
    @Test
    public void findPatientNotFoundTest(){
        Mockito.when(patientApiProxyMock.findPatient(1)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        Assert.assertNull(patientService.findPatient(1));
    }

    @Test
    public void savePatientTest(){
        MediScreenPatient apiReturnPatient = new MediScreenPatient();
        apiReturnPatient.setId(1);
        apiReturnPatient.setFirstName("firstName");
        apiReturnPatient.setLastName("lastName");
        apiReturnPatient.setBirthDate(LocalDate.of(2021, 11, 15));
        apiReturnPatient.setGender("X");
        apiReturnPatient.setAddress("address");
        apiReturnPatient.setPhone("phone");

        patient.setId(null);

        Mockito.when(patientApiProxyMock.createPatient(patient)).thenReturn(new ResponseEntity<>(apiReturnPatient, HttpStatus.CREATED));

        MediScreenPatient result = patientService.savePatient(patient);
        Assert.assertEquals(apiReturnPatient.getId(), result.getId());
    }

    @Test
    public void savePatientFailTest(){
        patient.setId(null);
        Mockito.when(patientApiProxyMock.createPatient(patient)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        MediScreenPatient result = patientService.savePatient(patient);
        Assert.assertNull(result);
    }
}
