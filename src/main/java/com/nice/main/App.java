package com.nice.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackageClasses = App.class)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class App {
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    	/*EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	
    	Cliente cliente = entityManager.find(Cliente.class, 1);
    	
    	System.out.println(cliente.getNome());*/
    }
}
