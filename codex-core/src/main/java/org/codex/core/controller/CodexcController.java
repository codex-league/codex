package org.codex.core.controller;


import org.apache.commons.io.IOUtils;
import org.codex.common.db.jdbc.TableDao;
import org.codex.common.models.CodexResult;
import org.codex.core.provider.ConfigProvider;
import org.codex.core.stream.TableCodexTemplateStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class CodexcController {


    @Autowired
    private ConfigProvider configProvider;

    @Autowired
    private TableDao tableDao;

    @Autowired
    private TableCodexTemplateStream tableCodexTemplateStream;

    /**
     * codex基础信息查询
     *
     * @return
     */
    @GetMapping("/codex/data")
    public CodexResult codex() {

        List<Map<String, Object>> tableList = tableDao.queryTableList();

        return CodexResult.ok()
                .put("tableList", tableList)
                .put("package", configProvider.getPackageInfo())
                .put("prefix", configProvider.getPrefix());
    }

    /**
     * 生成代码
     */
    @GetMapping("/codex/code/{tableName}")
    public void code(HttpServletResponse response, @PathVariable String tableName,
                     @RequestParam(required = false) String tablePrefix) throws IOException {

        byte[] data = tableCodexTemplateStream.doTemplate(tableName, tablePrefix);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
