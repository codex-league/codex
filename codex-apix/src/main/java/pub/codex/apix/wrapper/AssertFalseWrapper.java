package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.AssertFalse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class AssertFalseWrapper implements VaildWrapper{
    @Override
    public Class<? extends Annotation> getType() {
        return AssertFalse.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(AssertFalse.class);

        if (annotation != null) {
            return ((AssertFalse) annotation).groups();
        }
        return null;
    }
}
