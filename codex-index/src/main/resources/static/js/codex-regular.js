var codex = new Vue({
        el: '#regular',
        data: {
            isMatching: "",
            reg: "",
            expression: ""
        },

        methods: {
            regular: function (reg, expression) {
                if(reg===""||reg===null||expression===""||expression===null){
                    return false;
                }
                var patt1=new RegExp(reg);
                if(patt1.exec(expression)!==null){
                    this.isMatching=patt1.exec(expression);
                }
                else{
                    this.isMatching="无匹配";
                }
            }
        }
    }
);