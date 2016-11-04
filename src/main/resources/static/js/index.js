$(function () {
    $(document.body).click(function(e) {
        $(".drop-down-menu").removeClass("open");
        $(".love").removeClass("show");
    });
    $(".drop-down-menu").each(function() {
        var $title = $(this).find(".title");
        $(this).click(function(e) {
            e.stopPropagation();
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
    $(".love-button").click(function (e) {
        $(".love").toggleClass("show");
        e.stopPropagation();
    });
    $(".add-to-plan-to-watch").click(function () {
        $.getJSON("/anime/add_to_plan_to_watch_list", {anime: $(".info").attr("data-anime-id")}, function (data) {
            if (!data["success"]) {
                var message = data["info"];
                if (message === "not authorized") {
                    message = "You are not authorized";
                }
                swal("Error!", message, "error");
            }
        });
    });
    $(".add-to-completed").click(function () {
        $.getJSON("/anime/add_to_completed_list", {anime: $(".info").attr("data-anime-id")}, function (data) {
            if (!data["success"]) {
                var message = data["info"];
                if (message === "not authorized") {
                    message = "You are not authorized";
                }
                swal("Error!", message, "error");
            }
        });
    });
    var randomizing = false;
    $(".randomize-button").click(function() {
        $(".select").removeClass("show");
        if(!randomizing) {
            randomizing = true;
            var genre = $("#genre").find(".title").text();
            var year = $("#year").find(".title").text();
            var rating = $("#rating").find(".title").text();
            $(".info").removeClass("show");
            setTimeout(function() {
                $.getJSON("/json/anirandom.json", {
                    "genre": (genre == "Genre") ? "undefined" : genre,
                    "year": (year == "Year") ? "undefined" : year,
                    "rating": (rating == "Rating") ? "undefined" : rating
                }, (function($info) {
                        return function(data) {
                            randomizing = false;
                            $(".error").removeClass("show");
                            $info.addClass("show");
                            $info.find(".image").css("background-image", "url(\"" + data["image"] + "\")");
                            $info.find(".title span").text(data["title"]);
                            $info.find(".title a").attr("href", data["myAnimeListLink"]);
                            $info.find(".synopsis").text(data["synopsis"]);
                            $info.find(".rating").text(data["rating"].toString());
                            $info.attr("data-anime-id", data["_id"]);
                            (function() {
//                                var $titleWidth = $(".title span").width();
//                                console.log($titleWidth);
//                                var $containerWidth = $(".title").width();
//                                console.log($containerWidth);
//                                if($titleWidth > $containerWidth) {
                                    var $title = $info.find(".title span").html();
                                    $info.find(".title span").attr('anime-title', $title);
//                                }
                            })();
                        };
                    })($(".info"))).fail(function() {
                        randomizing = false;
                        $(".error").addClass("show");
                    });
            }, 300);
        }
    });
});

$(function() {
    $(".randomize-button").click(function() {
        $(".randomize-button").addClass("randomized");
    });
});