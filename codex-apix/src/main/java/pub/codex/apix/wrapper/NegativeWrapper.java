package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import javax.validation.constraints. Negative;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class NegativeWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return  Negative.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation( Negative.class);

        if (annotation != null) {
            return (( Negative) annotation).groups();
        }
        return null;
    }
}
