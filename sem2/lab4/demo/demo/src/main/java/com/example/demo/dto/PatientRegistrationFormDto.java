package com.example.demo.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PatientRegistrationFormDto {

    @NotBlank(message = "Введите логин")
    @Size(max = 100, message = "Логин должен содержать не более 100 символов")
    private String login;

    @NotBlank(message = "Введите пароль")
    private String password;

    @NotBlank(message = "Введите пароль повторно")
    private String passwordConfirmation;

    @NotBlank(message = "Введите имя")
    @Size(max = 100, message = "Имя должно содержать не более 100 символов")
    private String firstName;

    @Size(max = 100, message = "Отчество должно содержать не более 100 символов")
    private String middleName;

    @NotBlank(message = "Введите фамилию")
    @Size(max = 100, message = "Фамилия должна содержать не более 100 символов")
    private String lastName;

    @NotNull(message = "Укажите дату рождения")
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @NotBlank(message = "Введите номер полиса ОМС")
    @Pattern(regexp = "[0-9]{16}", message = "Номер ОМС должен содержать ровно 16 цифр")
    private String omsNumber;

    @NotBlank(message = "Введите телефон")
    @Pattern(
            regexp = "\\+7-[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2}",
            message = "Введите телефон в формате +7-900-123-45-67"
    )
    private String phone;

    public PatientRegistrationFormDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getOmsNumber() {
        return omsNumber;
    }

    public void setOmsNumber(String omsNumber) {
        this.omsNumber = omsNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
