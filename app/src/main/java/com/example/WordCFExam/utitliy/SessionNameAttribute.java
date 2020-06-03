package com.example.WordCFExam.utitliy;

public enum SessionNameAttribute {

    ProfileID("ProfileID"),
    ProfileName("ProfileName"),
    IsEditMode("IsEditMode"),
    CfExamEnabledFromHour("CfExamEnabledFromHour"),
    CfExamEnabledToHour("CfExamEnabledToHour"),
    CfExamSearchRateMinute("CfExamSearchRateMinute"),
    TextToSpeechRate("TextToSpeechRate");


    private String value;

    // getter method
    public String getValue()
    {
        return this.value;
    }

    // enum constructor - cannot be public or protected
    private SessionNameAttribute(String value)
    {
        this.value = value;
    }
}
