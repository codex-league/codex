package pub.codex.core.column;

public class ControllerColumn {
    private AddColumn add;

    private DeleteColumn del;

    private DetailColumn detail;

    private UpdateColumn update;

    private ListColumn list;

    public AddColumn getAdd() {
        return add;
    }

    public void setAdd(AddColumn add) {
        this.add = add;
    }

    public DeleteColumn getDel() {
        return del;
    }

    public void setDel(DeleteColumn del) {
        this.del = del;
    }

    public DetailColumn getDetail() {
        return detail;
    }

    public void setDetail(DetailColumn detail) {
        this.detail = detail;
    }

    public UpdateColumn getUpdate() {
        return update;
    }

    public void setUpdate(UpdateColumn update) {
        this.update = update;
    }

    public ListColumn getList() {
        return list;
    }

    public void setList(ListColumn list) {
        this.list = list;
    }
}
