<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/7
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <meta name="viewport"
        content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <meta charset="utf-8">
</head>
<body>
<span>
  <%= request.getSession().getAttribute("userid") %>
</span>
</body>
</html>
