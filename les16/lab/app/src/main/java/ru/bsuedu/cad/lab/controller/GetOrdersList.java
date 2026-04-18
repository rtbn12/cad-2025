package ru.bsuedu.cad.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuedu.cad.lab.entity.Orders;
import ru.bsuedu.cad.lab.repository.OrdersRepository;
import ru.bsuedu.cad.lab.service.CreateOrderRequest;
import ru.bsuedu.cad.lab.service.OrderService;
import ru.bsuedu.cad.lab.service.UpdateOrderRequest;

import java.util.List;

@RestController
@RequestMapping("/Oorders")
public class GetOrdersList {

    @Autowired
    OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping
    public List<Orders> getOrdersList() {
        return orderService.getFullOrdersList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable("id") Long id) {  // <-- ЯВНО УКАЖИ ИМЯ
        return ordersRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Orders createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request.getCustomerId(), request.getOrderDate(),
                request.getStatus(), request.getShippingAddress(),
                request.getProductId(), request.getQuantity());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id) {  // <-- ЯВНО УКАЖИ ИМЯ
        ordersRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable("id") Long id, @RequestBody UpdateOrderRequest request) {  // <-- ЯВНО УКАЖИ ИМЯ
        return orderService.updateOrder(id, request.getStatus(), request.getShippingAddress());
    }
}