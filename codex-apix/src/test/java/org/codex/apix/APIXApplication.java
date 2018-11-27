package org.codex.apix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApix
public class APIXApplication {

	public static void main(String[] args) {
		SpringApplication.run(APIXApplication.class, args);
	}

}
