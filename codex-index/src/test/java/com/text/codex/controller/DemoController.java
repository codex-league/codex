//package com.text.codex.controller;
//
//import com.text.codex.db.entity.DemoEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import pub.codex.apix.annotations.Api;
//import pub.codex.apix.annotations.ApiOperation;
//import pub.codex.apix.annotations.ApiParam;
//import pub.codex.apix.annotations.constant.Describe;
//import pub.codex.apix.annotations.group.VG;
//import pub.codex.common.models.R;
//
//
//
///**
// * 演示表
// *
// * @date 2019-12-17 21:00:26
// */
//@Api("演示表")
//@RestController
//public class DemoController {
//
//
//    /**
//     * 新增接口
//     *
//     * @param demoEntity
//     * @return
//     */
//    @ApiOperation("新增接口")
//    @PostMapping("/demo")
//    public R add(@RequestBody @Validated(VG.Add.class) DemoEntity demoEntity) {
//        return R.ok();
//    }
//
//
//    /**
//     * 更新接口
//     *
//     * @param demoEntity
//     * @return
//     */
//    @ApiOperation("更新接口")
//    @PutMapping("/demo")
//    public R update(@RequestBody @Validated(VG.Update.class) DemoEntity demoEntity) {
//        return R.ok();
//    }
//
//
//    /**
//     * 删除接口
//     *
//     * @param id 根据ID删除
//     * @return
//     */
//    @ApiOperation("删除接口")
//    @DeleteMapping("/demo/{id}")
//    public R delete(@ApiParam(Describe.ID) @PathVariable("id") String id) {
//        return R.ok();
//    }
//
//
//    /**
//     * 详情接口
//     *
//     * @param id 根据ID查询
//     * @return
//     */
//    @ApiOperation("详情接口")
//    @RequestMapping(value = "/demo/{id}", method = {RequestMethod.GET, RequestMethod.POST})
//    public R detail(@ApiParam(Describe.ID) @PathVariable("id") String id) {
//        return R.ok();
//    }
//
//
//    /**
//     * 列表接口
//     *
//     * @param where JSON条件
//     *              pageIndex 当前页
//     *              pageSize 页数
//     * @return
//     */
//    @ApiOperation("列表接口")
//    @GetMapping("/buildingBasic")
//    public R list(@ApiParam(Describe.WHERE) @RequestParam(required = false) String where, @ApiParam(Describe.KEYWORD) @RequestParam(required = false) String keyword, @ApiParam(Describe.PAGE_INDEX) @RequestParam(defaultValue = "0") Long pageIndex, @ApiParam(Describe.PAGE_SIZE) @RequestParam(defaultValue = "10") Long pageSize) {
//
//        return R.ok();
//
//    }
//
//}