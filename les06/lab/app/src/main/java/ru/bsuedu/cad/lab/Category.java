package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("category")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Category {

    int category_id;
    String name;
    String description;

    public int getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category(int category_id, String name, String description) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
    }
}
