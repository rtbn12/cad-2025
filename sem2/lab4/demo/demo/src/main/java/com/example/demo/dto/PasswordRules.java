package com.example.demo.dto;

public final class PasswordRules {
    private PasswordRules() {}
    public static final String REQUIRED = "(?=.*[A-Z])(?=.*[!@#$%^&*()_+=?.,:;~-])[A-Za-z0-9!@#$%^&*()_+=?.,:;~-]{10,72}";
    public static final String OPTIONAL = "(?:|" + REQUIRED + ")";
    public static final String MESSAGE = "От 10 до 72 символов: латиница, минимум одна заглавная буква и спецсимвол !@#$%^&*()_+=?.,:;~-; без пробелов";
}
