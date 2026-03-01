package ru.bsuedu.cad.lab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ru.bsuedu.cad.lab")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Configurator {


    @Bean(name = "productFileReader")
    public Reader productFileReader(){
        return new ResourceFileReader("product.csv");
    }

    @Bean(name = "categoryFileReader")
    public Reader categoryFileReader(){
        return  new ResourceFileReader("category.csv");
    }




    // ========== НАСТРОЙКА H2 БАЗЫ ДАННЫХ ==========

    @Bean
    public DataSource dataSource() {
        // Создаем встроенную H2 базу данных
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)           // Уникальное имя БД
                .setType(EmbeddedDatabaseType.H2)   // Используем H2
                .setScriptEncoding("UTF-8")         // Кодировка для русских букв
                .ignoreFailedDrops(true)             // Игнорируем ошибки удаления
                .addScript("schema.sql")             // Скрипт создания таблиц
//                .addScript("data.sql")               // Скрипт с тестовыми данными
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        // JdbcTemplate - удобная обертка для работы с БД
        return new JdbcTemplate(dataSource);
    }

    // Для красоты - метод, который выполняется при создании бина
    @Bean
    public DatabaseChecker databaseChecker(DataSource dataSource) {
        return new DatabaseChecker(dataSource);
    }
}

// ========== ВСПОМОГАТЕЛЬНЫЙ КЛАСС ДЛЯ ПРОВЕРКИ ==========
// Можно вынести в отдельный файл, но для простоты оставим здесь

class DatabaseChecker {
    private final DataSource dataSource;

    public DatabaseChecker(DataSource dataSource) {
        this.dataSource = dataSource;
        System.out.println("H2 база данных успешно создана и готова к работе!");

        // Проверяем подключение
        try (var conn = dataSource.getConnection()) {
            System.out.println("Подключение к БД: " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
        }
    }
}
