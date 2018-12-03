package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import javax.validation.constraints.PastOrPresent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class PastOrPresentWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return PastOrPresent.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(PastOrPresent.class);

        if (annotation != null) {
            return ((PastOrPresent) annotation).groups();
        }
        return null;
    }
}
