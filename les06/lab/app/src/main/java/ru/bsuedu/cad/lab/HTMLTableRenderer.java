package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component("HTML_render")
//@Primary
public class HTMLTableRenderer implements Renderer {

    private ProductProvider provider;

    @Autowired
    public HTMLTableRenderer(ProductProvider provider) {
        this.provider = provider;
    }

    @Override
    public void render() {
        System.out.println("Запущен HTML-рендер");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Product> products = provider.getProducts();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("HTML_table.html"));


            for(Product product : products){
                bw.write("\n|" + String.valueOf(product.getProductId()) + "|");
                bw.write(product.getName() + "|");
                bw.write(product.getDescription() + "|");
                bw.write(String.valueOf(product.getCategoryId()) + "|");
                bw.write(String.valueOf(product.getPrice()) + "|");
                bw.write(String.valueOf(product.getStockQuantity()) + "|");
                bw.write(product.getImageUrl() + "|" +"  ");
                bw.write(dateFormat.format(product.getCreatedAt()) + "|");
                bw.write(dateFormat.format(product.getUpdatedAt()) + "|\n");
                bw.write("<br>");
            }

            bw.close();


        } catch (IOException e) {
            System.out.println("Возникла ошибка при открытии файла!!!");
        }


    }
}
