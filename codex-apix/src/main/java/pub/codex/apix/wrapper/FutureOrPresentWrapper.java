package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import jakarta.validation.constraints.FutureOrPresent;
import java.lang.reflect.Field;

@Component
public class FutureOrPresentWrapper implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return FutureOrPresent.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(FutureOrPresent.class);

        if (annotation != null) {
            return ((FutureOrPresent) annotation).groups();
        }
        return null;
    }
}
