$(function () {
    // 请求文章地址
    const getNewsApi = "https://manage.zhou-yuanwai.com";
    // 跳转地址
    // const goUrl = "http://www.zhou-yuanwai.com";
    // const goUrl = "http://localhost:8080";

    // 字符截取
    function strSub(el, limit) {
        var _str = $(el).html();
        if (_str.length > limit) {
            var newStr = _str.substring(0, limit);
            console.log(newStr);
            $(el).html(newStr + "...")
        }
    }

    // 格式化时间
    template.defaults.imports.dateFormat = function (date, format) {
        return moment(date).format('YYYY-MM-DD')
    };

    // 获取当前页面的文章id
    const newsId = parseInt(window.location.href.split("news=")[1]);

    // 文章详情
    axios.get(getNewsApi + "/journalism/findOne.do?uidpk=" + newsId)
        .then(res => {
            // console.log(res.data)
            var newsData = res.data;
            template.defaults.escape = false;// 解析标签
            var newsInfoHtm = template("newsInfo", {n: newsData});
            // console.log(newsInfoHtm)
            $("#art").html(newsInfoHtm)
            document.title = res.data.title
        });


    // 上下文与精选文章
    axios.get(getNewsApi + "/journalism/findAll.do")
        .then(res => {
            // console.log(res);
            // 上下文

            // 存储上下文的数组
            var ab_artArr = [];
            // 对文章的id进行判断，如果为第一篇  显示后面两篇文章，若不是，则显示前后两篇文章
            res.data.forEach((item, index) => {
                if (item.uidpk === newsId) {
                    // console.log(index);
                    if (index === 0) {
                        ab_artArr.push(res.data[index + 1], res.data[index + 2])
                    } else if (index === res.data.length - 1) {
                        ab_artArr.push(res.data[index - 1], res.data[index - 2],)
                    } else {
                        ab_artArr.push(res.data[index - 1], res.data[index + 1])
                    }
                }
            });
            // console.log(ab_artArr)
            var abArtHtm = template("ab-art", {n: ab_artArr});
            // console.log(abArtHtm)
            $("#ab-list").html(abArtHtm); // 上下文完毕


            // 精选文章
            var goodArt = template("good-art", {n: res.data.splice(0, 3)});
            // console.log(goodArt);
            $("#good-list").html(goodArt)
        })

    // 跳转函数
    function goHref(el) {
        $(el).on("click", "li", function () {
            var newsId = $(this).data("uidpk");
            // console.log(newsId);
            window.location.href = "./newsInfo.html?news=" + newsId
        })
    }

    goHref("#ab-list");
    goHref("#good-list")

})
