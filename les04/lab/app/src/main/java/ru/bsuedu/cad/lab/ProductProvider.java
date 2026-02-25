package ru.bsuedu.cad.lab;

import org.springframework.stereotype.Component;
import ru.bsuedu.cad.lab.Product;

import java.util.List;

//@Component("pp")
public interface ProductProvider {
    public List<Product> getProducts();
}
