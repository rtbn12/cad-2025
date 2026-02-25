package ru.bsuedu.cad.lab;

import org.springframework.stereotype.Component;
import ru.bsuedu.cad.lab.Parser;
import ru.bsuedu.cad.lab.Product;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("csvp")
public class CSVParser implements Parser {
    @Override
    public List<Product> parse(String information) {

        List<Product> products = new ArrayList<>();

        String[] strs = information.split("\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i =1; i<strs.length; i++){

            String[] values = strs[i].split(",");

             long productId = Long.parseLong(values[0].trim());
             String name = values[1].trim();
             String description = values[2].trim();
             int categoryId = Integer.parseInt(values[3].trim());
             BigDecimal price = new BigDecimal(values[4].trim());
             int stockQuantity = Integer.parseInt(values[5].trim());
             String imageUrl = values[6].trim();
            Date createdAt = null;
            try {
                createdAt = sdf.parse(values[7].trim());
            } catch (ParseException e) {
                System.out.println("ошибка в парсинге даты created_at");
            }
            Date updatedAt = null;
            try {
                updatedAt = sdf.parse(values[8].trim());
            } catch (ParseException e) {
                System.out.println("ошибка в парсинге даты updated_at");
            }

            Product product = new Product(productId, name,  description, categoryId,
                     price, stockQuantity, imageUrl, createdAt, updatedAt);

             products.add(product);

        }

        return products;
    }
}
