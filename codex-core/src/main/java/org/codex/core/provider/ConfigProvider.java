package org.codex.core.provider;

import org.codex.core.model.PackageInfo;
import org.springframework.beans.factory.annotation.Value;

public class ConfigProvider {

    private PackageInfo packageInfo;

    @Value("${codex.prefix}")
    private String prefix;

    public ConfigProvider(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public String getPrefix() {
        return prefix;
    }
}
