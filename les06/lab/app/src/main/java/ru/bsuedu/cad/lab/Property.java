package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("property")
@PropertySource("application.properties")
public class Property {

    @Value("${fileName}")
    private String name;

    public String getName() {
        return name;
    }
}
