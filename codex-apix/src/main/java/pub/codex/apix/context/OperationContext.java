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

    private final RequestMappingContext requestContext;

    private final OperationBuilder operationBuilder;


    public OperationContext(RequestMethod requestMethod, RequestMappingContext requestContext) {
        this.requestMethod = requestMethod;
        this.requestContext = requestContext;
        this.operationBuilder = new OperationBuilder();
    }

    public String getName() {
        return requestContext.getName();
    }


    public OperationBuilder operationBuilder() {
        return operationBuilder;
    }


    public HttpMethod httpMethod() {
        return HttpMethod.valueOf(requestMethod.toString());
    }

    public <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
        return requestContext.findAnnotation(annotation);
    }

    public List<MethodParameter>  getParameterAnnotation(Class<? extends Annotation> annotation) {
        return requestContext.getParameterAnnotation(annotation);
    }
    public List<MethodParameter>  getParameter() {
        return requestContext.getParameter();
    }

}
