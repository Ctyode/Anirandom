$(function () {
    $(".drop-down-menu").click(function() {

        var hasClass = $(this).hasClass("open");

        $(".drop-down-menu").removeClass("open");
        if(!hasClass) {
            $(this).addClass("open");
        }
    })

    $(".button").click(function() {
        $.getJSON("/anirandom.json", {}, function(data) {
            console.log(data)
        })


    })
});