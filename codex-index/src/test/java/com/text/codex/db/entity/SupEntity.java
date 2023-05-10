package com.text.codex.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import pub.codex.apix.annotations.ApiModelProperty;
import pub.codex.apix.annotations.group.VG;

import java.io.Serializable;

/**
 * 演示表
 * 
 * @date 2019-12-17 21:00:26
 */
@TableName("sys_demo")
public class SupEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 渠道
	 */
	@ApiModelProperty(describe = "渠道",groups = {VG.Add.class,VG.Update.class})
	private String ter;

	public String getTer() {
		return ter;
	}

	public void setTer(String ter) {
		this.ter = ter;
	}
}
