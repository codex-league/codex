package pub.codex.common.column;


public class BaseColumn {
    //字段名
    private String attrname;
    //字段类型
    private String attrType;
    //最大长度
    private String maxLength;
    //字段注释
    private String comments;
    //主键标识
    private String primary;

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }
}
