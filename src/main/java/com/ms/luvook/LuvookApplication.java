package com.ms.luvook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("config/authkey.properties")
public class LuvookApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuvookApplication.class, args);
	}
}
