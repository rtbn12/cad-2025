package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsuedu.cad.lab.Parser;
import ru.bsuedu.cad.lab.Product;
import ru.bsuedu.cad.lab.ProductProvider;
import ru.bsuedu.cad.lab.Reader;

import java.util.ArrayList;
import java.util.List;

@Component("cpp")
public class ConcreteProductProvider implements ProductProvider {

    private Reader reader;
    private Parser parser;

    @Autowired
    public ConcreteProductProvider(Reader reader, Parser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public List<Product> getProducts() {

       String information = reader.read();
       return parser.parse(information);
    }
}
