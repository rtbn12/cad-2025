//package ru.bsuedu.cad.lab.servlets;
//
//import jakarta.servlet.ServletConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import ru.bsuedu.cad.lab.config.AppConfig;
//import ru.bsuedu.cad.lab.service.DataLoader;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@WebServlet(value = "/dbl",loadOnStartup = 1)
////@WebServlet(value = "/dbl", loadOnStartup = 1)
//public class LoadDataBaseServlet extends HttpServlet {
//
//    public AnnotationConfigApplicationContext context;
//
//    @Override
//    public void init() throws ServletException {
//        System.out.println("=== LoadDataBaseServlet инициализирован ===");
//        context = new AnnotationConfigApplicationContext(AppConfig.class);
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
//        resp.setContentType("text/html;charset=UTF-8");
//
//        PrintWriter out = resp.getWriter();
//
//        out.println("<p> Данные загружены из базы данных! </p>");
//    }
//
//    // http://localhost:8080/my-app/orders
//
//    @Override
//    public void destroy() {
//        context.close();
//    }
//}
