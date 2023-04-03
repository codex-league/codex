package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class NotBlankWrapper implements VaildWrapper {


    @Override
    public Class<? extends Annotation> getType() {
        return NotBlank.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(NotBlank.class);

        if (annotation != null) {
            return ((NotBlank) annotation).groups();
        }
        return null;
    }
}
