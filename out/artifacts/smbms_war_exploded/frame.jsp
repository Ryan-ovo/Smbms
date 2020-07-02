<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020/6/30
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
</head>
<body>
    <h1><%=request.getSession().getAttribute("user")+","%>欢迎您</h1>
</body>
</html>
