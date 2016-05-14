$(function() {
    $(".drop-down-more").each(function() {
        var $drop_down = $(this);
        $drop_down.find(".more").click(function() {
            $drop_down.toggleClass("show");
        });
        $drop_down.find(".button-edit-review").click(function() {
            $.getJSON("/anime/edit_completed_list", {anime: $(this).attr("data-anime-id")}, function (data) {
                if(data["status"] === "success") {
                    console.log("nya");
//                    $section.addClass("hidden");
//                    setTimeout(function() {
//                        $section.remove();
//                    }, 300);
                }
            });
        });
        $(".info").each(function() {
            var $info = $(this);
            var $title = $info.find(".anime-title span").html();
            $info.find(".anime-title span").attr('data-anime-title', $title);
        });
    });

});