package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.MediScreenRiskLevel;
import com.mediscreen.webapp.proxy.AssessmentApiProxy;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class AssessmentServiceTests {

    @Mock
    private AssessmentApiProxy assessmentApiProxyMock;

    @InjectMocks
    private AssessmentServiceImpl assessmentService;

    @Test
    public void getRiskLevelTest(){
        Mockito.when(assessmentApiProxyMock.getRiskForPatient(1)).thenReturn(new ResponseEntity<>(MediScreenRiskLevel.BORDERLINE, HttpStatus.OK));

        Assert.assertEquals(MediScreenRiskLevel.BORDERLINE, assessmentService.getRiskLevel(1));
    }
}
