package pub.codex.apix;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("pub.codex.apix")
@EnableConfigurationProperties(Docket.class)
@Conditional(ApixCondition.class)
public class ApixDocumentationConfiguration {


}
