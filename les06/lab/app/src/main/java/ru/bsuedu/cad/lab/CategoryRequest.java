package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


@Component("creq")
public class CategoryRequest implements Request{

    private static final Logger logger = LoggerFactory.getLogger(CategoryRequest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Category> categoryRowMapper() {
        return (rs, rowNum) -> new Category(
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }

    @Override
    public void consoleRequest() {
        logger.info("=== КАТЕГОРИИ С ТОВАРАМИ > 1 ===");

        String sql = """
            SELECT 
                c.category_id,
                c.name,
                c.description,
                COUNT(p.product_id) as product_count
            FROM CATEGORIES c
            LEFT JOIN PRODUCTS p ON c.category_id = p.category_id
            GROUP BY c.category_id, c.name, c.description
            HAVING COUNT(p.product_id) > 1
            ORDER BY product_count DESC
        """;


        List<Category> categories = jdbcTemplate.query(sql, categoryRowMapper());


        if (categories.size()>0){
            logger.info("Найдено категорий: " + categories.size());
            for (Category cat : categories) {
                logger.info("Категория: "+
                        cat.getCategory_id()+
                        " " +
                        cat.getName());
            }
        }
        else{
            logger.info("Подходящих категорий не найдено!");
        }

    }

}
