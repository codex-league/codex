package pub.codex.core;

import pub.codex.core.model.PackageInfo;
import pub.codex.core.provider.ConfigProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Codex自动配置
 */
@Configuration
@ComponentScan("pub.codex.**")
@ConditionalOnProperty(value = "codex.enable")
public class CodexAutoConfigProvider {

    /**
     * 配置用户的包信息
     *
     * @param packageInfo
     * @return
     */
    @Bean
    public ConfigProvider configProvider(PackageInfo packageInfo) {

        return new ConfigProvider(packageInfo);
    }

    @Bean
    @ConfigurationProperties("codex.package")
    public PackageInfo packageInfo() {
        return PackageInfo.build();
    }

}
