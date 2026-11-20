package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PatientSearchFormDto {
    @NotNull(message = "Выберите способ поиска")
    private PatientSearchType searchType;
    @NotBlank(message = "Введите ОМС или телефон")
    @Size(max = 16, message = "Введите 16 цифр ОМС или телефон в формате +7-900-123-45-67")
    private String searchValue;

    public PatientSearchType getSearchType() { return searchType; }
    public void setSearchType(PatientSearchType searchType) { this.searchType = searchType; }
    public String getSearchValue() { return searchValue; }
    public void setSearchValue(String searchValue) { this.searchValue = searchValue; }
}
