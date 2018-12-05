var codex = new Vue({
    el: '#codex',
    data: {
        message: 'Code-X 让程序员喝茶的朋友',

        active: ["1", "2"],
        apis: {},
        api: {},
        isNotice: true,
        paramsTableData: null,
        bodyTableData: null,
        activeNames: null,
        host: window.location.host,
        protocol: window.location.protocol,


        activeName: 'first',
        tablePrefix: null,
        prefixList: null,
        tableList: null,
        crudDialog: false,
        crudOperation: false,

        step: 0,

        row: null,
        package: {},
        ruleForm: {
            tablePrefix: null,
            controller: '1',
            controllertype: [],
        },

        operationForm: {
            addName: '',
            aType: '',
            aOuth: '',
            deleteName: '',
            dType: '',
            dOuth: '',
            createName: '',
            cType: '',
            cOuth: '',
            updateName: '',
            uType: '',
            uOuth: '',
            listName: '',
            lType: '',
            lOuth: '',

        },
        tableData: [{
            type: 'string',
            name: 'name',
            outh: '@NotNull'
        }, {
            type: 'int',
            name: 'age',
            outh: '@NotNull'
        }, {
            type: 'string',
            name: 'phone',
            outh: '@NotNull'
        }, {
            type: 'string',
            name: 'email',
            outh: '@NotNull'
        }],


        rules: {
            tablePrefix: [
                {required: true, message: '请选择一个你需要忽略的表名前缀'},
            ],
            controllertype: [
                {required: true, message: '请选择至少一个你需要的Controller'},
            ]
        },

    },
    methods: {
        code(row) {
            this.crudDialog = true;
            this.row = row
        },
        crudDialogCode(formName) {
            var _this = this;
            console.log(this.$refs[formName]);

            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    _this.crudDialog = false;
                    window.location.href = "/codex/code/" + _this.row.tableName + "?tablePrefix=" + _this.ruleForm.tablePrefix;
                    _this.$message({
                        type: 'success',
                        message: '正在下载!'
                    })
                }
            });

        },
        crudOperationCode(formName) {
            this.crudOperation = true;

        },

        //上一步
        previousStep() {
            this.step--;
        },
        //下一步
        nextStep() {

        },
        //提交
        submitAdd(formName) {

        },

        handleChange(val) {
            console.log(val);
        },
        countIndex: function (index, index1, index2) { // 计算索引组合


            var i = "";
            if (index != null) {
                i = i + index
            }

            if (index1 != null) {
                i = i + '-' + index1;
            }

            if (index2 != null) {
                i = i + '-' + index2;
            }
            return i;
        },
        showApi: function (api) {

            this.api = api;

            this.isNotice = false;
        },
        showNotice: function () {
            this.isNotice = true;
        },
        formatRequired: function (row, column) {
            return !!row.required ? '*必填' : '选填';
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
        (function () {
            Vue.http.get('/apix/doc').then(function (response) {
                console.log(response);
                if (response.ok) {
                    _this.apis = response.body;
                } else {
                    alert('获取文档信息错误');
                }
            }, function () {
                alert('获取文档信息错误');
            })
        })();


    }
});
