package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotEmpty;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


@Component
public class NotEmptyWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return NotEmpty.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(NotEmpty.class);

        if (annotation != null) {
            return ((NotEmpty) annotation).groups();
        }
        return null;
    }
}
