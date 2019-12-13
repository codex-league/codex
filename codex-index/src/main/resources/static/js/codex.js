var codex = new Vue({
    el: '#codex',
    data: {
        message: 'Code-X 让程序员喝茶的朋友',
        activeName:'first'
     },methods: {
        createApi(){
            var _this = this;

            Vue.http.get('/apix/getDoc').then(function (response) {
                console.log(response);
                if (response.ok) {
                    alert("API文档生成成功")
                } else {
                    alert('API文档生成错误');
                }
            }, function () {
                alert('获取文档信息错误');
            })
        }
    }
});
