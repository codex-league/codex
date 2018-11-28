package org.codex;

import org.codex.apix.EnableApix;
import org.codex.core.EnableCodex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableCodex
@EnableApix
public class CodexApplication {

    public static void main(String[] args) {

        SpringApplication.run(CodexApplication.class, args);
    }

}
