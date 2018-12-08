package pub.codex.common;

public class Constant {
    public enum interfaceConstant {
        ADD("VG.Add.class", "新增"),
        DELETE("VG.Delete.class", "删除"),
        GET("VG.Get.class", "查询"),
        UPDATE("VG.Update.class", "修改"),
        LIST("VG.List.class", "列表");

        private String value;
        private String describe;

        interfaceConstant(String value, String describe) {
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

    public enum annotationConatant{
        NOTNULL("@NotNull","被注释的元素不能为null"),
        NOTBLANK("@NotBlank","String不能为空"),
        NOTEMPTY("@NotEmpty","被注释的元素不能为空"),
        NULL("@Null","被注释的元素必须为空"),
        PATTERN("@Pattern","正则表达式"),
        APIMODELPROPERTY("@ApiModelProperty","Apix字段引用注释的注解");

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

    public enum flag{
        NULL_EXCEPTION(-1,"参数为空"),
        EXTERNAL_ANNOTATION(0,"外部注解"),
        APIMODELPROPERTY_ANNOTATION(1,"自有注解@ApiModelProperty");
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
}
