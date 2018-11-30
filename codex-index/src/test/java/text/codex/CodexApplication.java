package text.codex;

<<<<<<< HEAD:codex-index/src/test/java/text/codex/CodexApplication.java
import pub.codex.index.EnableCodexLeague;
=======
import org.codex.core.CodexAutoConfigProvider;
import org.codex.index.EnableCodexLeague;
>>>>>>> 随便测试，不重要修改:codex-index/src/test/java/org/codex/CodexApplication.java
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
