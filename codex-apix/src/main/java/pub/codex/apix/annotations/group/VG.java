package pub.codex.apix.annotations.group;

/**
 * 使用jsr-bean验证规范
 */
@SuppressWarnings(value = "all")
public interface VG {

    // 分页
    interface Page{
    }

    // 新增
    interface Add {
    }

    // 删除
    interface Delete {
    }

    // 查询单条记录
    interface Get {
    }

    // 列表查询
    interface List {
    }

    // 更新
    interface Update {
    }

    // 登录
    interface Login {
    }

    // 密码
    interface Passwd{

    }

    // 验证测试
    interface ValiTest {

    }
    // 验证开库
    interface ValiOpen {

    }

    // 验证分配
    interface ValiDistr {

    }

    // 验证添加/删除角色权限时的参数
    interface ValidRolePermission {

    }

    // 验证添加/删除用户角色时的参数
    interface ValidUserRole {

    }

    // 验证添加/删除用户部门时的参数
    interface ValidUserDepartment {

    }

    // 判断数据归集是否可用时的参数
    interface ValidDataCollectionAvailable {

    }

}
