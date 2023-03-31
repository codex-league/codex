package com.text.codex.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import pub.codex.apix.annotations.*;
import pub.codex.apix.annotations.group.VG;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.List;

/**
 * 演示表
 * 
 * @date 2019-12-17 21:00:26
 */
@TableName("sys_demo")
public class DemoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@NotNull(groups = {VG.Add.class})
	@ApiModelProperty(describe = "id",groups = {VG.Update.class})
	private Long id;
	/**
	 * 姓名
	 */
	@ApiModelProperty(describe = "姓名",groups = {VG.Add.class,VG.Update.class})
	private String name;
	/**
	 * 年龄
	 */
	@ApiModelProperty(describe = "年龄",groups = {VG.Add.class,VG.Update.class})
	private Integer age;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}
}
