package pub.codex.core.template;

import org.springframework.stereotype.Component;
import pub.codex.core.template.stream.template.TableCodexTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * Service类coding 模板
 */
@Component
public class ServiceTemplate extends TableCodexTemplate {

    private final String TEMPLATE_NAME = "Service.java";

    @Override
    public String templateName() {
        return TEMPLATE_NAME;
    }

    @Override
    public Map<String, Object> environmentMap() {
        return Collections.emptyMap();
    }

    @Override
    public String storagePath() {
        return buildServiceFilePath(TEMPLATE_NAME);
    }

}
