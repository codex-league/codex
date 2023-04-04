package pub.codex.core.controller;

import pub.codex.common.db.entity.RegularEntity;
import pub.codex.common.result.CodexResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegularxController {
    @PostMapping("/codex/regularx/data")
    @ResponseBody
    public CodexResult regularx(@RequestBody RegularEntity regularEntity){
        String regular=regularEntity.getRegular();
        String param=regularEntity.getParam();
        return CodexResult.ok(param.matches(regular)?param:"(匹配失败)");
    }
}
