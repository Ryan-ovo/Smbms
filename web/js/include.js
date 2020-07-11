$(function () {
    $.get("header.html",function (data) {
        $("#header").html(data);
    });
    $.get("left-side-menu.html",function (data) {
        $("#left-side-menu").html(data);
    });
    $.get("top-bar.html",function (data) {
        $("#top-bar").html(data);
    });

})