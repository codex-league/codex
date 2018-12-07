package pub.codex.common.models;

public class ControllerColumn {
    private FieldColumn add;

    private FieldColumn del;

    private FieldColumn detail;

    private FieldColumn update;

    private FieldColumn list;

    public FieldColumn getAdd() {
        return add;
    }

    public void setAdd(FieldColumn add) {
        this.add = add;
    }

    public FieldColumn getDel() {
        return del;
    }

    public void setDel(FieldColumn del) {
        this.del = del;
    }

    public FieldColumn getDetail() {
        return detail;
    }

    public void setDetail(FieldColumn detail) {
        this.detail = detail;
    }

    public FieldColumn getUpdate() {
        return update;
    }

    public void setUpdate(FieldColumn update) {
        this.update = update;
    }

    public FieldColumn getList() {
        return list;
    }

    public void setList(FieldColumn list) {
        this.list = list;
    }
}
