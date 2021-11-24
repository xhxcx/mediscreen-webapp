package com.mediscreen.webapp.model;

import lombok.Data;

@Data
public class MediScreenNote {
    private String id;
    private Integer patientId;
    private String note;
}
