package pub.codex.apix.wrapper;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;



@Component
public class EmailWrapper  implements VaildWrapper{

    @Override
    public Class<? extends Annotation> getType() {
        return Email.class;
    }

    @Override
    public Class<?>[] getGroup(Field field) {

        Annotation annotation = field.getAnnotation(Email.class);

        if (annotation != null) {
            return ((Email) annotation).groups();
        }
        return null;
    }
}
