package pub.codex.core;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class CodexCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        String url = context.getEnvironment().getProperty("codex.jdbc.url");
        if (StringUtils.isEmpty(url)) {
            return false;
        }

        String entity1 = context.getEnvironment().getProperty("codex.package.entityPath");
        String entity2 = context.getEnvironment().getProperty("codex.package.entity-path");
        if (StringUtils.isEmpty(entity1) && StringUtils.isEmpty(entity2)) {
            return false;
        }

        String mapper1 = context.getEnvironment().getProperty("codex.package.mapperPath");
        String mapper2 = context.getEnvironment().getProperty("codex.package.mapper-path");
        if (StringUtils.isEmpty(mapper1) && StringUtils.isEmpty(mapper2)) {
            return false;
        }
        String mapperXML1 = context.getEnvironment().getProperty("codex.package.mapperXMLPath");
        String mapperXML2 = context.getEnvironment().getProperty("codex.package.mapperXML-path");
        if (StringUtils.isEmpty(mapperXML1) && StringUtils.isEmpty(mapperXML2)) {
            return false;
        }
        String service1 = context.getEnvironment().getProperty("codex.package.servicePath");
        String service2 = context.getEnvironment().getProperty("codex.package.service-path");
        if (StringUtils.isEmpty(service1) && StringUtils.isEmpty(service2)) {
            return false;
        }
        String serviceImpl1 = context.getEnvironment().getProperty("codex.package.serviceImplPath");
        String serviceImpl2 = context.getEnvironment().getProperty("codex.package.serviceImpl-path");
        if (StringUtils.isEmpty(serviceImpl1) && StringUtils.isEmpty(serviceImpl2)) {
            return false;
        }
        String controller1 = context.getEnvironment().getProperty("codex.package.controllerPath");
        String controller2 = context.getEnvironment().getProperty("codex.package.controller-path");
        if (StringUtils.isEmpty(controller1) && StringUtils.isEmpty(controller2)) {
            return false;
        }

        return true;
    }
}
