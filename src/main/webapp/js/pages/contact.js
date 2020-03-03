$(function () {
    $(".qm-nav").on("click", "li", function () {
        $("#address").val($(this).html())
    })
})
