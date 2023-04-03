package pub.codex.apix.wrapper;

import jakarta.validation.constraints.Past;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class PastWrapper  implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return Past.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Past.class);

        if (annotation != null) {
            return ((Past) annotation).groups();
        }
        return null;
    }
}
