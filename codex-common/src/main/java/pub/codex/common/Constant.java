package pub.codex.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 常量
 */
public class Constant {

    /**
     * 接口常量
     */
    public enum interfaceConstant {
        ADD("VG.Add.class", "add", "新增"),
        DELETE("VG.Delete.class", "del", "删除"),
        GET("VG.Get.class", "detail", "查询"),
        UPDATE("VG.Update.class", "update", "修改"),
        LIST("VG.List.class", "list", "列表");

        private String value;
        private String type;
        private String describe;

        interfaceConstant(String value, String type, String describe) {
            this.value = value;
            this.describe = describe;
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        private String getDescribe() {
            return describe;
        }

        public String getType() {
            return type;
        }

        public static String getGroupInfo(String type) {
            if (StringUtils.isNotBlank(type)) {
                for (interfaceConstant obj : interfaceConstant.values()) {
                    if (obj.getType().equals(type)) {
                        return obj.getValue();
                    }
                }
            }
            return "";
        }
    }

    /**
     * 注解常量
     */
    public enum annotationConatant {
        NOTNULL("@NotNull", "被注释的元素不能为null"),
        NOTBLANK("@NotBlank", "String不能为空"),
        NOTEMPTY("@NotEmpty", "被注释的元素不能为空"),
        NULL("@Null", "被注释的元素必须为空"),
        PATTERN("@Pattern", "正则表达式"),
        MAX_LENGTH("@Length", "String不能超过最大长度"),
        APIMODELPROPERTY("@ApiModelProperty", "Apix字段引用注释的注解");

        private String value;
        private String describe;

        annotationConatant(String value, String describe) {
            this.value = value;
            this.describe = describe;
        }

        public String getValue() {
            return value;
        }

        public String getDescribe() {
            return describe;
        }
    }

    /**
     * 标记
     */
    public enum flag {
        NULL_EXCEPTION(-1, "参数为空"),
        EXTERNAL_ANNOTATION(0, "外部注解"),
        APIMODELPROPERTY_ANNOTATION(1, "自有注解@ApiModelProperty");

        private int value;
        private String describe;

        flag(int value, String describe) {
            this.value = value;
            this.describe = describe;
        }

        public int getValue() {
            return value;
        }

        public String getDescribe() {
            return describe;
        }
    }

    /**
     * 查询常量
     */
    public enum query {
        LIKE("like", "模糊查询"),
        EQUAL("equal", "精确查询");

        private String value;
        private String describe;

        query(String value, String describe) {
            this.value = value;
            this.describe = describe;
        }

        public String getValue() {
            return value;
        }

        public String getDescribe() {
            return describe;
        }
    }
}
