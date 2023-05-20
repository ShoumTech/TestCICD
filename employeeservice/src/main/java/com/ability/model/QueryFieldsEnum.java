package com.ability.model;

public enum QueryFieldsEnum {

    PROFILE("PROFILE"),
    EMERGENCY_CONTACT("EMERGENCY_CONTACT"), 
    TRANSPORT("TRANSPORT"), 
    AVAILABILITY("AVAILABILITY"),
    EDUCATION("EDUCATION"), 
    SKILLSET("SKILLSET"), 
    EXPERIENCE("EXPERIENCE"),
    REFERENCE("REFERENCE"),
    CONSENT("CONSENT"),
    DECLARATION("DECLARATION"),
    ALL("ALL");

    private String value;

    QueryFieldsEnum(String value) {
        this.value = value;
    }
    public String getField() {
        return this.value;
    }
}
