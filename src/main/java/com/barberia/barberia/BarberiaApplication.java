package com.barberia.barberia;

import com.barberia.barberia.entities.ERole;
import com.barberia.barberia.entities.RoleEntity;
import com.barberia.barberia.entities.UserEntity;
import com.barberia.barberia.repository.UserRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class BarberiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberiaApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.info(new Info()
						.title("prueba swagger")
						.version("1.0.0")
						.description("ejemplo swagger")
						.termsOfService("https://swagger.io/terms")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}



}
