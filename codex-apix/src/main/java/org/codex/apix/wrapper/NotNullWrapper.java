package org.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


@Component
public class NotNullWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return NotNull.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(NotNull.class);

        if (annotation != null) {
            return ((NotNull) annotation).groups();
        }
        return null;
    }
}
