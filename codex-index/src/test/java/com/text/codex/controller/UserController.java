package com.text.codex.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pub.codex.apix.annotations.Api;
import pub.codex.apix.annotations.ApiOperation;
import pub.codex.apix.annotations.ApiParam;
import pub.codex.apix.annotations.constant.Describe;
import pub.codex.apix.annotations.group.VG;
import pub.codex.common.models.R;
import pub.codex.core.template.utils.WhereUtils;
import com.text.codex.db.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户表
 *
 * @date 2021-03-14 12:46:54
 */
@Api("用户表")
@RestController
public class UserController {


    /**
     * 新增接口
     *
     * @param userEntity
     * @return
     */
    @ApiOperation("新增接口")
    @PostMapping("/user")
    public R add(@RequestBody @Validated(VG.Add.class) UserEntity userEntity) {
        return R.ok();
    }


    /**
     * 更新接口
     *
     * @param userEntity
     * @return
     */
    @ApiOperation("更新接口")
    @PutMapping("/user")
    public R update(@RequestBody @Validated(VG.Update.class) UserEntity userEntity) {
        return R.ok();
    }


    /**
     * 删除接口
     *
     * @param id 根据ID删除
     * @return
     */
    @ApiOperation("删除接口")
    @DeleteMapping("/user/{id}")
    public R delete(@ApiParam(Describe.ID) @PathVariable("id") String id) {
        return R.ok();
    }


    /**
     * 详情接口
     *
     * @param id 根据ID查询
     * @return
     */
    @ApiOperation("详情接口")
    @GetMapping("/user/{id}")
    public R detail(@ApiParam(Describe.ID) @PathVariable("id") String id) {
        return R.ok();
    }


    /**
     * 列表接口
     *
     * @param where JSON条件
     *              pageIndex 当前页
     *              pageSize 页数
     * @return
     */
    @ApiOperation("列表接口")
    @GetMapping("/user")
    public Object list(@ApiParam(Describe.WHERE) @RequestParam(required = false) String where,
                       @ApiParam(Describe.KEYWORD) @RequestParam(required = false) String keyword,
                       @ApiParam(Describe.PAGE_INDEX) @RequestParam(defaultValue = "0") Long pageIndex,
                       @ApiParam(Describe.PAGE_SIZE) @RequestParam(defaultValue = "10") Long pageSize) {

        return R.ok();

    }

}


