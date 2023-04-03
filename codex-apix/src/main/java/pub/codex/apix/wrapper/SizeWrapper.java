package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class SizeWrapper  implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return Size.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Size.class);

        if (annotation != null) {
            return ((Size) annotation).groups();
        }
        return null;
    }
}
