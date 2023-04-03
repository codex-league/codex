package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Digits;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


@Component
public class DigitsWrapper implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return Digits.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Digits.class);

        if (annotation != null) {
            return ((Digits) annotation).groups();
        }
        return null;
    }
}
