package pub.codex.apix.operation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import pub.codex.apix.annotations.ApiOperation;
import pub.codex.apix.context.OperationContext;

@Component
@Order(Ordered.OperationPathReader)
public class OperationPathReader implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {

        Optional<RequestMapping> requestAnnotation = context.findAnnotation(RequestMapping.class);
        if (requestAnnotation.isPresent() && requestAnnotation.get().value().length != 0) {
            context.operationBuilder().setPath(StringUtils.join(requestAnnotation.get().value(),","));
            return;
        }

        Optional<GetMapping> getAnnotation = context.findAnnotation(GetMapping.class);
        if (getAnnotation.isPresent() && getAnnotation.get().value().length != 0) {
            context.operationBuilder().setPath(StringUtils.join(getAnnotation.get().value(),","));
            return;
        }

        Optional<PostMapping> postAnnotation = context.findAnnotation(PostMapping.class);
        if (postAnnotation.isPresent() && postAnnotation.get().value().length != 0) {
            context.operationBuilder().setPath(StringUtils.join(postAnnotation.get().value(),","));
            return;
        }


        Optional<PutMapping> putAnnotation = context.findAnnotation(PutMapping.class);
        if (putAnnotation.isPresent() && putAnnotation.get().value().length != 0) {
            context.operationBuilder().setPath(StringUtils.join(putAnnotation.get().value(),","));
            return;
        }

        Optional<DeleteMapping> deleteAnnotation = context.findAnnotation(DeleteMapping.class);
        if (deleteAnnotation.isPresent() && deleteAnnotation.get().value().length != 0) {
            context.operationBuilder().setPath(StringUtils.join(deleteAnnotation.get().value(),","));
            return;
        }


        Optional<PatchMapping> patchAnnotation = context.findAnnotation(PatchMapping.class);
        if (patchAnnotation.isPresent() && patchAnnotation.get().value().length != 0) {
            context.operationBuilder().setPath(StringUtils.join(patchAnnotation.get().value(),","));
        }
    }
}
