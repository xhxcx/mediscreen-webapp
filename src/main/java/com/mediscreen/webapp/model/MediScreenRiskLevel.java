package com.mediscreen.webapp.model;

public enum MediScreenRiskLevel {
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In danger"),
    EARLY_ONSET("Early onset"),
    NOT_APPLICABLE("Not applicable");

    private String description;

    MediScreenRiskLevel(String s) { this.description = s; }

    public String getDescription() {
        return description;
    }
}
