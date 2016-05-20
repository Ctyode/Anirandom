$(function() {
    $(".drop-down-more").each(function() {
        var $drop_down = $(this);
        $drop_down.find(".more").click(function() {
            $drop_down.toggleClass("show");
        });
        $(".info").each(function() {
            var $info = $(this);
            var $title = $info.find(".anime-title span").html();
            $info.find(".anime-title span").attr('data-anime-title', $title);

            $drop_down.find(".edit").click(function() {
                $info.find(".hidden-edit").css('visibility', 'visible');
    //            $.getJSON("/anime/edit_completed_list", {anime: $("input").attr("data-anime-id")}, function (data) {
    //                if(data["status"] === "success") {
    //                    console.log("nya");
    ////                    $section.addClass("hidden");
    ////                    setTimeout(function() {
    ////                        $section.remove();
    ////                    }, 300);
    //                }
    //            });
            });
        });
    });
});