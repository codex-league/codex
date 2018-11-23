package org.codex.test;

import org.codex.core.EnableCodex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableCodex
public class CodexApplication {

    public static void main(String[] args) {

        SpringApplication.run(CodexApplication.class, args);
    }

}
