package pub.codex.apix;

/**
 * 使用方的基本参数设定
 */
public class Docket {

    private String controllerPackage;


    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }
}
