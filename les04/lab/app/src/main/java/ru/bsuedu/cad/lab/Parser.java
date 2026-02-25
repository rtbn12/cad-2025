package ru.bsuedu.cad.lab;

import org.springframework.stereotype.Component;
import ru.bsuedu.cad.lab.Product;

import java.util.List;

//@Component("parser")
public interface Parser {
    public List<Product> parse(String product);
}
