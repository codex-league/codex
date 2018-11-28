var apix = new Vue({
    el: '#apix',
    data: {
        message: '项目处于开发阶段，存在一些令人惊奇的BUG ',
        active: ["1","2"],
        apis: {},
        api: {},
        isNotice: true,
        paramsTableData: null,
        bodyTableData: null,
        activeNames: null,
        host: window.location.host,
        protocol: window.location.protocol

    },
    methods: {
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
            return !!row.required ? '*必填':'选填';
        }
    }
    ,
    created: function () {

        var _this = this;

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
    }
});
