package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import jakarta.validation.constraints.PositiveOrZero;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class PositiveOrZeroWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return PositiveOrZero.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(PositiveOrZero.class);

        if (annotation != null) {
            return ((PositiveOrZero) annotation).groups();
        }
        return null;
    }
}
