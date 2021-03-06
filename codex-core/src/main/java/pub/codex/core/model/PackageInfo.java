package pub.codex.core.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 包信息
 */
@ConfigurationProperties("codex.package")
public class PackageInfo {

    /**
     * service包路径
     */
    private String servicePath;

    /**
     * serviceImpl包路径
     */
    private String serviceImplPath;

    /**
     * mapper包路径
     */
    private String mapperPath;

    /**
     * mapperXML包路径
     */
    private String mapperXMLPath;

    /**
     * entity包路径
     */
    private String entityPath;

    /**
     * controller包路径
     */
    private String controllerPath;


    public String getServicePath() {
        return servicePath;
    }

    public PackageInfo setServicePath(String servicePath) {
        this.servicePath = servicePath;
        return this;
    }

    public String getServiceImplPath() {
        return serviceImplPath;
    }

    public PackageInfo setServiceImplPath(String serviceImplPath) {
        this.serviceImplPath = serviceImplPath;
        return this;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    public String getMapperXMLPath() {
        return mapperXMLPath;
    }

    public PackageInfo setMapperXMLPath(String mapperXMLPath) {
        this.mapperXMLPath = mapperXMLPath;
        return this;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public PackageInfo setEntityPath(String entityPath) {
        this.entityPath = entityPath;
        return this;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public void setControllerPath(String controllerPath) {
        this.controllerPath = controllerPath;
    }

    public static PackageInfo build() {
        return new PackageInfo();
    }
}
