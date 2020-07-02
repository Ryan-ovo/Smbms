<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020/6/30
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>系统登录</title>
    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript"></script>
    <style>
        body{
            background: url("image/Winter.jpg") no-repeat center;
        }
        .err{
            color: red;
            text-align: center;
            margin-left: 50px;
        }

    </style>

    <script>
        window.onload = function () {
            document.getElementById("img").onclick = function () {
                this.src = "/smbms/checkCode?time="+new Date().getTime();
            }
        }
    </script>
</head>
<body>
<div class="container" style="width: 500px">
    <h1 style="margin: 100px 0 0 50px;text-align: center">超市订单管理系统</h1>
    <h3 style="margin: 100px 0px 50px 50px;text-align: center">用户登录</h3>
    <form class="form-horizontal" action="/smbms/loginServlet" method="post">
        <div class="form-group">
            <label for="user" class="col-sm-2 control-label">用户名:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="user" placeholder="Username" name="username">
            </div>
        </div>
        <div class="form-group">
            <label for="pwd" class="col-sm-2 control-label">密码:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="pwd" placeholder="Password" name="password">
            </div>
        </div>
        <div class="form-group">
            <label for="checkcode" class="col-sm-2 control-label">验证码:</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" id="checkcode" placeholder="Checkcode" name="checkcode">
            </div>
            <div class="col-sm-3">
                <img id="img" src="/smbms/checkCode">
            </div>
        </div>
        <div class="form-group" style="text-align: center;margin-right: 10px">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn">登录</button>
            </div>
        </div>
    </form>
</div>

    <div class="err"><%=request.getAttribute("cc_error") == null ? "" : request.getAttribute("cc_error")%></div>
    <div class="err"><%=request.getAttribute("login_error") == null ? "" : request.getAttribute("login_error")%></div>
</body>
</html>