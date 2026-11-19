package com.example.demo.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class PatientProfileUpdateFormDto {
    private boolean changePassword;
    public PatientProfileUpdateFormDto() {}
    public boolean isChangePassword() { return changePassword; }
    public void setChangePassword(boolean changePassword) { this.changePassword = changePassword; }

    @AssertTrue(message = "Введите новый пароль и его подтверждение")
    public boolean isPasswordChangeComplete() {
        return !changePassword || (patientNewPassword != null && !patientNewPassword.isBlank()
                && patientNewPasswordTwo != null && !patientNewPasswordTwo.isBlank());
    }
    @NotBlank(message = "Введите имя") @Size(max = 100)
    private String patientFirstName;
    @Size(max = 100)
    private String patientMiddleName;
    @NotBlank(message = "Введите фамилию") @Size(max = 100)
    private String patientLastName;
    private  Long patientId;
    @NotBlank(message = "Введите телефон") @Pattern(regexp = "\\+7-[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2}", message = "Формат: +7-900-123-45-67")
    private String patientPhone;
    @NotBlank(message = "Введите ОМС") @Pattern(regexp = "[0-9]{16}", message = "ОМС — ровно 16 цифр")
    private String patientOmsNumber;
    @Pattern(regexp = PasswordRules.OPTIONAL, message = PasswordRules.MESSAGE)
    private String patientNewPassword;
    private  String patientNewPasswordTwo;
    @NotNull(message = "Укажите дату рождения") @PastOrPresent(message = "Дата рождения не может быть в будущем") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate patientBirthday;

    public PatientProfileUpdateFormDto(String patientFirstName, String patientMiddleName, String patientLastName,
                                       Long patientId, String patientPhone, String patientOmsNumber,
                                       String patientNewPassword, String patientNewPasswordTwo,
                                       LocalDate patientBirthday) {

        this.patientFirstName = patientFirstName;
        this.patientMiddleName = patientMiddleName;
        this.patientLastName = patientLastName;
        this.patientId = patientId;
        this.patientPhone = patientPhone;
        this.patientOmsNumber = patientOmsNumber;
        this.patientNewPassword = patientNewPassword;
        this.patientNewPasswordTwo = patientNewPasswordTwo;
        this.patientBirthday = patientBirthday;

    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public String getPatientOmsNumber() {
        return patientOmsNumber;
    }

    public String getPatientNewPassword() {
        return patientNewPassword;
    }

    public String getPatientNewPasswordTwo() {
        return patientNewPasswordTwo;
    }

    public LocalDate getPatientBirthday() {
        return patientBirthday;
    }
    public void setPatientFirstName(String value) { this.patientFirstName = value; }
    public void setPatientMiddleName(String value) { this.patientMiddleName = value; }
    public void setPatientLastName(String value) { this.patientLastName = value; }
    public void setPatientPhone(String value) { this.patientPhone = value; }
    public void setPatientOmsNumber(String value) { this.patientOmsNumber = value; }
    public void setPatientNewPassword(String value) { this.patientNewPassword = value; }
    public void setPatientNewPasswordTwo(String value) { this.patientNewPasswordTwo = value; }
    public void setPatientBirthday(LocalDate value) { this.patientBirthday = value; }
}
