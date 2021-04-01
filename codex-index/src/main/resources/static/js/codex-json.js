var codex = new Vue({
        el: '#json',
        data: {
            content: '',
            rules: {
                content: [{required: true, message: '验证内容不能为空'},],
            },
        },
        methods: {
            //格式化json数据
            formatData(formName) {
                var _this = this;
                var content = _this.content;
                var formatJson = function (json, options) {
                    var reg = null,
                        formatted = '',
                        pad = 0,
                        PADDING = '    ';
                    options = options || {};
                    options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
                    options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
                    if (typeof json !== 'string') {
                        json = JSON.stringify(json);
                    } else {
                        json = JSON.parse(json);
                        json = JSON.stringify(json);
                    }
                    reg = /([\{\}])/g;
                    json = json.replace(reg, '\r\n$1\r\n');
                    reg = /([\[\]])/g;
                    json = json.replace(reg, '\r\n$1\r\n');
                    reg = /(\,)/g;
                    json = json.replace(reg, '$1\r\n');
                    reg = /(\r\n\r\n)/g;
                    json = json.replace(reg, '\r\n');
                    reg = /\r\n\,/g;
                    json = json.replace(reg, ',');
                    if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                        reg = /\:\r\n\{/g;
                        json = json.replace(reg, ':{');
                        reg = /\:\r\n\[/g;
                        json = json.replace(reg, ':[');
                    }
                    if (options.spaceAfterColon) {
                        reg = /\:/g;
                        json = json.replace(reg, ':');
                    }
                    (json.split('\r\n')).forEach(function (node, index) {
                        var i = 0,
                            indent = 0,
                            padding = '';

                        if (node.match(/\{$/) || node.match(/\[$/)) {
                            indent = 1;
                        } else if (node.match(/\}/) || node.match(/\]/)) {
                            if (pad !== 0) {
                                pad -= 1;
                            }
                        } else {
                            indent = 0;
                        }

                        for (i = 0; i < pad; i++) {
                            padding += PADDING;
                        }
                        formatted += padding + node + '\r\n';
                        pad += indent;
                    });
                    return formatted;
                };
                try {
                    var resultJson = formatJson(content);
                } catch (e) {
                    alert("有错哦！！！！！！！" + e.toString())
                }
                _this.content = resultJson;
            }
            ,
        }
    })
;



