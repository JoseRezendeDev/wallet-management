package br.com.olik.asigntest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class AsigntestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsigntestApplication.class, args);
	}

}
