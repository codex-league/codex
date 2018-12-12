package pub.codex.core.template.stream.provider;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pub.codex.common.Constant;
import pub.codex.common.column.BaseColumn;
import pub.codex.common.column.ControllerColumn;
import pub.codex.common.column.FieldColumn;
import pub.codex.common.db.entity.ColumnEntity;
import pub.codex.common.db.entity.TableEntity;
import pub.codex.common.utils.BaseUtil;
import pub.codex.common.utils.TranslateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TableEntityProvider {
    /**
     * 设置 组建信息
     *
     * @param table
     * @param columns
     */
    public TableEntity buildTemplateEntity(Map<String, String> table, List<Map<String, String>> columns, ControllerColumn controllerColumn, String tablePrefix) {

        TableEntity tableEntity = new TableEntity();
        List<String> typeList = new ArrayList<>();

        // 表信息
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));

        //添加接口种类
        Map<String, FieldColumn> interfaceMap = BaseUtil.getMethod(controllerColumn);
        if (interfaceMap != null) {
            for (String type : interfaceMap.keySet()) {
                typeList.add(type);
            }
        }
        tableEntity.setInterfaceType(typeList);

        //类名
        String className = TranslateUtil.tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            //字段注解集合
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = TranslateUtil.columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = BaseUtil.getConfig().getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            //字段的注解拼装
            columnEntity.setAnnotationList(combAnnotation(interfaceMap, columnEntity.getAttrname(), columnEntity.getComments()));

            //字段精确或者模糊查询
            columnEntity.setQueryList(combQueryStr(interfaceMap, columnEntity.getAttrname(), columnEntity.getAttrName(), column.get("columnName"), tableEntity));

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        tableEntity.setController(controllerColumn != null);

        return tableEntity;

    }

    /**
     * 组装字段精确或者模糊查询
     *
     * @param interfaceMap
     * @param column
     * @param attrName
     * @param columnName
     * @param tableEntity
     * @return
     */
    private List<String> combQueryStr(Map<String, FieldColumn> interfaceMap, String column, String attrName, String columnName, TableEntity tableEntity) {
        if (interfaceMap == null || interfaceMap.get(Constant.interfaceConstant.LIST.getType()) == null) {
            return null;
        }
        List<String> queryList = new ArrayList<>();
        FieldColumn fieldColumn = interfaceMap.get(Constant.interfaceConstant.LIST.getType());
        Map<String, BaseColumn> interfaceColumn = TranslateUtil.transformUtils(fieldColumn.getTableData());
        BaseColumn baseColumn = interfaceColumn.get(column);
        if (baseColumn.getAnnotation().equals(Constant.query.LIKE.getValue())) {
            queryList.add(".like(!StringUtils.isEmpty(" + tableEntity.getClassname() + "Entity.get" + attrName + "()),\"" + columnName + "\", " + tableEntity.getClassname() + "Entity.get" + attrName + "())");
        }
        if (baseColumn.getAnnotation().equals(Constant.query.EQUAL.getValue())) {
            queryList.add(".eq(!StringUtils.isEmpty(" + tableEntity.getClassname() + "Entity.get" + attrName + "()),\"" + columnName + "\", " + tableEntity.getClassname() + "Entity.get" + attrName + "())");
        }
        return queryList;
    }

    /**
     * 组装最终tableEntity的注解
     *
     * @param interfaceMap
     * @param column
     * @param comments
     * @return
     */
    private List<String> combAnnotation(Map<String, FieldColumn> interfaceMap, String column, String comments) {
        if (interfaceMap == null) {
            return null;
        }
        List<String> annotation = new ArrayList<>();
        Map<String, StringBuffer> map = new HashMap<>();
        for (String type : interfaceMap.keySet()) {
            if (type != Constant.interfaceConstant.LIST.getValue()) {
                findAnnotation(interfaceMap.get(type), type, column, map);
            }
        }
        //todo 目前只处理@NotNull,@NotBlank,@NotEmpty,@Null,@Pattern和@ApiModelProperty
        annotationFactory(annotation, map, Constant.annotationConatant.NOTNULL, null);
        annotationFactory(annotation, map, Constant.annotationConatant.NOTBLANK, null);
        annotationFactory(annotation, map, Constant.annotationConatant.NOTEMPTY, null);
        annotationFactory(annotation, map, Constant.annotationConatant.NULL, null);
        annotationFactory(annotation, map, Constant.annotationConatant.PATTERN, null);
        if (annotationFactory(annotation, map, Constant.annotationConatant.APIMODELPROPERTY, comments) == Constant.flag.APIMODELPROPERTY_ANNOTATION) {
            return annotation;
        }
        if (comments != null && !comments.equals("")) {
            annotation.add(Constant.annotationConatant.APIMODELPROPERTY.getValue() + "(\"" + comments + "\")");
        }
        return annotation;
    }

    /**
     * 注解的完整拼装
     *
     * @param annotation
     * @param map
     * @param annoEnum
     * @param comments
     * @return
     */
    private Constant.flag annotationFactory(List<String> annotation, Map<String, StringBuffer> map, Constant.annotationConatant annoEnum, String comments) {
        if (map.get(annoEnum.getValue()) == null) {
            return Constant.flag.NULL_EXCEPTION;
        }
        if (annoEnum.getValue().equals(Constant.annotationConatant.APIMODELPROPERTY.getValue())) {
            StringBuffer groups = map.get(annoEnum.getValue());
            if (!StringUtils.isEmpty(comments)) {
                annotation.add(Constant.annotationConatant.APIMODELPROPERTY.getValue() + "(describe = \"" + comments + "\",groups = {" + groups.deleteCharAt(groups.length() - 1) + "})");
            }
            return Constant.flag.APIMODELPROPERTY_ANNOTATION;
        }
        StringBuffer groups = map.get(annoEnum.getValue());
        annotation.add(annoEnum.getValue() + "(groups = {" + groups.deleteCharAt(groups.length() - 1) + "})");
        return Constant.flag.EXTERNAL_ANNOTATION;
    }

    /**
     * 拼接不同注解的分组信息
     *
     * @param fieldColumn
     * @param type
     * @param column
     * @param map
     */
    private void findAnnotation(FieldColumn fieldColumn, String type, String column, Map<String, StringBuffer> map) {
        if (fieldColumn == null || fieldColumn.getTableData() == null) {
            return;
        }
        String value = Constant.interfaceConstant.getGroupInfo(type);
        if (StringUtils.isEmpty(value)) {
            return;
        }
        Map<String, BaseColumn> interfaceColumn = TranslateUtil.transformUtils(fieldColumn.getTableData());
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
