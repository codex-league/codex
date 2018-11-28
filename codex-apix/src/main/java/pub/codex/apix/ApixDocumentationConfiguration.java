package pub.codex.apix;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "pub.codex.apix",
})
public class ApixDocumentationConfiguration {


    @Bean
    @ConfigurationProperties("apix")
    public Docket docket(){
        return new Docket();
    }

}
