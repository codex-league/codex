package pub.codex.apix.description;

import com.google.common.base.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pub.codex.apix.annotations.ApiOperation;
import pub.codex.apix.context.DescriptionContext;


@Component
@Order(Ordered.DefaultDescriptionReader)
public class DefaultDescriptionReader implements DescriptionBuilderPlugin {

    @Override
    public void apply(DescriptionContext context) {

        String DescriptionName = context.getName();

        Optional<ApiOperation> apiOperationAnnotation = context.findAnnotation(ApiOperation.class);
        if (apiOperationAnnotation.isPresent() && StringUtils.hasText(apiOperationAnnotation.get().value())) {
            context.getApiDescriptionBuilder().setSummary(apiOperationAnnotation.get().describe());
        }

        context.getApiDescriptionBuilder()
                .setMethodName(DescriptionName);

    }
}
