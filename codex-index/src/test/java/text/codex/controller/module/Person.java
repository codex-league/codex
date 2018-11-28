package text.codex.controller.module;

import pub.codex.apix.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class Person {

    @NotBlank
    String id;

    @NotBlank(groups = Person.class)
    private String name;

    @ApiModelProperty
    private String age;

    @ApiModelProperty(groups = Person.class, describe = "心别")
    String sex;

    Student student;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
