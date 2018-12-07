var codex = new Vue({
    el: '#codex',
    data: {
        message: 'Code-X 让程序员喝茶的朋友',

        active: ["1", "2"],
        interfaces: {
            add: "增加",
            update: "修改",
            del: "删除",
            detail: "详情",
            list: "列表"
        },
        apis: {},
        api: {},
        isNotice: true,
        paramsTableData: null,
        bodyTableData: null,
        activeNames: null,
        host: window.location.host,
        protocol: window.location.protocol,
        modules: {},

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

        operationForm: {},
        tableData: [{
            attrType: '',
            attrname: '',
            comments: ''
        }],
        value: '',
        options: [{value: '@ApiModelProperty', label: '选填'}, {value: '@NotNull', label: '@NotNull'}, {
            value: '@NotBlank',
            label: '@NotBlank'
        }, {
            value: '@NotEmpty',
            label: '@NotEmpty'
        }, {value: '@Null', label: '@Null'}, {value: '@Pattern', label: '@Pattern'}, {value: '@Max', label: '@Max'}, {
            value: '@Min',
            label: '@Min'
        }, {value: '@Digits', label: '@Digits'}, {value: '@Email', label: '@Email'}, {
            value: '@Future',
            label: '@Future'
        }, {
            value: '@AssertFalse',
            label: '@AssertFalse'
        }, {value: '@AssertTrue', label: '@AssertTrue'}, {value: '@DecimalMax', label: '@DecimalMax'}, {
            value: '@DecimalMin',
            label: '@DecimalMin'
        }, {value: '@Size', label: '@Size'}, {
            value: '@FutureOrPresent',
            label: '@FutureOrPresent'
        }, {value: '@Negative', label: '@Negative'}, {
            value: '@NegativeOrZero',
            label: '@NegativeOrZero'
        }, {
            value: '@Past',
            label: '@Past'
        }, {value: '@PastOrPresent', label: '@PastOrPresent'}, {
            value: '@Positive',
            label: '@Positive'
        }, {value: '@PositiveOrZero', label: '@PositiveOrZero'}
        ],


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

            //这里异步获取
            var _this = this;
            (function () {
                Vue.http.get('/codex/info/' + _this.row.tableName).then(function (response) {
                    if (response.ok) {
                        _this.info = response.body.info;

                    }
                })
            })();
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
        crudOperationCode: function (formName) {

            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            setTimeout(() => {
                loading.close();
            }, 2000);

            var _this = this;
            _this.modules = {};
            for (var i in _this.ruleForm.controllertype) {
                _this.modules[_this.ruleForm.controllertype[i]] = {
                    tableData: JSON.parse(JSON.stringify(_this.info)),
                    name: _this.interfaces[_this.ruleForm.controllertype[i]]
                }
            }
            _this.crudOperation = true;

        },

        //上一步
        previousStep() {
            this.step--;
        },
        //下一步
        nextStep() {
            if (this.step++ > this.ruleForm.controllertype.length) {
                this.step = 0;
            }
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
