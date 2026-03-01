package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ccp")
public class ConcreteCategoryProvider implements CategoryProvider {


    private Reader reader;
    private CategoryParser parser;

    @Autowired
    public ConcreteCategoryProvider(@Qualifier("categoryFileReader") Reader reader, CategoryParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public List<Category> getCategories() {
        String info= reader.read();
        return parser.parse(info);
    }
}
