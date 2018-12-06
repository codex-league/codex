package pub.codex.core.column;

import java.util.List;

public class ControllerColumn {
    List<BaseColumn> add;

    List<BaseColumn> del;

    List<BaseColumn> detail;

    List<BaseColumn> update;

    List<BaseColumn> list;

    public List<BaseColumn> getAdd() {
        return add;
    }

    public void setAdd(List<BaseColumn> add) {
        this.add = add;
    }

    public List<BaseColumn> getDel() {
        return del;
    }

    public void setDel(List<BaseColumn> del) {
        this.del = del;
    }

    public List<BaseColumn> getDetail() {
        return detail;
    }

    public void setDetail(List<BaseColumn> detail) {
        this.detail = detail;
    }

    public List<BaseColumn> getUpdate() {
        return update;
    }

    public void setUpdate(List<BaseColumn> update) {
        this.update = update;
    }

    public List<BaseColumn> getList() {
        return list;
    }

    public void setList(List<BaseColumn> list) {
        this.list = list;
    }
}
