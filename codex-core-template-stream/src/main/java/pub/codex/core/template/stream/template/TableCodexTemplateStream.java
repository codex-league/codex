package pub.codex.core.template.stream.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.codex.common.db.entity.TableEntity;
import pub.codex.common.db.jdbc.TableDao;
import pub.codex.common.field.ControllerlClass;
import pub.codex.core.template.stream.provider.TableEntityProvider;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * TABLE 相关coding 的template执行器
 */
@Component
public class TableCodexTemplateStream {

    @Autowired
    private List<TableCodexTemplate> tableCodexTemplateList;

    @Autowired
    private TableDao tableDao;

    @Autowired
    TableEntityProvider tableEntityProvider;

    /**
     * foreach template
     *
     * @param tableName
     * @param zip
     */
    public void doTemplate(String tableName, ControllerlClass controllerlClass, String tablePrefix, ZipOutputStream zip) {

        // 表&列信息
        Map<String, String> table = getTable(tableName);
        List<Map<String, String>> columns = getColumns(tableName);

        TableEntity tableEntity = tableEntityProvider.buildTemplateEntity(table, columns, controllerlClass, tablePrefix);
        //遍历
        tableCodexTemplateList.stream().forEach(tableCodexTemplate -> {
            tableCodexTemplate.build(tableEntity, zip);
        });
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
