package ru.bsuedu.cad.lab.service;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bsuedu.cad.lab.config.TestConfigDB;
import ru.bsuedu.cad.lab.entity.*;
import ru.bsuedu.cad.lab.repository.CategoriesRepository;
import ru.bsuedu.cad.lab.repository.CustomersRepository;
import ru.bsuedu.cad.lab.repository.OrdersRepository;
import ru.bsuedu.cad.lab.repository.ProductsRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfigDB.class)
@Transactional
@Rollback
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {


        System.out.println("=== Сохраняем клиента ===");
        Customers testCustomer = new Customers();
        String customerName = "probnik";
        String customerEmail = "ppp@probnik.ru";
        String customerPhone = "12345678999";
        String customerAddress = "1q345";
        List<Orders> customerOrders = new ArrayList<>();
        testCustomer.setOrders(customerOrders);
        testCustomer.setAddress(customerAddress);
        testCustomer.setName(customerName);
        testCustomer.setPhone(customerPhone);
        testCustomer.setEmail(customerEmail);
        Customers savedCustomer = customersRepository.save(testCustomer);
        System.out.println("Клиент сохранён, ID: " + savedCustomer.getCustomerId());

        System.out.println("=== Сохраняем категорию ===");
        Categories testCategories = new Categories();
        String categoryName = "probCategory";
        String categoryDescription = "probCategoryDescription";
        List<Products> categoryProducts = new ArrayList<>();
        testCategories.setProducts(categoryProducts);
        testCategories.setName(categoryName);
        testCategories.setDescription(categoryDescription);
        Categories savedCategory = categoriesRepository.save(testCategories);
        System.out.println("Категория сохранена, ID: " + savedCategory.getCategoryId());

        System.out.println("=== Сохраняем продукт ===");
        Products testProduct = new Products();
        String productName = "probnikProduct";
        String productDescription = "Probnik";
        Categories productCategory = savedCategory;
        BigDecimal productPrice = BigDecimal.valueOf(1000);
        Long productStockQuantity = 10L;
        String productImageUrl = "probURL";
        LocalDateTime productCreatedAt = LocalDateTime.now();
        LocalDateTime productUpdatedAt = LocalDateTime.now();
        List<Order_Details> productOrderDetails = new ArrayList<>();
        testProduct.setOrderDetails(productOrderDetails);
        testProduct.setUpdatedAt(productUpdatedAt);
        testProduct.setCreatedAt(productCreatedAt);
        testProduct.setPrice(productPrice);
        testProduct.setImageUrl(productImageUrl);
        testProduct.setName(productName);
        testProduct.setCategory(productCategory);
        testProduct.setDescription(productDescription);
        testProduct.setStockQuantity(productStockQuantity);
        productsRepository.save(testProduct);
        System.out.println("Продукт сохранён");
    }

    @Test
    void testCreateOrderAndFindByIdOK(){

        Customers customer = customersRepository.findAll().get(0);

        Products product = productsRepository.findAll().get(0);

        orderService.createOrder(customer.getCustomerId(),
                                           LocalDateTime.now(),
                                    "now",
                            "bestgrad",
                                           product.getProductId(),
                                   10L);


        List<Orders> allOrders = ordersRepository.findAll();
        assertEquals(1, allOrders.size());

        Orders currentOrder = allOrders.get(0);
        assertEquals(customer.getCustomerId(), currentOrder.getCustomer().getCustomerId());
        assertEquals("now", currentOrder.getStatus());
        assertEquals("bestgrad", currentOrder.getShippingAddress());

        Optional<Orders> orderFound = ordersRepository.findById(currentOrder.getOrderId());
        assertEquals(currentOrder.getTotalPrice(), orderFound.get().getTotalPrice());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        assertThrows(Exception.class, () -> {
            orderService.createOrder(
                    999999L,
                    LocalDateTime.now(),
                    "now",
                    "bestgrad",
                    1L,
                    10L
            );
        });
    }
}
