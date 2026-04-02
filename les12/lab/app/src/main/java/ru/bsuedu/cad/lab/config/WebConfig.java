package ru.bsuedu.cad.lab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Import(AppConfig.class)  // импортируем существующую конфигурацию
public class WebConfig implements WebMvcConfigurer {
    // здесь будут дополнительные настройки, если понадобятся
}