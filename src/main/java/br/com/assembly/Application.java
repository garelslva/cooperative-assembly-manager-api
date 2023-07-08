package br.com.assembly;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Cooperative Assembly Manager API", version = "2.0", description = "Management of Cooperative Assembly."))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
