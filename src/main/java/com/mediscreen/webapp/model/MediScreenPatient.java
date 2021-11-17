package com.mediscreen.webapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MediScreenPatient {

    private int id;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp birthDate;
    private String gender;
    private String address;
    private String phone;
}
