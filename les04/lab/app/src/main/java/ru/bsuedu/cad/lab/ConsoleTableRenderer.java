package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsuedu.cad.lab.ProductProvider;
import ru.bsuedu.cad.lab.Renderer;

import java.text.SimpleDateFormat;
import java.util.List;

@Component("ctr")
public class ConsoleTableRenderer implements Renderer {

    private ProductProvider provider;

    @Autowired
    public ConsoleTableRenderer(ProductProvider provider) {
        this.provider = provider;
    }

    @Override
    public void render(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Product> products = provider.getProducts();

        System.out.println("+-----+---------------------+--------------------------------+" +
                "-----+-----+-----+------------------------------+--------------+" +
                "--------------+");


        for(Product product: products){

            System.out.print("\n|" + String.valueOf(product.getProductId()) + "|");
            System.out.print(product.getName() + "|");
            System.out.print(product.getDescription() + "|");
            System.out.print(String.valueOf(product.getCategoryId()) + "|");
            System.out.print(String.valueOf(product.getPrice()) + "|");
            System.out.print(String.valueOf(product.getStockQuantity()) + "|");
            System.out.print(product.getImageUrl() + "|" +" ");
            System.out.print(dateFormat.format(product.getCreatedAt()) + "|");
            System.out.print(dateFormat.format(product.getUpdatedAt()) + "|\n");

        }

        System.out.println("+-----+---------------------+--------------------------------+" +
                "-----+-----+-----+------------------------------+--------------+" +
                "--------------+");


    }
}
