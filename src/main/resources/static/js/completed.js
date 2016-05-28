$(function() {
    $("section").each(function() {
        var $section = $(this);
        var $drop_down = $section.find(".drop-down-more");
        $drop_down.find(".more").click(function() {
            $drop_down.toggleClass("show");
        });
        var $hidden_edit = $section.find(".hidden-edit");
        console.log($hidden_edit);
        $drop_down.find(".edit").click(function() {
            $hidden_edit.css('visibility', 'visible');
        });
        var $title_span = $section.find(".anime-title span");
        $title_span.attr("data-anime-title", $title_span.html());
        $hidden_edit.find("input[type=submit]").click(function() {
            var anime = $hidden_edit.find("input[name=anime]").val();
            var rating = $hidden_edit.find("input[name=rating]").val();
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
            });
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

            $('#container > *').hide(0)

            var tabID = $(this).data("tab");
            $('#' + tabID).show(0);

        });
    }
});