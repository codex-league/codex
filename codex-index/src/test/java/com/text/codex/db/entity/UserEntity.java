package com.text.codex.db.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pub.codex.apix.annotations.*;
import pub.codex.apix.annotations.group.VG;

import java.io.Serializable;


import java.util.Date;
import java.util.List;


/**
 * 用户表
 *
 * @date 2021-03-18 14:10:10
 */
@TableName("t_user")
public class UserEntity extends SupEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @NotBlank(groups = {VG.Update.class})
    @ApiModelProperty("ID")
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(groups = {VG.Update.class})
    @ApiModelProperty(describe = "用户名", groups = {VG.Add.class})
    private String userName;
    /**
     * 1:男，2：女
     */
    @NotNull(groups = {VG.Add.class})
    @ApiModelProperty(describe = "1:男，2：女", groups = {VG.Update.class})
    private Integer sex;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Valid
    @ApiModelProperty(describe = "子实体1", groups = {VG.Add.class})
    private List<DemoEntity> demo1;

    @Valid
    @ApiModelProperty(describe = "子实体2", groups = {VG.Add.class})
    private DemoEntity demo2;

    @Valid
    @ApiModelProperty(describe = "子实体3", groups = {VG.Add.class})
    private DemoEntity demo3;

    @Valid
    @ApiModelProperty(describe = "子实体4", groups = {VG.Add.class})
    private UserEntity user1;

    @Valid
    @ApiModelProperty(describe = "子实体5", groups = {VG.Add.class})
    private UserEntity user2;




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return sex;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public List<DemoEntity> getDemo1() {
        return demo1;
    }

    public void setDemo1(List<DemoEntity> demo1) {
        this.demo1 = demo1;
    }

    public DemoEntity getDemo2() {
        return demo2;
    }

    public void setDemo2(DemoEntity demo2) {
        this.demo2 = demo2;
    }

    public DemoEntity getDemo3() {
        return demo3;
    }

    public void setDemo3(DemoEntity demo3) {
        this.demo3 = demo3;
    }

    public UserEntity getUser1() {
        return user1;
    }

    public void setUser1(UserEntity user1) {
        this.user1 = user1;
    }

    public UserEntity getUser2() {
        return user2;
    }

    public void setUser2(UserEntity user2) {
        this.user2 = user2;
    }
}
