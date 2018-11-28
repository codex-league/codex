package pub.codex.apix.operation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pub.codex.apix.context.OperationContext;

@Component
@Order(Ordered.DefaultOperationReader)
public class DefaultOperationReader implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        String operationName = context.getName();
        context.operationBuilder()
                .setMethod(context.httpMethod())
                .setSummary(operationName);
    }

}
