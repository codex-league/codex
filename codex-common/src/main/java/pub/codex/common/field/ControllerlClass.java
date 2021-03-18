package pub.codex.common.field;

public class ControllerlClass {
    private ControllerMethod add;

    private ControllerMethod del;

    private ControllerMethod detail;

    private ControllerMethod update;

    private ControllerMethod list;

    public ControllerMethod getAdd() {
        return add;
    }

    public void setAdd(ControllerMethod add) {
        this.add = add;
    }

    public ControllerMethod getDel() {
        return del;
    }

    public void setDel(ControllerMethod del) {
        this.del = del;
    }

    public ControllerMethod getDetail() {
        return detail;
    }

    public void setDetail(ControllerMethod detail) {
        this.detail = detail;
    }

    public ControllerMethod getUpdate() {
        return update;
    }

    public void setUpdate(ControllerMethod update) {
        this.update = update;
    }

    public ControllerMethod getList() {
        return list;
    }

    public void setList(ControllerMethod list) {
        this.list = list;
    }
}
