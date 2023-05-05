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
public class TableEntityHandle {

    @Autowired
    private ValidateAnnotationBuild validateAnnotationBuild;


    /**
     * 设置组件信息
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

            //字段的分组的注解拼装
            columnEntity.setAnnotationList(combAnnotationByGroup(interfaceMap, columnEntity.getAttrname(), columnEntity.getComments()));

            //字段的添加的注解拼装
            columnEntity.setAnnotationListByAdd(combAnnotationByType(interfaceMap, columnEntity.getAttrname(), columnEntity.getComments(), Constant.InterfaceConstant.ADD));

            //字段的修改的注解拼装
            columnEntity.setAnnotationListByUpdate(combAnnotationByType(interfaceMap, columnEntity.getAttrname(), columnEntity.getComments(), Constant.InterfaceConstant.UPDATE));

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
     * 组装最终tableEntity的注解
     *
     * @param interfaceMap
     * @param column
     * @param comments
     * @return
     */
    private List<String> combAnnotationByGroup(Map<String, ControllerMethod> interfaceMap, String column, String comments) {
        if (interfaceMap == null) {
            return null;
        }
        List<String> annotations = new ArrayList<>();
        Map<String, StringBuffer> groupMap = new HashMap<>();
        for (String type : interfaceMap.keySet()) {
            if (!type.equals(Constant.InterfaceConstant.LIST.getValue())) {
                findAnnotation(type, interfaceMap.get(type), column, groupMap);
            }
        }

        //todo 目前只处理@NotNull,@NotBlank,@NotEmpty,@Null,@Pattern和@ApiModelProperty

        validateAnnotationBuild.baseAnnotationFactory(annotations, groupMap, Constant.annotationConatant.NOTNULL);
        validateAnnotationBuild.baseAnnotationFactory(annotations, groupMap, Constant.annotationConatant.NOTBLANK);
        validateAnnotationBuild.baseAnnotationFactory(annotations, groupMap, Constant.annotationConatant.NOTEMPTY);
        validateAnnotationBuild.baseAnnotationFactory(annotations, groupMap, Constant.annotationConatant.NULL);
        validateAnnotationBuild.baseAnnotationFactory(annotations, groupMap, Constant.annotationConatant.PATTERN);

        validateAnnotationBuild.apiModelPropertyAnnotationFactory(annotations, groupMap, comments);

        return annotations;
    }


    /**
     * 组装最终tableEntity的注解
     *
     * @param interfaceMap
     * @param column
     * @param comments
     * @return
     */
    private List<String> combAnnotationByType(Map<String, ControllerMethod> interfaceMap, String column, String comments, Constant.InterfaceConstant type) {

        ControllerMethod controllerMethod = interfaceMap.get(type.getType());

        if (controllerMethod == null) {
            return null;
        }

        Map<String, BaseField> interfaceColumn = TranslateUtil.transformUtils(controllerMethod.getFields());

        BaseField baseField = interfaceColumn.get(column);

        List<String> annotations = new ArrayList<>();
        if (StringUtils.isNotBlank(baseField.getAnnotation())) {
            if (!baseField.getAnnotation().contains(Constant.annotationConatant.APIMODELPROPERTY.getValue())) {
                annotations.add(baseField.getAnnotation());
            }

            annotations.add(Constant.annotationConatant.APIMODELPROPERTY.getValue() + "(\"" + comments + "\")");

        }
        return annotations;
    }


    /**
     * 拼接不同注解的分组信息
     *
     * @param controllerMethod
     * @param type
     * @param column
     * @param map
     */
    private void findAnnotation(String type, ControllerMethod controllerMethod, String column, Map<String, StringBuffer> map) {
        if (controllerMethod == null || controllerMethod.getFields() == null) {
            return;
        }

        String value = Constant.InterfaceConstant.getGroupInfo(type);
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
