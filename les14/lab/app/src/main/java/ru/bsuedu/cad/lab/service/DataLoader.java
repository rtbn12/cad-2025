package ru.bsuedu.cad.lab.service;


import jakarta.annotation.PostConstruct;
import jdk.jfr.Category;
import org.springframework.stereotype.Service;
import ru.bsuedu.cad.lab.entity.Categories;
import ru.bsuedu.cad.lab.entity.Customers;
import ru.bsuedu.cad.lab.entity.Products;
import ru.bsuedu.cad.lab.repository.CategoriesRepository;
import ru.bsuedu.cad.lab.repository.CustomersRepository;
import ru.bsuedu.cad.lab.repository.ProductsRepository;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DataLoader {


    private final CustomersRepository customersRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;


    public DataLoader(
            CustomersRepository customersRepository,
            ProductsRepository productsRepository,
            CategoriesRepository categoriesRepository) {
        this.customersRepository = customersRepository;
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;

    }

    public void load(){

        String line;

        System.out.println("НАЧИНАЮ ПРОЦЕСС ЗАГРУЗКИ КЛИЕНТОВ, ТОВАРОВ И КАТЕГОРИЙ ИЗ CSV ФАЙЛОВ В БД");



        try {
//            BufferedReader CategoryReader = new BufferedReader(new FileReader("src/main/resources/category.csv"));
            BufferedReader CategoryReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("category.csv")));

            CategoryReader.readLine();

            while((line = CategoryReader.readLine())!= null){
                Categories category = new Categories();

                String[] info = line.split(",");

                category.setCategoryId(Long.parseLong(info[0]));
                category.setName(info[1]);
                category.setDescription(info[2]);
                category.setProducts(new ArrayList<>());

                categoriesRepository.save(category);

            }

            CategoryReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл с категориями!");
        } catch (IOException e) {
            System.out.println("Не удалось закрыть файл с категориями без создания ошибок!");
        }

        try {
//            BufferedReader customerReader = new BufferedReader(new FileReader("src/main/resources/customer.csv"));
            BufferedReader customerReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("customer.csv")));

            customerReader.readLine();

            while((line = customerReader.readLine())!= null){
                Customers customer = new Customers();
                String[] info = line.split(",");
                customer.setCustomerId(Long.parseLong(info[0]));
                customer.setName(info[1]);
                customer.setEmail(info[2]);
                customer.setPhone(info[3]);
                customer.setAddress(info[4]);
                customer.setOrders(new ArrayList<>());
                customersRepository.save(customer);
            }

            customerReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл с клиентами!");
        } catch (IOException e) {
            System.out.println("Не удалось закрыть файл с клиентами без создания ошибок!");
        }


        try {
//            BufferedReader productReader = new BufferedReader(new FileReader("src/main/resources/product.csv"));
            BufferedReader productReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("product.csv")));

            productReader.readLine();

            while((line = productReader.readLine())!= null){
                Products product = new Products();
                String[] info = line.split(",");
                product.setProductId(Long.parseLong(info[0]));
                product.setName(info[1]);
                product.setDescription(info[2]);
                product.setCategory(categoriesRepository.findById(Long.parseLong(info[3])).get());
                product.setPrice(new BigDecimal(info[4]));
                product.setStockQuantity(Long.parseLong(info[5]));
                product.setImageUrl(info[6]);
                product.setCreatedAt(LocalDateTime.parse(info[7] + "T00:00:00"));
                product.setUpdatedAt(LocalDateTime.parse(info[8] + "T00:00:00"));
                product.setOrderDetails(new ArrayList<>());

                productsRepository.save(product);

            }

            productReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл с продуктами!");
        } catch (IOException e) {
            System.out.println("Не удалось закрыть файл с продуктами без создания ошибок!");
        }

        System.out.println("Процесс окончен!");
        
        


    }

    @PostConstruct
    public void init() {
        load();
        System.out.println("=== Данные загружены при старте приложения ===");
    }

}
