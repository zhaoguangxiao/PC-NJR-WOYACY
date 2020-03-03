$(function () {
    $(".tab-nav").on("click", "li", function () {
        $(this).addClass("tn-active").siblings().removeClass("tn-active")
        $(".tab-cont").find("li").eq($(this).index()).fadeIn(300).siblings().fadeOut(300)
        $("#nowPlace").html($(this).data("name"))
    })

    // console.log($(".c-bottom").find("p").eq(1))

    // $(".c-bottom").find("p").eq(1).title = $(this).html()

    $(".c-bottom").on("mouseover","p",function (event) {
        event.stopPropagation();
        $(this).attr("title",$(this).html())
    })
})

