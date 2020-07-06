var winHeight=0;

//获取窗口高度
if (window.innerHeight){
    winHeight = window.innerHeight;
}else if((document.body) && (document.body.clientHeight)){
    winHeight = document.body.clientHeight;
}
//通过深入Document内部对body进行检测dao，获取窗口高度
if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth){
    winWidth = document.documentElement.clientWidth;
    winHeight = document.documentElement.clientHeight;
}
$("#content_height").css({"height":parseInt(winHeight) - 81});