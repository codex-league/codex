package pub.codex.core.template;

import org.springframework.stereotype.Component;
import pub.codex.core.template.stream.template.TableCodexTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * ServiceImpl类coding 模板
 */
@Component
public class ServiceImplTemplate extends TableCodexTemplate {

    private final String TEMPLATE_NAME = "ServiceImpl.java";

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
        return buildServiceImplFilePath(TEMPLATE_NAME);
    }

}
