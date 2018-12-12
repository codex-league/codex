var codex = new Vue({
    el: '#codex',
    data: {
        message: 'Code-X 让程序员喝茶的朋友',

        interfaces: {
            add: "增加",
            update: "修改",
            del: "删除",
            detail: "详情",
            list: "列表"
        },
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
        jdbc:{},
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
        options: [
            {value: '@ApiModelProperty', label: '选填', kind: "add,update,del,detail"},
            {value: 'like', label: '模糊查询',  kind: 'list'},
            {value: 'equal', label: '精确查询',  kind: 'list'},
            {value: '@NotNull', label: '@NotNull', type: 'Integer,Long,Short,Byte,Double,Float,Boolean,Date', kind: 'add,update,del,detail'},
            {value: '@NotBlank', label: '@NotBlank',  type: "String", kind: 'add,update,del,detail'},
            {value: '@NotEmpty', label: '@NotEmpty',  type: "String", kind: 'add,update,del,detail'},
            {value: '@Null', label: '@Null',  type: "String,Integer,Long,Short,Byte,Double,Float,Boolean,Date", kind: 'add,update,del,detail'},
            {value: '@Pattern', label: '@Pattern',  type: "String", kind: 'add,update,del,detail'}
            // {value: '@Max', label: '@Max'},
            // {value: '@Min', label: '@Min'},
            // {value: '@Digits', label: '@Digits'},
            // {value: '@Email', label: '@Email'},
            // {value: '@Future', label: '@Future'},
            // {value: '@AssertFalse', label: '@AssertFalse'},
            // {value: '@AssertTrue', label: '@AssertTrue'},
            // {value: '@DecimalMax', label: '@DecimalMax'},
            // {value: '@DecimalMin', label: '@DecimalMin'},
            // {value: '@Size', label: '@Size'},
            // {value: '@FutureOrPresent', label: '@FutureOrPresent'},
            // {value: '@Negative', label: '@Negative'},
            // {value: '@NegativeOrZero', label: '@NegativeOrZero'},
            // {value: '@Past', label: '@Past'},
            // {value: '@PastOrPresent', label: '@PastOrPresent'},
            // {value: '@Positive', label: '@Positive'},
            // {value: '@PositiveOrZero', label: '@PositiveOrZero'}
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
            _this.modules = {};
            this.$refs[formName].validate(function (valid) {

                if (_this.ruleForm.controllertype === '1') {
                    if (valid) {
                        _this.crudDialog = false;
                        window.location.href = "/codex/code/" + _this.row.tableName + "?tablePrefix=" + _this.ruleForm.tablePrefix;
                        _this.$message({
                            type: 'success',
                            message: '正在下载!'
                        })
                    }
                }

                if (_this.ruleForm.controllertype === '2'){
                    for (var i in _this.ruleForm.controllertype) {
                        _this.modules[_this.ruleForm.controllertype[i]] = {
                            tableData: JSON.parse(JSON.stringify(_this.info)),
                            name: _this.interfaces[_this.ruleForm.controllertype[i]],
                            kind: _this.ruleForm.controllertype[i]
                        }
                    }
                    _this.crudOperation = true;

                }
            });

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
        // 获取可选注解
        getOptions(type, kind) {

            var tempOptions = [];
            this.options.forEach(item => {
                if((!item['type'] || item.type.indexOf(type) > -1 )
                    && (!item['kind'] || item.kind.indexOf(kind) > -1)){ // 如果 type没有条件，直接通过
                    tempOptions.push(JSON.parse(JSON.stringify(item)));
                }
            });
            return tempOptions;
        },

        //提交
        apply() {

            var _this = this;
            (function (url, data) {
                if (url && data) {
                    var form = $('<form></form>');
                    form.attr('action', url + "?tablePrefix=" + _this.ruleForm.tablePrefix);
                    form.attr('method', 'post');

                    var input = $('<input type="text" />');
                    input.attr('name', 'data');
                    input.attr('value', JSON.stringify(data));
                    form.append(input);

                    form.appendTo('body').submit().remove();
                }
            })("/codex/generate/" + _this.row.tableName, _this.modules);

        },
    }
    ,
    created: function () {

        var _this = this;
        (function () {
            Vue.http.get('/codex/data').then(function (response) {
                if (response.ok) {
                    console.log("111",response.body)
                    _this.tableList = response.body.tableList;
                    _this.package = response.body.package;
                    _this.jdbc=response.body.jdbc;
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
