package pub.codex.apix;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class ApixCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        String packages1 = context.getEnvironment().getProperty("apix.controllerPackage");
        String packages2 = context.getEnvironment().getProperty("apix.controller-package");
        if (StringUtils.isEmpty(packages1) && StringUtils.isEmpty(packages2)) {
            return false;
        }

        return true;
    }
}
