package pub.codex.apix.wrapper;


import org.springframework.stereotype.Component;

import javax.validation.constraints.AssertTrue;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class AssertTrueWrapper  implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return AssertTrue.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(AssertTrue.class);

        if (annotation != null) {
            return ((AssertTrue) annotation).groups();
        }
        return null;
    }

}
