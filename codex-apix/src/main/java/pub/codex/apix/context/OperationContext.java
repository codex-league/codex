package pub.codex.apix.context;

import com.google.common.base.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import pub.codex.apix.build.OperationBuilder;

import java.lang.annotation.Annotation;
import java.util.List;

public class OperationContext {

    private final RequestMethod requestMethod;

    private final DescriptionContext descriptionContext;

    private final OperationBuilder operationBuilder;


    public OperationContext(RequestMethod requestMethod, DescriptionContext descriptionContext) {
        this.requestMethod = requestMethod;
        this.descriptionContext = descriptionContext;
        this.operationBuilder = new OperationBuilder();
    }

    public String getName() {
        return descriptionContext.getName();
    }


    public OperationBuilder operationBuilder() {
        return operationBuilder;
    }


    public HttpMethod httpMethod() {
        return HttpMethod.valueOf(requestMethod.toString());
    }

    public <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
        return descriptionContext.findAnnotation(annotation);
    }

    public List<MethodParameter>  getParameterAnnotation(Class<? extends Annotation> annotation) {
        return descriptionContext.getParameterAnnotation(annotation);
    }
    public List<MethodParameter>  getParameter() {
        return descriptionContext.getParameter();
    }

}
