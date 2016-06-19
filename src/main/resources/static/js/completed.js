$(function() {
    $("section").each(function() {
        var $section = $(this);
        var $drop_down = $section.find(".drop-down-more");
        $(document.body).click(function() {
            $drop_down.removeClass("show");
        });
        $drop_down.find(".more").click(function(e) {
            $drop_down.toggleClass("show");
            e.stopPropagation();
        });
        var $hidden_edit = $section.find(".hidden-edit");
        $drop_down.find(".edit").click(function() {
            $section.addClass("edit");
        });
        $drop_down.find(".remove").click(function() {
            $.getJSON("/anime/remove_from_completed", {anime: $(this).attr("data-anime-id")}, function (data) {
                if(data["success"]) {
                    $section.addClass("hidden");
                    setTimeout(function() {
                        $section.remove();
                    }, 300);
                }
            });
        });
        var $title_span = $section.find(".anime-title span");
        $title_span.attr("data-anime-title", $title_span.html());
        var $stars = $section.find(".stars");
        $stars.find("input").change(function() {
            console.log("asdasd")
            if(this.checked) {
                $stars.attr("data-checked", this.value);
            }
        });
        $hidden_edit.find("input[type=submit]").click(function() {
            var anime = $hidden_edit.find("input[name=anime]").val();
            var rating = $stars.attr("data-checked");
            var review = $hidden_edit.find("textarea").val();
            console.log({
                anime:  anime,
                rating: rating,
                review: review
            });
            $.getJSON("/anime/edit_completed_list", {
                anime:  anime,
                rating: rating,
                review: review
            }, function(data) {
                console.log(data);
                document.location.reload();
            });
            return false;
            setTimeout();
        });
    });
});

$(function() {
//    $("ul li").click(function() {
//
//        var tab = $(this).index();
//        var position = 273.2 * tab;
//
//        if($(this).hasClass('slider')) {
//            return;
//        }
//
//        $(".slider").css({
//            left: position + "px"
//        });
//
//        $('li').removeClass('active');
//        $(this).addClass('active');
//        $(this).find('li').addClass('active');
//    });

    if($) {

        $('#container > *').hide(0);
        $('#container #reviewed').show(0);

        $(".menuElement").click(function() {

            $('#container > *').hide(0);
            $('#unreviewed').css("visibility", "visible");
            var tabID = $(this).data("tab");
            $('#' + tabID).show(0);

        });
    }
});