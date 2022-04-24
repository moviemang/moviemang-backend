package com.moviemang.datastore.config;//package com.moviemang.datastore.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@PropertySource("classpath:${spring.profiles.active}/jdbc.properties")
//@EnableJpaRepositories(
//        basePackages = "com.moviemang.datastore.repository.mongo",
//        entityManagerFactoryRef = "mongoEntityManager",
//        transactionManagerRef = "mongoTransactionManager")
//public class MongoDataSourceConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.mongo")
//    public DataSource mongoDataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean mongoEntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(mongoDataSource());
//        //Entity 패키지 경로
//        em.setPackagesToScan(new String[] { "com.moviemang.datastore.domain" });
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        //Hibernate 설정
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto","update");
//        properties.put("hibernate.dialect","org.hibernate.ogm.datastore.mongodb.MongoDBDialect");
//        em.setJpaPropertyMap(properties);
//        return em;
//    }
//
//    @Bean
//    public PlatformTransactionManager mongoTransactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(mongoEntityManager().getObject());
//        return transactionManager;
//    }
//}
