$(function () {
    var update_marquee = function(hold_time, speed) {
        var title_width = $(".info .title").width();
        var span_width = $(".info .title span").width();
        var slide = span_width - title_width;
        
        var $wrap = $(".title .wrap");
        var transition_duration = slide / speed;
        $wrap.css("transition", "all " + transition_duration + "s linear 0s");
        var marquee = function() {
            $wrap.css("left", -slide + "px");
            setTimeout(function() {
              $wrap.css("left", "0px");
            }, transition_duration * 1000 + hold_time);
            setTimeout(marquee, transition_duration * 1000 * 2 + hold_time * 2);
        };
        setTimeout(marquee, hold_time);
    };

    (function($auth) {
        $auth.find(".auth-button").click(function() {
            if($auth.attr("section") === undefined) {
                $auth.attr("section", $(this).attr("data-target"));
            } else {
                $auth.removeAttr("section");
            }
        });
        $auth.find(".goto-button").click(function() {
            $auth.attr("section", $(this).attr("data-target"));
        });
    })($(".auth"));
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
    $(".love-button").click(function () {
        $(".love").toggleClass("show");
    });
    $(".add-to-plan-to-watch").click(function () {
        $.getJSON("/anime/add_to_plan_to_watch_list", {anime: $(".info").attr("data-anime-id")}, function (data) {
            console.log(data);
        });
    });
    $(".add-to-completed").click(function () {
        $.getJSON("/anime/add_to_completed_list", {anime: $(".info").attr("data-anime-id")}, function (data) {
            console.log(data);
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
                            $info.find(".title .wrap span").text(data["title"]);
                            $info.find(".synopsis").text(data["synopsis"]);
                            $info.find(".rating").text(data["rating"].toString());
                            $info.attr("data-anime-id", data["_id"]);
                            update_marquee(2000, 50);
                        };
                    })($(".info"))).fail(function() {
                        randomizing = false;
                        $(".error").addClass("show");
                    });
            }, 300);
        }
    });
});
