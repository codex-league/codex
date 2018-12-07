package pub.codex.core.template.stream.template;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.util.StringUtils;
import pub.codex.common.CodexException;
import pub.codex.common.Constant;
import pub.codex.common.db.entity.ColumnEntity;
import pub.codex.common.db.entity.TableEntity;
import pub.codex.core.column.BaseColumn;
import pub.codex.core.column.ControllerColumn;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
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


public abstract class TableCodexTemplate extends BaseTableCodexTemplate {

    /**
     * 表信息
     */
    protected TableEntity tableEntity = new TableEntity();

    /**
     * 压缩包信息
     */
    protected ZipOutputStream zip;


    /**
     * coding 的主要逻辑在这里实现
     */
    public abstract void coding();

    /**
     * 设置 组建信息
     *
     * @param table
     * @param columns
     * @param zip
     */
    public void buildTemplateEntity(Map<String, String> table, List<Map<String, String>> columns, ControllerColumn controllerColumn, String tablePrefix, ZipOutputStream zip) {

        // 表信息
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));

        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));


        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            //字段注解集合
            StringBuffer annotationList = new StringBuffer();
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

            if (controllerColumn != null) {
                annotationList = combAnnotation(controllerColumn, columnEntity.getAttrname());
            }
            if (annotationList != null) {
                columnEntity.setAnnotationList(annotationList);
            }

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        // zip
        this.zip = zip;

        coding();

    }

    /**
     * 组装最终tableEntity的注解
     * @param controllerColumn
     * @param column
     * @return
     */
    private StringBuffer combAnnotation(ControllerColumn controllerColumn, String column) {
        StringBuffer annotation = new StringBuffer();
        Map<String, StringBuffer> map = new HashMap<>();
        findAnnotation(transformUtils(controllerColumn.getAdd()), Constant.interfaceConstant.ADD.getValue(), column, map);
        findAnnotation(transformUtils(controllerColumn.getDel()), Constant.interfaceConstant.DELETE.getValue(), column, map);
        findAnnotation(transformUtils(controllerColumn.getDetail()), Constant.interfaceConstant.GET.getValue(), column, map);
        findAnnotation(transformUtils(controllerColumn.getUpdate()), Constant.interfaceConstant.UPDATE.getValue(), column, map);
        findAnnotation(transformUtils(controllerColumn.getList()), Constant.interfaceConstant.LIST.getValue(), column, map);
        //todo 目前只处理@NotNull和@NotBlank
        if (map.get(Constant.annotationConatant.NOTNULL) != null) {
            StringBuffer stringBuffer = map.get(Constant.annotationConatant.NOTNULL);
            annotation.append(Constant.annotationConatant.NOTNULL + "(" + stringBuffer.deleteCharAt(stringBuffer.length() - 1) + ")\n");
        }
        if (map.get(Constant.annotationConatant.NOTBLANK) != null) {
            StringBuffer stringBuffer = map.get(Constant.annotationConatant.NOTBLANK);
            annotation.append(Constant.annotationConatant.NOTBLANK + "(" + stringBuffer.deleteCharAt(stringBuffer.length() - 1) + ")\n");
        }
        return annotation.deleteCharAt(annotation.length()-1);
    }

    /**
     * 拼接不同注解的分组信息
     * @param interfaceColumn
     * @param value
     * @param column
     * @param map
     */
    private void findAnnotation(Map<String, BaseColumn> interfaceColumn, String value, String column, Map<String, StringBuffer> map) {
        if (interfaceColumn != null) {
            BaseColumn baseColumn = interfaceColumn.get(column);
            if (map.get(baseColumn.getAnnotation()) != null) {
                StringBuffer stringBuffer = map.get(baseColumn.getAnnotation());
                map.put(baseColumn.getAnnotation(), stringBuffer.append(value + ","));
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                map.put(baseColumn.getAnnotation(), stringBuffer.append(value + ","));
            }
        }
    }

    /**
     * List<BaseColumn>转换成Map<String, BaseColumn>
     * @param interfaceColumn
     * @return
     */
    private Map<String, BaseColumn> transformUtils(List<BaseColumn> interfaceColumn) {
        if (interfaceColumn != null) {
            Map<String, BaseColumn> map = new HashMap<>();
            for (BaseColumn baseColumn : interfaceColumn) {
                map.put(baseColumn.getAttrname(), baseColumn);
            }
            return map;
        }
        return null;
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
