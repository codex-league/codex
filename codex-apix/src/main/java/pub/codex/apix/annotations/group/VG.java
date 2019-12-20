package pub.codex.apix.annotations.group;

/**
 * 使用jsr-bean验证规范
 */
@SuppressWarnings(value = "all")
public interface VG {

    // 分页
    public interface Page {
    }

    // 新增
    public interface Add {
    }

    // 删除
    public interface Delete {
    }

    // 查询单条记录
    public interface Get {
    }

    // 列表查询
    public interface List {
    }

    // 更新
    public interface Update {
    }

    // 登录
    public interface Login {
    }

    // 密码
    public interface Passwd {

    }

    // 验证测试
    public interface ValiTest {

    }
}
