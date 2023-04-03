package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Null;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


@Component
public class NullWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return Null.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Null.class);

        if (annotation != null) {
            return ((Null) annotation).groups();
        }
        return null;
    }
}
