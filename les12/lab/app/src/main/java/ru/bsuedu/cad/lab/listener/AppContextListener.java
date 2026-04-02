//package ru.bsuedu.cad.lab.listener;
//
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import ru.bsuedu.cad.lab.config.AppConfig;
//
////@WebListener
//public class AppContextListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//        // Сохраняем его в ServletContext (глобальное хранилище Tomcat)
//        sce.getServletContext().setAttribute("springContext", context);
//
//        System.out.println("=== Spring контекст создан и сохранен в ServletContext ===");
//
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        // Закрываем контекст при остановке приложения
//        AnnotationConfigApplicationContext context =
//                (AnnotationConfigApplicationContext) sce.getServletContext().getAttribute("springContext");
//        if (context != null) {
//            context.close();
//            System.out.println("=== Spring контекст закрыт ===");
//        }
//    }
//}
