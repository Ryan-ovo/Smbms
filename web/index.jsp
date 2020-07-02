<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020/6/29
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en" class="no-js">

<head>

  <meta charset="utf-8">
  <title>Fullscreen Login</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- CSS -->
  <link rel="stylesheet" href="css/reset.css">
  <link rel="stylesheet" href="css/supersized.css">
  <link rel="stylesheet" href="css/style.css">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

</head>

<body oncontextmenu="return false">

<div class="page-container">
  <h1>Login</h1>
  <form id="loginForm" action="" method="post">
    <div>
      <input type="text" name="username" class="username" placeholder="Username" autocomplete="off"/>
    </div>
    <div>
      <input type="password" name="password" class="password" placeholder="Password" oncontextmenu="return false" onpaste="return false" />
    </div>
    <div>
      <input type="text" name="checkcode" class="checkcode" placeholder="Checkcode" oncontextmenu="return false" onpaste="return false"/>
    </div>
    <div style="margin-top: 20px">
      <img id="img" src="/smbms/checkCode">
    </div>
    <button id="submit" type="button">Sign in</button>
  </form>
  <div class="connect">
    <p>Welcome to Supermarket Bill Management System.</p>
    <p style="margin-top:20px;">欢迎来到超市订单管理系统。</p>
  </div>
</div>
<div class="alert" style="display:none">
  <h2>消息</h2>
  <div class="alert_con">
    <p id="ts"></p>
    <p style="line-height:70px"><a id="relogin" class="btn">确定</a></p>
  </div>
</div>

<!-- Javascript -->
<script src="http://apps.bdimg.com/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script>
<script src="js/supersized.3.2.7.min.js"></script>
<script src="js/supersized-init.js"></script>
<script>
  $(".btn").click(function(){
    is_hide();
  })
  var u = $("input[name=username]");
  var p = $("input[name=password]");
  var c = $("input[name=checkcode]");
  $("#submit").click(function () {
    if (u.val() == '' || p.val() == '') {
      $("#ts").html("用户名或密码不能为空~");
      is_show();
      return false;
    }
    if (c.val() == '') {
      $("#ts").html("验证码不能为空");
      is_show();
      return false;
    }
    $.post("loginServlet", $("#loginForm").serialize(), function (data) {
      //处理服务器响应的数据
      if (data.flag) {
        //登录成功
        location.href = "frame.jsp";
      } else {
        $("#ts").html(data.errorMsg);
        is_show();
        return false;
      }
    })
  });

  $("#relogin").click(function () {
      location.href="index.jsp";
  })

  window.onload = function() {
    $(".connect p").eq(0).animate({"left":"0%"}, 600);
    $(".connect p").eq(1).animate({"left":"0%"}, 400);
    freshCheckCode();
  }
  function is_hide(){ $(".alert").animate({"top":"-40%"}, 300) }
  function is_show(){ $(".alert").show().animate({"top":"45%"}, 300) }
  function freshCheckCode() {
    $("#img").click(function () {
      this.src = "/smbms/checkCode?time="+new Date().getTime();
    })
  }
</script>
</body>

</html>


