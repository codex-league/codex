package pub.codex.common.db.entity;

import java.util.List;

/**
 * 表数据
 */
public class TableEntity {
    //表的名称
    private String tableName;

    //表的备注
    private String comments;

    //表的主键
    private ColumnEntity pk;

    //表的列名
    private List<ColumnEntity> columns;

    //类名(第一个字母大写)，如：sys_user => SysUser
    private String className;

    //类名(第一个字母小写)，如：sys_user => sysUser
    private String classname;

    //接口类型
    private List<String> interfaceType;

    // 是否生成Controller
    private boolean isController;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<String> getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(List<String> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public boolean isController() {
        return isController;
    }

    public void setController(boolean controller) {
        isController = controller;
    }
}
