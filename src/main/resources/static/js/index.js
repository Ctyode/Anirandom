$(function () {
    (function($login) {
        // $login.click(function() {
        //     if($(this).attr("section") !== undefined) {
        //         return false;
        //     }
        // });
        $login.find(".login-text").click(function() {
            if($login.attr("section") === undefined) {
                $login.attr("section", "sign-in");
            } else {
                $login.removeAttr("section");
            }
        });
        $login.find(".goto-button").click(function() {
            $login.attr("section", $(this).attr("target"));
        });
        // $("body").click(function() {
        //     $login.removeAttr("section");
        // });
    })($(".login"));
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
                $.getJSON("/anirandom.json", {
                    "genre": (genre == "Genre") ? "undefined" : genre,
                    "year": (year == "Year") ? "undefined" : year,
                    "rating": (rating == "Rating") ? "undefined" : rating
                }, (function($info) {
                        return function(data) {
                            randomizing = false;
                            $(".error").removeClass("show");
                            $info.addClass("show");
                            $info.find(".image").css("background-image", "url(\"" + data["image"] + "\")");
                            $info.find(".title").text(data["title"]);
                            $info.find(".synopsis").text(data["synopsis"]);
                            $info.find(".rating").text(data["rating"].toString());
                        };
                    })($(".info"))).fail(function() {
                        randomizing = false;
                        $(".error").addClass("show");
                    });
            }, 300);
        }
    });
});
