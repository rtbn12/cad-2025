package ru.bsuedu.cad.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // соответствует /WEB-INF/templates/login.html
    }
}

//http://localhost:8080/app/web/orders     ← список заказов
//http://localhost:8080/app/web/test       ← тестовый эндпоинт
//http://localhost:8080/app/               ← корень приложения
//http://localhost:8080/app/login          ← страница входа
//http://localhost:8080/app/Oorders    rest