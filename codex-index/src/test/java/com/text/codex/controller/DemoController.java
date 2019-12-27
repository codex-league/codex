package com.text.codex.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.text.codex.db.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pub.codex.apix.annotations.Api;
import pub.codex.apix.annotations.ApiOperation;
import pub.codex.apix.annotations.ApiParam;
import pub.codex.apix.annotations.constant.Describe;
import pub.codex.apix.annotations.group.VG;
import pub.codex.common.models.R;
import com.text.codex.db.entity.DemoEntity;
import pub.codex.core.template.utils.WhereUtils;

import java.util.Map;

/**
 * 演示表
 *
 * @date 2019-12-17 21:00:26
 */
@Api("演示表")
@RestController
public class DemoController {

    @Autowired
    DemoService demoService;

    /**
     * 新增接口
     *
     * @param demoEntity
     * @return
     */
    @ApiOperation("新增接口")
    @PostMapping("/demo")
    public R add(@RequestBody @Validated(VG.Add.class) DemoEntity demoEntity) {
        demoService.save(demoEntity);
        return R.ok();
    }


    /**
     * 更新接口
     *
     * @param demoEntity
     * @return
     */
    @ApiOperation("更新接口")
    @PutMapping("/demo")
    public R update(@RequestBody @Validated(VG.Update.class) DemoEntity demoEntity) {
        demoService.updateById(demoEntity);
        return R.ok();
    }


    /**
     * 删除接口
     *
     * @param id 根据ID删除
     * @return
     */
    @ApiOperation("删除接口")
    @DeleteMapping("/demo/{id}")
    public R delete(@ApiParam(Describe.ID) @PathVariable("id") String id) {
        demoService.removeById(id);
        return R.ok();
    }


    /**
     * 详情接口
     *
     * @param id 根据ID查询
     * @return
     */
    @ApiOperation("详情接口")
    @GetMapping("/demo/{id}")
    public R detail(@ApiParam(Describe.ID) @PathVariable("id") String id) {
        return R.ok().data(demoService.getById(id));
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
    @GetMapping("/buildingBasic")
    public R list(@ApiParam(Describe.WHERE) @RequestParam(required = false) String where, @ApiParam(Describe.KEYWORD) @RequestParam(required = false) String keyword, @ApiParam(Describe.PAGE_INDEX) @RequestParam(defaultValue = "0") Long pageIndex, @ApiParam(Describe.PAGE_SIZE) @RequestParam(defaultValue = "10") Long pageSize) {
        QueryWrapper<DemoEntity> entity = new QueryWrapper<>();

        WhereUtils.setWhereAndKeyword(entity, where, keyword); // 设置 where 和  keyword查询


        return R.ok().data(demoService.page(new Page<>(pageIndex, pageSize), entity));

    }

}