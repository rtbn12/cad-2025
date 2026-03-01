package ru.bsuedu.cad.lab;
import org.springframework.stereotype.Component;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("dbr")
public class DataBaseRenderer implements Renderer{


    private CategoryProvider provider;
    private ProductProvider provider2;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataBaseRenderer(CategoryProvider provider, ProductProvider provider2, JdbcTemplate jdbcTemplate) {
        this.provider = provider;
        this.provider2 = provider2;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void render() {

        List<Category> categories = provider.getCategories();
        List<Product> products = provider2.getProducts();

        String categorySql = "INSERT INTO CATEGORIES (category_id, name, description) VALUES (?, ?, ?)";
        String productSql = "INSERT INTO PRODUCTS (product_id, name, description, category_id, price, stock_quantity, image_url, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println("Процесс сохранения данных из файла в бд запущен!");

        System.out.println("Заполняю таблицу 'Categories'!");
        for(Category category : categories){

            try{
                jdbcTemplate.update(categorySql,
                        category.getCategory_id(),
                        category.getName(),
                        category.getDescription()
                );

                System.out.println("Категория " + category.getCategory_id() + " спешно сохранена в бд!");

            }catch (Exception e){
                System.out.println("Ошибка в сохранении категории " + category.getCategory_id() + "  в бд !!!");
            }

        }

        System.out.println("Заполняю таблицу 'PRODUCTS'");
        for (Product product : products) {
            try {
                jdbcTemplate.update(productSql,
                        product.getProductId(),
                        product.getName(),
                        product.getDescription(),
                        product.getCategoryId(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getImageUrl(),
                        product.getCreatedAt(),
                        product.getUpdatedAt()
                );
                System.out.println("Продукт " + product.getProductId() + " спешно сохранен в бд!");
            } catch (Exception e) {
                System.out.println("Ошибка в сохранении продукта " + product.getProductId() + "  в бд !!!");
            }
        }

        System.out.println("Сохранено категорий: " + categories.size());
        System.out.println("Сохранено продуктов: " + products.size());
        

    }




}
