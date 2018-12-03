package text.codex;


import pub.codex.index.EnableCodexLeague;

import pub.codex.core.CodexAutoConfigProvider;
import pub.codex.index.EnableCodexLeague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@EnableCodexLeague
public class CodexApplication {

    public static void main(String[] args) {

        SpringApplication.run(CodexApplication.class, args);
    }

}
