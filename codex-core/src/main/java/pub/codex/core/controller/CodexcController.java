package pub.codex.core.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pub.codex.common.db.jdbc.TableDao;
import pub.codex.common.models.CodexResult;
import pub.codex.core.column.InfoColumn;
import pub.codex.core.provider.ConfigProvider;
import pub.codex.core.template.stream.template.BaseTableCodexTemplate;
import pub.codex.core.template.stream.template.TableCodexTemplateStream;
import org.springframework.util.StringUtils;
import pub.codex.common.db.entity.ColumnEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    @GetMapping("/codex/info/{tableName}")
    public CodexResult infoResponse(@PathVariable String tableName){
        List<Map<String, String>> columns = (List<Map<String, String>>) tableDao.queryColumns(tableName);
        List<InfoColumn> infoList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            InfoColumn infoColumn = new InfoColumn();
            infoColumn.setComments(column.get("columnComment"));

            //列名转换成Java属性名
            String attrName = new BaseTableCodexTemplate().columnToJava(column.get("columnName"));
            infoColumn.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = new BaseTableCodexTemplate().getConfig().getString(column.get("dataType"), "unknowType");
            infoColumn.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey"))) {
                infoColumn.setPrimary("primary");
            }
            infoList.add(infoColumn);
        }
        return CodexResult.ok().put("info",infoList);
    }

//    @PostMapping("/codex/generate")
//    public CodexResult createController(@RequestBody ){
//
//    }
}
