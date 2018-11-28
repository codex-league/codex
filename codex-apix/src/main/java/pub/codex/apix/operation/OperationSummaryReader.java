package pub.codex.apix.operation;

import com.google.common.base.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pub.codex.apix.annotations.ApiOperation;
import pub.codex.apix.context.OperationContext;

@Component
@Order(Ordered.OperationSummaryReader)
public class OperationSummaryReader implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        Optional<ApiOperation> apiOperationAnnotation = context.findAnnotation(ApiOperation.class);
        if (apiOperationAnnotation.isPresent() && StringUtils.hasText(apiOperationAnnotation.get().value())) {
            context.operationBuilder().setSummary(apiOperationAnnotation.get().describe());
        }
    }

}
