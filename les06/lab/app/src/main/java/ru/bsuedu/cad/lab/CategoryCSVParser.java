package ru.bsuedu.cad.lab;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("CCSVp")
public class CategoryCSVParser implements CategoryParser {
    @Override
    public List<Category> parse(String information) {
        List<Category> categories = new ArrayList<>();

        String[] strs = information.split("\n");

        for(int i =1; i<strs.length; i++){

            String[] values = strs[i].split(",");

            int category_id = Integer.parseInt(values[0].trim());
            String name = values[1].trim();
            String description = values[2].trim();

            Category category = new Category(category_id,name,description);

            categories.add(category);

        }

        return categories;
    }
}
