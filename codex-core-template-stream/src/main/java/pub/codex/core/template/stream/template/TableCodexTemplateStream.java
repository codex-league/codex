package pub.codex.core.template.stream.template;

import org.apache.commons.io.IOUtils;
import pub.codex.common.db.jdbc.TableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * TABLE 相关coding 的template执行器
 */
@Service
public class TableCodexTemplateStream {

    @Autowired
    private List<TableCodexTemplate> tableCodexTemplateList;

    @Autowired
    private TableDao tableDao;

    /**
     * foreach template
     *
     * @param tableName
     */
    public byte[] doTemplate(String tableName, String tablePrefix) {

        // 压缩包
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);


        // 表&列信息
        Map<String, String> table = getTable(tableName);
        List<Map<String, String>> columns = getColumns(tableName);

        //遍历
        tableCodexTemplateList.stream().forEach(tableCodexTemplate -> {
            tableCodexTemplate.buildTemplateEntity(table, columns, tablePrefix, zip);
        });

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }


    /**
     * 查询表信息
     *
     * @param tableName
     * @return
     */
    private Map<String, String> getTable(String tableName) {
        return (Map<String, String>) tableDao.queryTable(tableName);
    }

    /**
     * 查询列信息
     *
     * @param tableName
     * @return
     */
    private List<Map<String, String>> getColumns(String tableName) {
        return (List<Map<String, String>>) tableDao.queryColumns(tableName);
    }


}
