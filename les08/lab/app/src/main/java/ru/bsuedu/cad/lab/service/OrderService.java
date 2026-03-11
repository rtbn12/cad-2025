package ru.bsuedu.cad.lab.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bsuedu.cad.lab.entity.Order_Details;
import ru.bsuedu.cad.lab.entity.Orders;
import ru.bsuedu.cad.lab.entity.Products;
import ru.bsuedu.cad.lab.repository.CustomersRepository;
import ru.bsuedu.cad.lab.repository.OrderDetailsRepository;
import ru.bsuedu.cad.lab.repository.OrdersRepository;
import ru.bsuedu.cad.lab.repository.ProductsRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final CustomersRepository customersRepository;
    private final ProductsRepository productsRepository;
    private final OrdersRepository ordersRepository;
    private final OrderDetailsRepository orderDetailsRepository;


    public OrderService(
            CustomersRepository customersRepository,
            ProductsRepository productsRepository,
            OrdersRepository ordersRepository,
            OrderDetailsRepository orderDetailsRepository) {
        this.customersRepository = customersRepository;
        this.productsRepository = productsRepository;
        this.ordersRepository = ordersRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Transactional
    public Orders createOrder(Long customerId, LocalDateTime orderDate,
                            String status, String shippingAddress,
                            Long productId, Long quantity)
    {
        System.out.println("Процесс создания нового заказа и добавления его в бд запущен!");
        Orders order = new Orders();
        Order_Details ordDetail = new Order_Details();

        Products currentProduct = productsRepository.findById(productId).get();

        BigDecimal totalPrice = BigDecimal.valueOf(quantity).multiply(currentProduct.getPrice());


        order.setCustomer(customersRepository.findById(customerId).get());
        order.setOrderDate(orderDate);
        order.setTotalPrice(totalPrice);
        order.setStatus(status);
        order.setShippingAddress(shippingAddress);

         Orders savedOrder = ordersRepository.save(order);


        ordDetail.setOrder(savedOrder);
        ordDetail.setProduct(currentProduct);
        ordDetail.setQuantity(quantity);
        ordDetail.setPrice(currentProduct.getPrice());

        orderDetailsRepository.save(ordDetail);

        System.out.println("Процесс создания нового заказа и добавления его в бд завершён!");


        return savedOrder;


    }

    @Transactional(readOnly = true)
    public List<Orders> getFullOrdersList() {
        List<Orders> all = ordersRepository.findAll();

        System.out.println("\nСПИСОК ВСЕХ ЗАКАЗОВ:");
        System.out.println("========================================");

        for (Orders order : all) {
            System.out.println("Заказ #" + order.getOrderId());
            System.out.println("  Покупатель ID: " + order.getCustomer().getCustomerId());
            System.out.println("  Дата: " + order.getOrderDate());
            System.out.println("  Сумма: " + order.getTotalPrice() + " руб");
            System.out.println("  Статус: " + order.getStatus());
            System.out.println("  Адрес: " + order.getShippingAddress());
            System.out.println("----------------------------------------");
        }

        return all;
    }
}
