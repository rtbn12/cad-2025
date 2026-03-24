package ru.bsuedu.cad.lab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bsuedu.cad.lab.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;

@WebServlet("/create-order")
public class createOrder  extends HttpServlet {

    public ApplicationContext context;

    @Override
    public void init() throws ServletException {

        context = (AnnotationConfigApplicationContext) getServletContext().getAttribute("springContext");

        if (context == null) {
            throw new ServletException("Spring контекст не найден в ServletContext!");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // Начало HTML документа
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Создание заказа</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }");
        out.println("h1 { color: #333; }");
        out.println("form { background-color: white; padding: 20px; border-radius: 8px; max-width: 400px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("label { display: block; margin-top: 10px; font-weight: bold; }");
        out.println("input, select { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }");
        out.println("button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; margin-top: 20px; cursor: pointer; width: 100%; }");
        out.println("button:hover { background-color: #45a049; }");
        out.println(".back { text-align: center; margin-top: 20px; }");
        out.println(".back a { color: #4CAF50; text-decoration: none; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // Заголовок
        out.println("<h1>Создание нового заказа</h1>");

        // НАЧАЛО ФОРМЫ — самое важное!
        // method="post" — данные будут отправлены методом POST
        // action — куда отправлять (на этот же сервлет)
        out.println("<form method='post' action='" + req.getContextPath() + "/create-order'>");

        // Поле 1: ID покупателя
        out.println("<label>ID покупателя (Customer ID):</label>");
        out.println("<input type='number' name='customerId' required placeholder='Например: 2'>");
        // name="customerId" — это ключ, по которому мы потом получим значение в doPost()

        // Поле 2: ID товара
        out.println("<label>ID товара (Product ID):</label>");
        out.println("<input type='number' name='productId' required placeholder='Например: 2'>");

        // Поле 3: Количество
        out.println("<label>Количество (Quantity):</label>");
        out.println("<input type='number' name='quantity' required placeholder='Например: 3'>");

        // Поле 4: Адрес доставки
        out.println("<label>Адрес доставки:</label>");
        out.println("<input type='text' name='address' required placeholder='Например: Победы 85б'>");

        // Поле 5: Статус (выпадающий список)
        out.println("<label>Статус:</label>");
        out.println("<select name='status'>");
        out.println("<option value='НОВЫЙ'>НОВЫЙ</option>");
        out.println("<option value='В ОБРАБОТКЕ'>В ОБРАБОТКЕ</option>");
        out.println("<option value='ОТПРАВЛЕН'>ОТПРАВЛЕН</option>");
        out.println("<option value='ДОСТАВЛЕН'>ДОСТАВЛЕН</option>");
        out.println("</select>");

        // Кнопка отправки
        out.println("<button type='submit'>Создать заказ</button>");
        out.println("</form>");
        // КОНЕЦ ФОРМЫ

        // Ссылка назад
        out.println("<div class='back'>");
        out.println("<a href='" + req.getContextPath() + "/orders'>← Вернуться к списку заказов</a>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");

        out.close();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            OrderService orderService = context.getBean(OrderService.class);

            Long customerId = Long.parseLong(req.getParameter("customerId"));
            LocalDateTime orderDate = LocalDateTime.now();
            String status = req.getParameter("status");
            String shippingAddress = req.getParameter("address");
            Long productId = Long.parseLong(req.getParameter("productId"));
            Long quantity = Long.parseLong(req.getParameter("quantity"));

            orderService.createOrder(customerId, orderDate, status, shippingAddress, productId, quantity);

            resp.sendRedirect(req.getContextPath() + "/orders");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<h1>Ошибка при создании заказа</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<a href='" + req.getContextPath() + "/create-order'>Вернуться к форме</a>");
            out.close();
        }
    }


}
