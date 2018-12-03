package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import javax.validation.constraints.DecimalMin;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class DecimalMinWrapper  implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return DecimalMin.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(DecimalMin.class);

        if (annotation != null) {
            return ((DecimalMin) annotation).groups();
        }
        return null;
    }
}
