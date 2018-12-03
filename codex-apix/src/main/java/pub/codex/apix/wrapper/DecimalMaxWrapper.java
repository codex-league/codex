package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import javax.validation.constraints.DecimalMax;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


@Component
public class DecimalMaxWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return DecimalMax.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(DecimalMax.class);

        if (annotation != null) {
            return ((DecimalMax) annotation).groups();
        }
        return null;
    }
}
