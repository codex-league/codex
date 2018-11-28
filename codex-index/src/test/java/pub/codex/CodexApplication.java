package pub.codex;

import pub.codex.index.EnableCodexLeague;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableCodexLeague
public class CodexApplication {

    public static void main(String[] args) {

        SpringApplication.run(CodexApplication.class, args);
    }

}
