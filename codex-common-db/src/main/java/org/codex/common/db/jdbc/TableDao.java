package org.codex.common.db.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 */

@Repository
public class TableDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryTableList() {

        String sql = "SELECT table_name tableName,ENGINE,table_comment tableComment,create_time createTime FROM information_schema.TABLES WHERE table_schema=(SELECT DATABASE ()) ORDER BY create_time DESC";

        return jdbcTemplate.queryForList(sql);
    }


    public Map<String, ?> queryTable(String tableName) {
        String sql = "SELECT table_name tableName,ENGINE,table_comment tableComment,create_time createTime FROM information_schema.TABLES WHERE table_schema=(SELECT DATABASE ()) AND table_name= ?";

        return jdbcTemplate.queryForMap(sql, tableName);
    }


    public List<?> queryColumns(String tableName) {
        String sql = "SELECT column_name columnName,data_type dataType,column_comment columnComment,column_key columnKey,extra FROM information_schema.COLUMNS WHERE table_name=? and table_schema = (select database()) order by ordinal_position";

        return jdbcTemplate.queryForList(sql,tableName);
    }

}
