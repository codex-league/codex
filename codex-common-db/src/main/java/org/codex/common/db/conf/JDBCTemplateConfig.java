package org.codex.common.db.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JDBCTemplateConfig {

    /**
     * 创建JDBC连接
     *
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource driverManagerDataSourceCodex) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManagerDataSourceCodex);
        return jdbcTemplate;
    }

    @Bean
    @ConfigurationProperties("codex.jdbc")
    public DriverManagerDataSource DriverManagerDataSourceCodex() {
        return new DriverManagerDataSource();
    }


}
