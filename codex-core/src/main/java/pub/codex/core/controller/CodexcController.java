package pub.codex.core.controller;


import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pub.codex.common.column.ControllerColumn;
import pub.codex.common.column.InfoColumn;
import pub.codex.common.db.jdbc.TableDao;
import pub.codex.common.models.CodexResult;
import pub.codex.common.utils.BaseUtil;
import pub.codex.common.utils.TranslateUtil;
import pub.codex.core.provider.ConfigProvider;
import pub.codex.core.template.stream.template.TableCodexTemplateStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

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
     * 获取表项数据
     *
     * @param tableName
     * @return
     */
    @GetMapping("/codex/info/{tableName}")
    public CodexResult infoResponse(@PathVariable String tableName) {
        List<Map<String, String>> columns = (List<Map<String, String>>) tableDao.queryColumns(tableName);
        List<InfoColumn> infoList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            InfoColumn infoColumn = new InfoColumn();
            infoColumn.setComments(column.get("columnComment"));

            //列名转换成Java属性名
            String attrName = TranslateUtil.columnToJava(column.get("columnName"));
            infoColumn.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = BaseUtil.getConfig().getString(column.get("dataType"), "unknowType");
            infoColumn.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey"))) {
                infoColumn.setPrimary("primary");
            }
            infoList.add(infoColumn);
        }
        return CodexResult.ok().put("info", infoList);
    }

    /**
     * 生成代码（不含controller）
     */
    @GetMapping("/codex/code/{tableName}")
    public void code(HttpServletResponse response, @PathVariable String tableName,
                     @RequestParam(required = false) String tablePrefix) throws IOException {

        // 压缩包
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        tableCodexTemplateStream.doTemplate(tableName, null, tablePrefix, zip);

        IOUtils.closeQuietly(zip);
        byte[] data = outputStream.toByteArray();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 生成代码（含controller）
     *
     * @param response
     * @param tableName
     * @param tablePrefix
     * @throws IOException
     */
    @PostMapping("/codex/generate/{tableName}")
    public void createController(HttpServletRequest request, HttpServletResponse response, @PathVariable String tableName, @RequestParam(required = false) String tablePrefix
    ) throws IOException {

        String dataBody = (String) request.getParameter("data");

        ControllerColumn controllerColumn = JSON.parseObject(dataBody, ControllerColumn.class);
        // 压缩包
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        tableCodexTemplateStream.doTemplate(tableName, controllerColumn, tablePrefix, zip);

        IOUtils.closeQuietly(zip);
        byte[] data = outputStream.toByteArray();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
