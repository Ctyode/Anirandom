$(function () {
    $(".drop-down-menu").each(function() {
        var $title = $(this).find(".title");
        $(this).click(function() {
            var hasClass = $(this).hasClass("open");
            $(".drop-down-menu").removeClass("open");
            if(!hasClass) {
                $(this).addClass("open");
            }
        });
        $(this).find("li").click(function() {
            $title.text($(this).text());
        });
    });
    $(".button").click(function() {
        var genre = $("#genre").find(".title").text();
        var year = $("#year").find(".title").text();
        var rating = $("#rating").find(".title").text();
        if(genre != "Genre" && year != "Year" && rating != "Rating") {
            $.getJSON("/anirandom.json", {"genre": genre,
                                          "year": year,
                                          "rating": rating}, function(data) {
                (function($info) {
                    $info.find(".image").css("background-image", "url(\"" + data["image"] + "\")");
                    $info.find(".title").text(data["title"]);
                    $info.find(".synopsis").text(data["synopsis"]);
                    $info.find(".rating").text(data["rating"].toString());
                })($(".info"));
            });
        } else {
            // TODO: подсвечивать невыбраные параметры красным
        }
    });
});
