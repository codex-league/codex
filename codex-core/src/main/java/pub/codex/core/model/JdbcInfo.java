package pub.codex.core.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据源信息
 */
@ConfigurationProperties("codex.jdbc")
public class JdbcInfo {

    private String url;

    private String userName;

    private String driverClassName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

}
