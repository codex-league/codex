package pub.codex.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import pub.codex.core.model.JdbcInfo;
import pub.codex.core.model.PackageInfo;
import pub.codex.core.provider.ConfigProvider;

/**
 * Codex自动配置
 */
@Configuration
@ComponentScan({"pub.codex.core", "pub.codex.common.db", "pub.codex.core.template", "pub.codex.core.template.stream"})
@EnableConfigurationProperties({PackageInfo.class, JdbcInfo.class})
@Conditional(CodexCondition.class)
public class CodexAutoConfigProvider {

    /**
     * 配置用户的包信息
     *
     * @param packageInfo
     * @return
     */
    @Bean
    public ConfigProvider configProvider(PackageInfo packageInfo,JdbcInfo jdbcInfo) {

        return new ConfigProvider(packageInfo,jdbcInfo);
    }

}
