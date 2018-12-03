package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NegativeOrZero;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class NegativeOrZeroWrapper implements VaildWrapper {


    @Override
    public Class<? extends Annotation> getType() {
        return NegativeOrZero.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(NegativeOrZero.class);

        if (annotation != null) {
            return ((NegativeOrZero) annotation).groups();
        }
        return null;
    }
}
