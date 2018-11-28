package pub.codex.apix.module;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;

public class ResourceGroup {

    private final String groupName;

    private final Class<?> controllerClazz;


    public ResourceGroup(String groupName, Class<?> controllerClazz) {
        this.groupName = groupName;
        this.controllerClazz = controllerClazz;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final ResourceGroup rhs = (ResourceGroup) obj;

        return equal(this.groupName, rhs.groupName)
                && equal(this.controllerClazz, rhs.controllerClazz);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(groupName, controllerClazz);
    }

    @Override
    public String toString() {
        return String.format("ResourceGroup{groupName='%s', controller=%s}", groupName,
                controllerClazz.getName());
    }

}
