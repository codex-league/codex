package org.codex;

import org.codex.apix.Docket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApixConfig {


    @Bean
    public Docket docket(){
        return new Docket("org.codex.controller");
    }

}
