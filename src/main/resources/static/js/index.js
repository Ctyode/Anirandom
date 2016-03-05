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
    var randomizing = false;
    $(".button").click(function() {
        $(".select").removeClass("show");
        if(!randomizing) {
            randomizing = true;
            var genre = $("#genre").find(".title").text();
            var year = $("#year").find(".title").text();
            var rating = $("#rating").find(".title").text();
            $(".info").removeClass("show");
            setTimeout(function() {
                $.getJSON("/anirandom.json", {"genre": (genre == "Genre") ? "undefined" : genre,
                    "year": (year == "Year") ? "undefined" : year,
                    "rating": (rating == "Rating") ? "undefined" : rating}, function(data) {
                    (function($info) {
                        randomizing = false;
                        $info.addClass("show");
                        $info.find(".image").css("background-image", "url(\"" + data["image"] + "\")");
                        $info.find(".title").text(data["title"]);
                        $info.find(".synopsis").text(data["synopsis"]);
                        $info.find(".rating").text(data["rating"].toString());
                    })($(".info"));
                });
            }, 300);
        }
    });
});
