$(function() {
    $("section").each(function() {
        var $section = $(this);
        var $drop_down = $section.find(".drop-down-more");
        $drop_down.find(".more").click(function(e) {
            $drop_down.toggleClass("show");
            e.stopPropagation();
        });
        $(document.body).click(function() {
            $drop_down.removeClass("show");
        });
        $drop_down.find(".remove").click(function() {
            $.getJSON("/anime/remove_from_plan_to_watch", {anime: $(this).attr("data-anime-id")}, function (data) {
                if(data["status"] === "success") {
                    $section.addClass("hidden");
                    setTimeout(function() {
                        $section.remove();
                    }, 300);
                }
            });
        });
        $drop_down.find(".move").click(function() {
            $.getJSON("/anime/move_to_completed", {anime: $(this).attr("data-anime-id")}, function (data) {
                if(data["status"] === "success") {
                    console.log("blablabla")
                    $section.addClass("hidden");
                    setTimeout(function() {
                        $section.remove();
                    }, 300);
                }
            });
        });
    });
});