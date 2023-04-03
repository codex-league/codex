package pub.codex.apix.wrapper;

import jakarta.validation.constraints.Max;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


@Component
public class MaxWrapper implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return Max.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Max.class);

        if (annotation != null) {
            return ((Max) annotation).groups();
        }
        return null;
    }

}
