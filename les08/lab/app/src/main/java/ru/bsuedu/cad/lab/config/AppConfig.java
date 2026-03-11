package ru.bsuedu.cad.lab.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("ru.bsuedu.cad.lab")
@EnableJpaRepositories("ru.bsuedu.cad.lab.repository")  // для Spring Data
@EnableTransactionManagement  // для @Transactional
public class AppConfig {


    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:~/lab4;DB_CLOSE_ON_EXIT=FALSE");
        config.setUsername("sa");
        config.setPassword("");
        config.setDriverClassName("org.h2.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        return new HikariDataSource(config);
    }


    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.hbm2ddl.auto", "create-drop");  // автосоздание схемы!
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        hibernateProp.put("hibernate.format_sql", "true");
//        hibernateProp.put("hibernate.show_sql", "true");
        hibernateProp.put("hibernate.max_fetch_depth", 3);
        hibernateProp.put("hibernate.jdbc.batch_size", 10);
        hibernateProp.put("hibernate.jdbc.fetch_size", 50);
        return hibernateProp;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());  // используем наш DataSource
        em.setPackagesToScan("ru.bsuedu.cad.lab.entity");  // правильный пакет!

        // Используем Hibernate как JPA провайдер
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);  // разрешаем генерацию DDL
        em.setJpaVendorAdapter(vendorAdapter);

        em.setJpaProperties(hibernateProperties());
        return em;
    }

    // 4. Менеджер транзакций (обязательно для JPA)
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}