var codex = new Vue({
    el: '#codex',
    data: {
        message: 'Code-X 让程序员喝茶的朋友',
        activeName: 'first',
        tablePrefix: null,
        prefixList: null,
        tableList: null,
        crudDialog: false,
        row: null,
        package: {},
        ruleForm:{
            tablePrefix: null
        },
        rules: {
            tablePrefix: [
                { required: true, message: '请选择一个你需要忽略的表名前缀'},
            ]}
    },
    methods: {
        code(row) {
            if (this.prefixList && this.prefixList.length > 1) { // 大于一以上，需要选择
                this.crudDialog = true;
                this.row = row
            } else {
                if (this.prefixList && this.prefixList.length === 1) {
                    this.tablePrefix = this.prefixList[0]
                }
                this.generatorCode(row);
            }
        },
        crudDialogCode(formName) {
            var _this = this;
            console.log(this.$refs[formName]);
            this.$refs[formName].validate(function(valid){
                if(valid){
                    _this.crudDialog = false;
                    window.location.href = "/code/" + _this.row.tableName + "?tablePrefix=" + _this.ruleForm.tablePrefix;
                    _this.$message({
                        type: 'success',
                        message: '正在下载!'
                    })
                }
            });
        },
        generatorCode(row) {
            var _this = this;

            this.$confirm('确定要生成CRUD代码吗？, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                window.location.href = "/codex/code/" + row.tableName + "?tablePrefix=" + _this.tablePrefix;
                _this.$message({
                    type: 'success',
                    message: '正在下载!'
                })

            }).catch(function () {
                _this.$message({
                    type: 'info',
                    message: '操作已取消'
                })
            })
        }
    }
    ,
    created: function () {

        var _this = this;
        (function () {
            Vue.http.get('/codex/data').then(function (response) {
                if (response.ok) {
                    _this.tableList = response.body.tableList;
                    _this.package = response.body.package;
                    _this.prefixList = !!response.body.prefix ? response.body.prefix.split(",") : [];
                } else {
                    alert('获取数据库表信息错误');
                }
            }, function () {
                alert('获取数据库表信息错误');
            })
        })();


    }
});
