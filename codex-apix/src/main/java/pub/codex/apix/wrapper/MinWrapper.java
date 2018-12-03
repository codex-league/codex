package pub.codex.apix.wrapper;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MinWrapper implements VaildWrapper {

    @Override
    public Class<? extends Annotation> getType() {
        return Min.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Min.class);

        if (annotation != null) {
            return ((Min) annotation).groups();
        }
        return null;
    }
}
