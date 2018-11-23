package org.codex.core.stream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.codex.common.CodexException;
import org.codex.common.db.entity.ColumnEntity;
import org.codex.common.db.entity.TableEntity;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * TABLE相关的 实现的抽象类
 * 根据 数据库表 coding 的template 写这里
 *
 * @author xuxi
 */


public abstract class TableCodexTemplate extends BaseTableCodexTemplate implements CodexTemplate {

    /**
     * 表信息
     */
    protected TableEntity tableEntity = new TableEntity();

    /**
     * 压缩包信息
     */
    protected ZipOutputStream zip;


    /**
     * 设置 组建信息
     *
     * @param table
     * @param columns
     * @param zip
     */
    public void setComponent(Map<String, String> table, List<Map<String, String>> columns, String tablePrefix, ZipOutputStream zip) {

        // 表信息
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));

        String className = tableToJava(tableEntity.getTableName(), tablePrefix); // todo 表名前缀忽略
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));


        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = getConfig().getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        // zip
        this.zip = zip;

    }


    /**
     * 渲染模板
     *
     * @param templateName
     * @param map
     * @param filepath
     */
    protected void buildTemplate(String templateName, Map<String, Object> map, String filepath) {
        //渲染模板
        Template template = getTemplate(templateName);
        VelocityContext context = new VelocityContext(map);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        //添加到zip
        try {
            zip.putNextEntry(new ZipEntry(filepath));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new CodexException("渲染模板失败，表名：" + tableEntity.getClassName(), e);
        }
    }

}
