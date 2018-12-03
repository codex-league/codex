package pub.codex.core.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.codex.core.template.stream.BaseTemplateConfigProvider;

@Service
public class TemplateConfigProvider implements BaseTemplateConfigProvider {

    @Autowired
    private ConfigProvider configProvider;

    @Override
    public String getServicePath() {

        return configProvider.getPackageInfo().getServicePath();
    }

    @Override
    public String getServiceImplPath() {
        return configProvider.getPackageInfo().getServiceImplPath();
    }

    @Override
    public String getMapperPath() {
        return configProvider.getPackageInfo().getMapperPath();
    }

    @Override
    public String getMapperXMLPath() {
        return configProvider.getPackageInfo().getMapperXMLPath();
    }

    @Override
    public String getEntityPath() {
        return configProvider.getPackageInfo().getEntityPath();
    }
}
