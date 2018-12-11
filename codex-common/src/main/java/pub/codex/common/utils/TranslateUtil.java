package pub.codex.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import pub.codex.common.column.BaseColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslateUtil {
    /**
     * List<BaseColumn>转换成Map<String, BaseColumn>
     *
     * @param interfaceColumn
     * @return
     */
    public static Map<String, BaseColumn> transformUtils(List<BaseColumn> interfaceColumn) {
        if (interfaceColumn == null) {
            return null;
        }
        Map<String, BaseColumn> map = new HashMap<>();
        for (BaseColumn baseColumn : interfaceColumn) {
            map.put(baseColumn.getAttrname(), baseColumn);
        }
        return map;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }
}
