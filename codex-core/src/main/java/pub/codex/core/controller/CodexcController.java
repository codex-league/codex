package pub.codex.core.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pub.codex.common.column.BaseColumn;
import pub.codex.common.field.ControllerlClass;
import pub.codex.common.db.jdbc.TableDao;
import pub.codex.common.models.CodexResult;
import pub.codex.common.models.R;
import pub.codex.common.utils.BaseUtil;
import pub.codex.common.utils.TranslateUtil;
import pub.codex.core.provider.ConfigProvider;
import pub.codex.core.template.stream.template.TableCodexTemplateStream;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

@RestController
public class CodexcController {

    /**
     * 下载文档字节桶
     */
    private byte[] downloadByteBucket;

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
    @GetMapping("/codex/generate/table/option")
    public CodexResult codex() {

        List<Map<String, Object>> tableList = tableDao.queryTableList();

        return CodexResult.ok()
                .put("tableList", tableList)
                .put("package", configProvider.getPackageInfo())
                .put("prefix", configProvider.getPrefix())
                .put("jdbc", configProvider.getJdbcInfo());
    }

    /**
     * 获取表项数据
     *
     * @param tableName
     * @return
     */
    @GetMapping("/codex/generate/table/attribute/detail/{tableName}")
    public CodexResult infoResponse(@PathVariable String tableName) {
        List<Map<String, String>> columns = (List<Map<String, String>>) tableDao.queryColumns(tableName);

        List<BaseColumn> baseColumns = columns.stream().map(item -> {

            BaseColumn baseColumn = new BaseColumn();
            baseColumn.setComments(item.get("columnComment"));

            //列名转换成Java属性名
            String attrName = TranslateUtil.columnToJava(item.get("columnName"));
            baseColumn.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = BaseUtil.getConfig().getString(item.get("dataType"), "unknowType");
            baseColumn.setAttrType(attrType);

            Object maxLength = item.get("maxLength");
            if (maxLength != null) {
                baseColumn.setMaxLength(maxLength.toString());
            }else{
                baseColumn.setMaxLength("-");
            }

            //是否主键
            if ("PRI".equalsIgnoreCase(item.get("columnKey"))) {
                baseColumn.setPrimary("primary");
            }

            return baseColumn;
        }).collect(Collectors.toList());

        return CodexResult.ok().put("attributes", baseColumns);
    }

    /**
     * 生成代码（不含controller）
     */
    @GetMapping("/codex/generate/code/default/{tableName}")
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
     * @param tableName
     * @param tablePrefix
     * @throws IOException
     */
    @PostMapping("/codex/generate/code/controller/{tableName}")
    public R createController(@PathVariable String tableName,
                              @RequestParam(required = false) String tablePrefix,
                              @RequestBody ControllerlClass controllerlClass) {

        // 压缩包
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        tableCodexTemplateStream.doTemplate(tableName, controllerlClass, tablePrefix.trim(), zip);

        IOUtils.closeQuietly(zip);
        downloadByteBucket = outputStream.toByteArray();

        return R.ok();
    }


    /**
     * download Byte Bucket file
     *
     * @param response
     * @param tableName
     */
    @GetMapping("/codex/generate/code/downloadByteBucket/{tableName}")
    public ResponseEntity<?> downloadByteBucket(HttpServletResponse response,
                                                @PathVariable String tableName) throws IOException {

        if (downloadByteBucket == null || downloadByteBucket.length <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + ".zip\"");
        response.addHeader("Content-Length", "" + downloadByteBucket.length);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(downloadByteBucket, response.getOutputStream());

        downloadByteBucket = null;

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
