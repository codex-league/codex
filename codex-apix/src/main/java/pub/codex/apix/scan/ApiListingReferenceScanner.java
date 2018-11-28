package pub.codex.apix.scan;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.codex.apix.Docket;
import pub.codex.apix.annotations.ApiIgnore;
import pub.codex.apix.context.DocumentationContext;
import pub.codex.apix.context.RequestHandler;
import pub.codex.apix.context.RequestMappingContext;
import pub.codex.apix.module.ResourceGroup;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Multimaps.asMap;

/**
 * 扫描所有API
 */
@Component
public class ApiListingReferenceScanner {

    private Docket docket;

    @Autowired
    public ApiListingReferenceScanner(Docket docket) {
        this.docket = docket;
    }

    public Map<ResourceGroup, List<RequestMappingContext>> scan(DocumentationContext context) {
        ArrayListMultimap<ResourceGroup, RequestMappingContext> resourceGroupRequestMappings
                = ArrayListMultimap.create();

        // 获取指定包和未标记 @Apilgnore的 mapping对象
        Iterable<RequestHandler> matchingHandlers = from(context.getRequestHandlers())
                .filter(getRequestHandlerSelector());

        matchingHandlers.forEach(requestHandler -> {
            ResourceGroup resourceGroup = new ResourceGroup(requestHandler.groupName(), requestHandler.declaringClass());
            resourceGroupRequestMappings.put(resourceGroup, new RequestMappingContext(requestHandler));
        });

        return asMap(resourceGroupRequestMappings);

    }


    /**
     * 获取 RequestHandler 条件选择器
     *
     * @return
     */
    public Predicate<RequestHandler> getRequestHandlerSelector() {

        return and(getRequestHandlerSelectorByIgnore(), withBasePackage(docket.getBasePackage())); // todo 暂时忽略
    }


    /**
     * 过滤已被@ApiIgnore标记的RequestHandler
     *
     * @return
     */
    public Predicate<RequestHandler> getRequestHandlerSelectorByIgnore() {
        return and(
                not(withClassAnnotation(ApiIgnore.class)),
                not(withMethodAnnotation(ApiIgnore.class)));
    }


    /**
     * 忽略已被@ApiIgnore标记的方法
     */
    public static Predicate<RequestHandler> withMethodAnnotation(final Class<? extends Annotation> annotation) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return input.isAnnotatedWith(annotation);
            }
        };
    }

    /**
     * 忽略已被@ApiIgnore标记的类
     */
    public static Predicate<RequestHandler> withClassAnnotation(final Class<? extends Annotation> annotation) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return input.declaringClass().isAnnotationPresent(annotation);
            }
        };
    }

    /**
     * 匹配包类的 RequestHandler
     *
     * @param basePackage
     * @return
     */
    public static Predicate<RequestHandler> withBasePackage(final String basePackage) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return input.declaringClass().getPackage().getName().startsWith(basePackage);
            }
        };
    }


}
