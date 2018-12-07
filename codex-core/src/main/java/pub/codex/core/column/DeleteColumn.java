package pub.codex.core.column;

import java.util.List;

public class DeleteColumn {
    private String name;
    private List<BaseColumn> tableData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseColumn> getTableData() {
        return tableData;
    }

    public void setTableData(List<BaseColumn> tableData) {
        this.tableData = tableData;
    }
}
