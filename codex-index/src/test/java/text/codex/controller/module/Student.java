package text.codex.controller.module;

import pub.codex.apix.annotations.ApiModelProperty;

public class Student {

    @ApiModelProperty(groups = Student.class, value = "许西")
    String xuxi;

    public String getXuxi() {
        return xuxi;
    }

    public void setXuxi(String xuxi) {
        this.xuxi = xuxi;
    }
}
