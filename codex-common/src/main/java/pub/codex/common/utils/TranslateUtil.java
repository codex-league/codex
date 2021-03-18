package pub.codex.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import pub.codex.common.field.BaseField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TranslateUtil {
    /**
     * List<BaseColumn>转换成Map<String, BaseColumn>
     *
     * @param interfaceColumn
     * @return
     */
    public static Map<String, BaseField> transformUtils(List<BaseField> interfaceColumn) {
        if (interfaceColumn == null) {
            return null;
        }

        return interfaceColumn.stream().collect(Collectors.toMap(BaseField::getAttrname, v -> v));

    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }
}
