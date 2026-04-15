package ru.bsuedu.cad.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bsuedu.cad.lab.entity.Orders;
import ru.bsuedu.cad.lab.service.CreateOrderRequest;
import ru.bsuedu.cad.lab.service.OrderService;

@Controller
@RequestMapping("/web")
public class WebController {

    private final OrderService orderService;

    public WebController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getFullOrdersList());
        return "order-list";
    }

    // GET форма для создания заказа
    @GetMapping("/create-test")
    public String showCreateForm(Model model) {
        System.out.println("=== GET /create-test called! ===");
        model.addAttribute("order", new CreateOrderRequest());
        return "order-create";
    }

    // POST обработка формы
    @PostMapping("/create-test")
    public String createOrder(@ModelAttribute CreateOrderRequest request) {
        System.out.println("=== POST /create-test called! ===");
        System.out.println("CustomerId: " + request.getCustomerId());
        System.out.println("Status: " + request.getStatus());
        System.out.println("Address: " + request.getShippingAddress());
        System.out.println("ProductId: " + request.getProductId());
        System.out.println("Quantity: " + request.getQuantity());

        orderService.createOrder(
                request.getCustomerId(),
                request.getOrderDate(),
                request.getStatus(),
                request.getShippingAddress(),
                request.getProductId(),
                request.getQuantity()
        );
        return "redirect:/web/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Orders order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order-edit";
    }

    @PostMapping("/orders/edit/{id}")
    public String updateOrder(@PathVariable("id") Long id,
                              @RequestParam String status,
                              @RequestParam String shippingAddress) {
        orderService.updateOrder(id, status, shippingAddress);
        return "redirect:/web/orders";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/web/orders";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "WebController works!";
    }

    // Тестовый POST метод
    @PostMapping("/test-post")
    @ResponseBody
    public String testPost() {
        return "POST method works!";
    }
}

//http://localhost:8080/zooshop/web/orders
//http://localhost:8080/zooshop/web/orders