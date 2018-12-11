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

        // 表信息
        TableEntity tableEntity = new TableEntity();

        tableEntity.setTableName(table.get("tableName" ));
        tableEntity.setComments(table.get("tableComment" ));

        //添加接口种类
        tableEntity.setInterfaceType(BaseUtil.getMethod(controllerColumn));

        String className = TranslateUtil.tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            //字段注解集合
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName" ));
            columnEntity.setDataType(column.get("dataType" ));
            columnEntity.setComments(column.get("columnComment" ));
            columnEntity.setExtra(column.get("extra" ));

            //列名转换成Java属性名
            String attrName = TranslateUtil.columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = BaseUtil.getConfig().getString(columnEntity.getDataType(), "unknowType" );
            columnEntity.setAttrType(attrType);

            //字段的注解拼装
            List<String> annotationList = combAnnotation(controllerColumn, columnEntity.getAttrname(), columnEntity.getComments());
            columnEntity.setAnnotationList(annotationList);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        tableEntity.setController(controllerColumn != null);

        return tableEntity;

    }

    /**
     * 组装最终tableEntity的注解
     *
     * @param controllerColumn
     * @param column
     * @param comments
     * @return
     */
    private List<String> combAnnotation(ControllerColumn controllerColumn, String column, String comments) {
        if (controllerColumn == null) {
            return null;
        }
        List<String> annotation = new ArrayList<>();
        Map<String, StringBuffer> map = new HashMap<>();
        findAnnotation(controllerColumn.getAdd(), Constant.interfaceConstant.ADD.getValue(), column, map);
        findAnnotation(controllerColumn.getDel(), Constant.interfaceConstant.DELETE.getValue(), column, map);
        findAnnotation(controllerColumn.getDetail(), Constant.interfaceConstant.GET.getValue(), column, map);
        findAnnotation(controllerColumn.getUpdate(), Constant.interfaceConstant.UPDATE.getValue(), column, map);
        findAnnotation(controllerColumn.getList(), Constant.interfaceConstant.LIST.getValue(), column, map);
        //todo 目前只处理@NotNull,@NotBlank,@NotEmpty,@Null,@Pattern和@ApiModelProperty
        annotationFactory(annotation, map, Constant.annotationConatant.NOTNULL, null);
        annotationFactory(annotation, map, Constant.annotationConatant.NOTBLANK, null);
        annotationFactory(annotation, map, Constant.annotationConatant.NOTEMPTY, null);
        annotationFactory(annotation, map, Constant.annotationConatant.NULL, null);
        annotationFactory(annotation, map, Constant.annotationConatant.PATTERN, null);
        if (annotationFactory(annotation, map, Constant.annotationConatant.APIMODELPROPERTY, comments) == Constant.flag.APIMODELPROPERTY_ANNOTATION) {
            return annotation;
        }
        if (comments != null && !comments.equals("" )) {
            annotation.add(Constant.annotationConatant.APIMODELPROPERTY.getValue() + "(\"" + comments + "\")" );
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
            if (comments != null && !comments.equals("" )) {
                annotation.add(Constant.annotationConatant.APIMODELPROPERTY.getValue() + "(describe = \"" + comments + "\",groups = {" + groups.deleteCharAt(groups.length() - 1) + "})" );
            }
            return Constant.flag.APIMODELPROPERTY_ANNOTATION;
        }
        StringBuffer groups = map.get(annoEnum.getValue());
        annotation.add(annoEnum.getValue() + "(groups = {" + groups.deleteCharAt(groups.length() - 1) + "})" );
        return Constant.flag.EXTERNAL_ANNOTATION;
    }

    /**
     * 拼接不同注解的分组信息
     *
     * @param fieldColumn
     * @param value
     * @param column
     * @param map
     */
    private void findAnnotation(FieldColumn fieldColumn, String value, String column, Map<String, StringBuffer> map) {
        if (fieldColumn == null || fieldColumn.getTableData() == null) {
            return;
        }
        Map<String, BaseColumn> interfaceColumn = TranslateUtil.transformUtils(fieldColumn.getTableData());
        BaseColumn baseColumn = interfaceColumn.get(column);
        if (map.get(baseColumn.getAnnotation()) != null) {
            StringBuffer stringBuffer = map.get(baseColumn.getAnnotation());
            map.put(baseColumn.getAnnotation(), stringBuffer.append(value + "," ));
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            map.put(baseColumn.getAnnotation(), stringBuffer.append(value + "," ));
        }

    }

}
