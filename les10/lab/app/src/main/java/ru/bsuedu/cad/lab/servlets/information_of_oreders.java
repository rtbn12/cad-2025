package ru.bsuedu.cad.lab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bsuedu.cad.lab.config.AppConfig;
import ru.bsuedu.cad.lab.entity.Orders;
import ru.bsuedu.cad.lab.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/orders")
public class information_of_oreders extends HttpServlet {

   public AnnotationConfigApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        OrderService orderService = context.getBean(OrderService.class);

        List<Orders> fullOrdersList = orderService.getFullOrdersList();

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>=====Список заказов=====</h1>");

        for(Orders order : fullOrdersList){

            out.println("<p1> "+ " Заказ #" + order.getOrderId() + " </p1>") ;
            out.println("<p2> " + "  Покупатель ID: " + order.getCustomer().getCustomerId() + " </p2>");
            out.println("<p3> " + "  Дата: " + order.getOrderDate() + " </p3>");
            out.println("<p4> " + "  Сумма: " + order.getTotalPrice() + " руб" + " </p4>");
            out.println("<p5> " + "  Статус: " + order.getStatus() + " </p5>");
            out.println("<p6> " + "  Адрес: " + order.getShippingAddress() + " </p6>");
            out.println("<p7> " + "----------------------------------------" + " </p7>");


        }

    }

    @Override
    public void destroy() {
        context.close();
    }
}
