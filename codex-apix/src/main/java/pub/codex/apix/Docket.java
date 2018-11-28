package pub.codex.apix;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 使用方的基本参数设定
 */
@ConfigurationProperties(prefix = "apix")
public class Docket {

    private String controllerPackage;


    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }
}
