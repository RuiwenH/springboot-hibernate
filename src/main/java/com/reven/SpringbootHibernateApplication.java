package com.reven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class SpringbootHibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHibernateApplication.class, args);
	}

}

