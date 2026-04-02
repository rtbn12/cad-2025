//package ru.bsuedu.cad.lab.servlets;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import ru.bsuedu.cad.lab.config.AppConfig;
//import ru.bsuedu.cad.lab.entity.Orders;
//import ru.bsuedu.cad.lab.service.DataLoader;
//import ru.bsuedu.cad.lab.service.OrderService;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet("/orders")
//public class information_of_oreders extends HttpServlet {
//
//   public AnnotationConfigApplicationContext context;
//
//    @Override
//    public void init() throws ServletException {
//
//        System.out.println("=== LoadDataBaseServlet инициализирован ===");
//
//        context = (AnnotationConfigApplicationContext) getServletContext().getAttribute("springContext");
//
//        if (context == null) {
//            throw new ServletException("Spring контекст не найден в ServletContext!");
//        }
//
//
//        try {
//            DataLoader dataLoader = context.getBean(DataLoader.class);
//            System.out.println("=== Starting the data download ===");
//            dataLoader.load();
//            System.out.println("=== ЗData download completed ===");
//        } catch (Exception e) {
//            System.out.println("=== \n" +
//                    "ERROR when uploading data ===");
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        OrderService orderService = context.getBean(OrderService.class);
//
//        DataLoader dataLoader =  context.getBean(DataLoader.class);
//        dataLoader.load();
//
//        List<Orders> fullOrdersList = orderService.getFullOrdersList();
//
//        resp.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//        out.println("<p> Данные загружены из базы данных! </p>");
//        out.println("<p> Количество заказов в списке: " + fullOrdersList.size() + " </p>");
//        out.println("<h1>=====Список заказов=====</h1>");
//
//        System.out.println("fullOrdersList size from getFullOrdersList(): " + fullOrdersList.size());
//        for (Orders order : fullOrdersList) {
//            System.out.println("Order from getFullOrdersList: " + order.getOrderId());
//        }
//
//        out.println("<!DOCTYPE html>");
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<meta charset='UTF-8'>");
//        out.println("<title>Список заказов</title>");
//        out.println("<style>");
//        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
//        out.println("table { border-collapse: collapse; width: 100%; }");
//        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
//        out.println("th { background-color: #4CAF50; color: white; }");
//        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
//        out.println("tr:hover { background-color: #ddd; }");
//        out.println(".button { background-color: #4CAF50; border: none; color: white; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; margin: 20px 0; cursor: pointer; }");
//        out.println("</style>");
//        out.println("</head>");
//        out.println("<body>");
//        out.println("<h1>Список заказов</h1>");
//        out.println("<p>Всего заказов: " + fullOrdersList.size() + "</p>");
//
//        out.println("<table>");
//        out.println("<tr>");
//        out.println("<th>№ заказа</th>");
//        out.println("<th>Покупатель ID</th>");
//        out.println("<th>Дата</th>");
//        out.println("<th>Сумма (руб)</th>");
//        out.println("<th>Статус</th>");
//        out.println("<th>Адрес доставки</th>");
//        out.println("</tr>");
//
//        for(Orders order : fullOrdersList){
//            out.println("<tr>");
//            out.println("<td>" + order.getOrderId() + "</td>");
//            out.println("<td>" + order.getCustomer().getCustomerId() + "</td>");
//            out.println("<td>" + order.getOrderDate() + "</td>");
//            out.println("<td>" + order.getTotalPrice() + "</td>");
//            out.println("<td>" + order.getStatus() + "</td>");
//            out.println("<td>" + order.getShippingAddress() + "</td>");
//            out.println("</tr>");
//        }
//
//        out.println("</table>");
//
//        out.println("<a href='" + req.getContextPath() + "/create-order' class='button'>Создать новый заказ</a>");
//
//        out.println("<a href='" + req.getContextPath() + "/productTable' class='button'>Подробнее о продуктах</a>");
//
//        out.println("</body>");
//        out.println("</html>");
//         out.close();
//
//    }
//    // http://localhost:8080/my-app/orders
//
//    @Override
//    public void destroy() {
//        context.close();
//    }
//}
