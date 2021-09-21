$(function () {
    $.get("header.html",function (data) {
        //这里直接获取该标签，设置其内容为对于的值
        $("#header").html(data);
    });
    $.get("footer.html",function (data) {
        //这里直接获取该标签，设置其内容为对于的值
        $("#footer").html(data);
    });
});