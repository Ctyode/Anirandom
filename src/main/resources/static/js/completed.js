$(function() {
    $(".drop-down-more").each(function() {
        var $drop_down = $(this);
        console.log($drop_down);
        $drop_down.find(".more").click(function() {
            console.log($drop_down);
            $drop_down.toggleClass("show");
        });
    });
});