package ru.bsuedu.cad.lab.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bsuedu.cad.lab.JsonOrientedClasses.JsonOrientedProduct;
import ru.bsuedu.cad.lab.entity.Products;
import ru.bsuedu.cad.lab.repository.ProductsRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/productTable")
public class ProductTableServlet extends HttpServlet {

    public AnnotationConfigApplicationContext context;
    public ProductsRepository pr;
    public ObjectMapper mapper;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = (AnnotationConfigApplicationContext) getServletContext().getAttribute("springContext");

        if (context == null) {
            throw new ServletException("Spring контекст не найден в ServletContext!");
        }

        pr = context.getBean(ProductsRepository.class);

        if(pr == null){

            System.out.println("Не удалось получить бин 'ProductsRepository'");
        }

        mapper = new ObjectMapper();


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        List<Products> allProduct = pr.findAll();

        List<JsonOrientedProduct> allJsonOrientedProduct = new ArrayList<>();


        for (Products product : allProduct){

            String name = product.getName();
            Long quantity = product.getStockQuantity();
            String categoryName =  product.getCategory().getName();

            allJsonOrientedProduct.add(new JsonOrientedProduct(name, categoryName, quantity));

        }

        String json = mapper.writeValueAsString(allJsonOrientedProduct);

        out.println(json);

        out.close();
    }


}
