package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Positive;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class PositiveWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return Positive.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Positive.class);

        if (annotation != null) {
            return ((Positive) annotation).groups();
        }
        return null;
    }
}
