$(function () {
    $(".drop-down-menu").click(function () {

        var hasClass = $(this).hasClass("open");

        $(".drop-down-menu").removeClass("open");
        if(!hasClass) {
            $(this).addClass("open");
        }
    })
});