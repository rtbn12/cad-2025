package ru.bsuedu.cad.lab.service;


import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.bsuedu.cad.lab.entity.Customers;
import ru.bsuedu.cad.lab.entity.Order_Details;
import ru.bsuedu.cad.lab.entity.Orders;
import ru.bsuedu.cad.lab.entity.Products;
import ru.bsuedu.cad.lab.repository.CustomersRepository;
import ru.bsuedu.cad.lab.repository.OrderDetailsRepository;
import ru.bsuedu.cad.lab.repository.OrdersRepository;
import ru.bsuedu.cad.lab.repository.ProductsRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private  CustomersRepository customersRepository;
    @Mock
    private  ProductsRepository productsRepository;
    @Mock
    private  OrdersRepository ordersRepository;
    @Mock
    private  OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderService orderService;



    @BeforeAll
    static void startAllTest(){
        System.out.println("Start ALL test");
    }



    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
//        orderService = new OrderService(customersRepository,
//                                        productsRepository,
//                                        ordersRepository,
//                                        orderDetailsRepository);
    }

    @Test
    void createdOrderCorrected(){



        Long customerId =1L;
        LocalDateTime orderDate= LocalDateTime.now();
        String status = "norm";
        String shippingAddress = "BESTGRAD";
        Long productId =1L;
        Long quantity = 10L;
        BigDecimal productPrice = BigDecimal.valueOf(10);
        BigDecimal totalPrice = BigDecimal.valueOf(100);
        Long orderID = 900L;


        Products product = mock(Products.class);
        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));
        when(product.getPrice()).thenReturn(productPrice);

        Customers customer = mock(Customers.class);
        when(customersRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customer.getCustomerId()).thenReturn(customerId);


        when(ordersRepository.save(any(Orders.class))).thenAnswer(invocation ->{
                    Orders newOrder = invocation.getArgument(0);
                    newOrder.setOrderId(orderID);
                    return newOrder;
        });


        when(orderDetailsRepository.save(any(Order_Details.class))).thenAnswer(invocation ->{
            return invocation.getArgument(0);
        });


        Orders saveOrder = orderService.createOrder(customerId, orderDate,
                 status,  shippingAddress,
                 productId,  quantity);


        assertEquals(customerId, saveOrder.getCustomer().getCustomerId() );
        assertEquals(orderDate, saveOrder.getOrderDate());
        assertEquals(status, saveOrder.getStatus());
        assertEquals(shippingAddress, saveOrder.getShippingAddress());
        assertEquals(totalPrice,saveOrder.getTotalPrice());
        assertEquals(orderID, saveOrder.getOrderId());


        Mockito.verify(customersRepository, Mockito.times(1)).findById(customerId);
        Mockito.verify(productsRepository, Mockito.times(1)).findById(productId);
        Mockito.verify(ordersRepository, Mockito.times(1)).save(any(Orders.class));
        Mockito.verify(orderDetailsRepository, Mockito.times(1)).save(any(Order_Details.class));


    }




    @AfterEach
    void endEachTest(){
        System.out.println("END test");
    }






    @AfterAll
    static void endAllTest(){
        System.out.println("END ALL tests");
    }




}
