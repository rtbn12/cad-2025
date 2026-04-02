//package ru.bsuedu.cad.lab.servlets;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import ru.bsuedu.cad.lab.config.AppConfig;
//import ru.bsuedu.cad.lab.entity.Orders;
//import ru.bsuedu.cad.lab.service.DataLoader;
//import ru.bsuedu.cad.lab.service.OrderService;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet( value = "/zaglusca" , loadOnStartup = 2)
//public class ZagluscaCreater extends HttpServlet {
//
//
//
//    @Override
//    public void init() throws ServletException {
//        System.out.println("Запуск клиента магазина зоо-товаров!!!!!!!!");
//
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//        DataLoader dataLoader =  context.getBean(DataLoader.class);
//        dataLoader.load();
//
//        OrderService orderService = context.getBean(OrderService.class);
//
//
//        Orders createdOrder = orderService.createOrder(
//                2L,
//                LocalDateTime.now(),
//                "НОВЫЙ",
//                "Победы 85б",
//                2L,
//                3L
//        );
//        Orders createdOrder2 = orderService.createOrder(
//                3L,
//                LocalDateTime.now(),
//                "НОВЫЙ",
//                "Победы 85б",
//                3L,
//                3L
//        );
//
//        List<Orders> ordersList = new ArrayList<>();
//
//        ordersList.add(createdOrder);
//        ordersList.add(createdOrder2);
//
//        for (Orders order : ordersList){
//            System.out.println("Созданный заказ:");
//            System.out.println("Заказ #" + order.getOrderId());
//            System.out.println("  Покупатель ID: " + order.getCustomer().getCustomerId());
//            System.out.println("  Дата: " + order.getOrderDate());
//            System.out.println("  Сумма: " + order.getTotalPrice() + " руб");
//            System.out.println("  Статус: " + order.getStatus());
//            System.out.println("  Адрес: " + order.getShippingAddress());
//            System.out.println("----------------------------------------");
//        }
//        context.close();
//    }
//
//
//}
