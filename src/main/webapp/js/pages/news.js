$(function () {
    // 字符截取
    function strSub(el, limit) {
        var _str = $(el).html();
        if (_str.length > limit) {
            var newStr = _str.substring(0, limit);
            // console.log(newStr);
            $(el).html(newStr + "...")
        }
    }

    // 请求文章地址
    const getNewsApi = "https://manage.zhou-yuanwai.com";
    // 跳转地址
    // const goUrl = "http://www.zhou-yuanwai.com";
    // const goUrl = "http://localhost:8080";

    // 格式化时间
    template.defaults.imports.dateFormat = function (date, format) {
        return moment(date).format('YYYY-MM-DD')
    };

    template.defaults.imports.getDay = function (val) {
        return val.split("-")[2]
    };

    template.defaults.imports.getTime = function (val) {
        return val.split("-")[0] + "-" + val.split("-")[1]
    };

    // 数组分组
    function group(array, subGroupLength) {
        let index = 0;
        let newArray = [];
        while (index < array.length) {
            newArray.push(array.slice(index, index += subGroupLength));
        }
        return newArray;
    }

    // 存储总数据
    var allData = [];
    // 当前页数
    var page = 0;

    // 文章详情
    axios.get(getNewsApi + "/journalism/findAll.do")
        .then(res => {
            // console.log(res);

            allData = group(res.data, 4);
            console.log(allData);


            template.defaults.escape = false;
            var newsHtm = template("news1", {n: allData[page]});
            // console.log(newsHtm)
            $("#news-list").html(newsHtm);
            strSub(".te", 51);
            strSub(".te1", 78);
        });

    // 点击跳转
    $("#news-list").on("click", "li", function () {
        var newsId = $(this).data("uidpk");
        window.location.href = "./newsInfo.html?news=" + newsId
    });

    // 上下页

    // 上
    $(".prev-btn").on("click", function () {
        $(".prev-btn").html("上一页")
        $(".next-btn").html("下一页")
        console.log(allData.length)
        if (page <= 0) {
            // console.log(123)
            $(this).html("没有了")
        }
        page = page - 1;
        template.defaults.escape = false;
        var newsHtm = template("news1", {n: allData[page]});
        // console.log(newsHtm)
        $("#news-list").html(newsHtm);
        strSub(".te", 51);
        strSub(".te1", 78);
    });

    // 下
    $(".next-btn").on("click", function () {
        $(".prev-btn").html("上一页")
        $(".next-btn").html("下一页")
        console.log(allData.length)
        if (allData.length >= page) {
            // console.log(123)
            $(this).html("没有了")
        }
        page = page + 1;
        template.defaults.escape = false;
        var newsHtm = template("news1", {n: allData[page]});
        // console.log(newsHtm)
        $("#news-list").html(newsHtm);
        strSub(".te", 51);
        strSub(".te1", 78);
    })


})
