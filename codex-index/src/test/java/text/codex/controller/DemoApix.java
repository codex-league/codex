package text.codex.controller;

import pub.codex.apix.annotations.Api;
import pub.codex.apix.annotations.ApiOperation;
import pub.codex.apix.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import text.codex.controller.module.Person;
import text.codex.controller.module.Student;

@Api("演示API管理接口")  // 只描述Controller 信息
@RestController
public class DemoApix {


    /**
     * 演示接口1
     *
     * @param person
     * @return
     */
    @ApiOperation(value = "演示接口1") // 只描述API信息
    @PostMapping("index")
    private Person index(@Validated(Person.class) @RequestBody Person person, @ApiParam @RequestParam(required = false) String name) { // @ApiParam 会检查RequestParams ，会检查RequestParams优先级较高


        return person;
    }

    @ApiOperation(value = "测试接口1")
    @PostMapping("ceshi-1")
    private void ceshi11(@Validated(Student.class) @RequestBody Student student) {

    }

    @ApiOperation(value = "测试接口2")
    @PostMapping("ceshi-2")
    private void ceshi22(@ApiParam(value = "登录名") @RequestParam(required = false) String loginName) {

    }

}
