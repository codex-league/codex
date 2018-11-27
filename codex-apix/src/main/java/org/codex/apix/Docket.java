package org.codex.apix;

/**
 * 使用方的基本参数设定
 */
public class Docket {

    private String BasePackage;

    public Docket(String basePackage) {
        BasePackage = basePackage;
    }

    public String getBasePackage() {
        return BasePackage;
    }
}
