$(function () {
    // 字符截取
    function strSub(el, limit) {
        var _str = $(el).html();
        if (_str.length > limit) {
            var newStr = _str.substring(0, limit);
            console.log(newStr);
            $(el).html(newStr + "...")
        }
    }
})
