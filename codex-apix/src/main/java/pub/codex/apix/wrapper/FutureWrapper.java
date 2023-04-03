package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Future;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class FutureWrapper implements VaildWrapper  {

    @Override
    public Class<? extends Annotation> getType() {
        return Future.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Future.class);

        if (annotation != null) {
            return ((Future) annotation).groups();
        }
        return null;
    }
}
