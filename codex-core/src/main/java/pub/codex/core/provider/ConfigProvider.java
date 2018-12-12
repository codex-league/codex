package pub.codex.core.provider;

import pub.codex.core.model.JdbcInfo;
import pub.codex.core.model.PackageInfo;
import org.springframework.beans.factory.annotation.Value;

public class ConfigProvider {

    private PackageInfo packageInfo;

    private JdbcInfo jdbcInfo;

    @Value(value = "${codex.prefix:}")
    private String prefix;

    public ConfigProvider(PackageInfo packageInfo,JdbcInfo jdbcInfo) {
        this.packageInfo = packageInfo;
        this.jdbcInfo = jdbcInfo;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public JdbcInfo getJdbcInfo() {
        return jdbcInfo;
    }

    public String getPrefix() {
        return prefix;
    }

}
