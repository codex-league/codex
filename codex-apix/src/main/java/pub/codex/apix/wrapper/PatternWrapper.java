package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class PatternWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return Pattern.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Pattern.class);

        if (annotation != null) {
            return ((Pattern) annotation).groups();
        }
        return null;
    }
}
