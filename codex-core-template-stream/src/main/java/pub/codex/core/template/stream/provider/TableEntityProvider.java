package pub.codex.core.template.stream.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.codex.common.Constant;
import pub.codex.common.field.BaseField;
import pub.codex.common.field.ControllerMethod;
import pub.codex.common.field.ControllerlClass;
import pub.codex.common.db.entity.ColumnEntity;
import pub.codex.common.db.entity.TableEntity;
import pub.codex.common.utils.BaseUtil;
import pub.codex.common.utils.TranslateUtil;
import pub.codex.core.template.stream.build.ValidateAnnotationBuild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TableEntityProvider {

    @Autowired
    private ValidateAnnotationBuild validateAnnotationBuild;


    /**
     * 设置 组建信息
     *
     * @param table
     * @param columns
     */
    public TableEntity buildTemplateEntity(Map<String, String> table, List<Map<String, String>> columns, ControllerlClass controllerlClass, String tablePrefix) {

        TableEntity tableEntity = new TableEntity();
        List<String> typeList = new ArrayList<>();

        // 表信息
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));

        //添加接口种类
        Map<String, ControllerMethod> interfaceMap = BaseUtil.getPropertyByMethod(controllerlClass);
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

        tableEntity.setController(controllerlClass != null);

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
    private List<String> combQueryStr(Map<String, ControllerMethod> interfaceMap, String column, String attrName, String columnName, TableEntity tableEntity) {
        if (interfaceMap == null || interfaceMap.get(Constant.interfaceConstant.LIST.getType()) == null) {
            return null;
        }
        List<String> queryList = new ArrayList<>();
        ControllerMethod controllerMethod = interfaceMap.get(Constant.interfaceConstant.LIST.getType());
        Map<String, BaseField> interfaceColumn = TranslateUtil.transformUtils(controllerMethod.getFields());
        BaseField baseField = interfaceColumn.get(column);
        if (baseField.getAnnotation() != null) {
            if (baseField.getAnnotation().equals(Constant.query.LIKE.getValue())) {
                queryList.add(".like(!StringUtils.isEmpty(" + tableEntity.getClassname() + "Entity.get" + attrName + "()),\"" + columnName + "\", " + tableEntity.getClassname() + "Entity.get" + attrName + "())");
            }
            if (baseField.getAnnotation().equals(Constant.query.EQUAL.getValue())) {
                queryList.add(".eq(!StringUtils.isEmpty(" + tableEntity.getClassname() + "Entity.get" + attrName + "()),\"" + columnName + "\", " + tableEntity.getClassname() + "Entity.get" + attrName + "())");
            }
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
    private List<String> combAnnotation(Map<String, ControllerMethod> interfaceMap, String column, String comments) {
        if (interfaceMap == null) {
            return null;
        }
        List<String> annotation = new ArrayList<>();
        Map<String, StringBuffer> groupMap = new HashMap<>();
        for (String type : interfaceMap.keySet()) {
            if (!type.equals(Constant.interfaceConstant.LIST.getValue())) {
                findAnnotation(interfaceMap.get(type), type, column, groupMap);
            }
        }

        //todo 目前只处理@NotNull,@NotBlank,@NotEmpty,@Null,@Pattern和@ApiModelProperty
        validateAnnotationBuild.baseAnnotationFactory(annotation, groupMap, Constant.annotationConatant.NOTNULL);
        validateAnnotationBuild.baseAnnotationFactory(annotation, groupMap, Constant.annotationConatant.NOTEMPTY);
        validateAnnotationBuild.baseAnnotationFactory(annotation, groupMap, Constant.annotationConatant.NULL);
        validateAnnotationBuild.baseAnnotationFactory(annotation, groupMap, Constant.annotationConatant.PATTERN);

        validateAnnotationBuild.NotBlankAnnotationFactory(annotation, groupMap);

        validateAnnotationBuild.apiModelPropertyAnnotationFactory(annotation, groupMap, comments);

        return annotation;
    }


    /**
     * 拼接不同注解的分组信息
     *
     * @param controllerMethod
     * @param type
     * @param column
     * @param map
     */
    private void findAnnotation(ControllerMethod controllerMethod, String type, String column, Map<String, StringBuffer> map) {
        if (controllerMethod == null || controllerMethod.getFields() == null) {
            return;
        }

        String value = Constant.interfaceConstant.getGroupInfo(type);
        if (org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return;
        }

        Map<String, BaseField> interfaceColumn = TranslateUtil.transformUtils(controllerMethod.getFields());
        BaseField baseField = interfaceColumn.get(column);

        StringBuffer stringBuffer;
        if (map.get(baseField.getAnnotation()) != null) {
            stringBuffer = map.get(baseField.getAnnotation());
        } else {
            stringBuffer = new StringBuffer();
        }
        map.put(baseField.getAnnotation(), stringBuffer.append(value + ", "));

    }

}
