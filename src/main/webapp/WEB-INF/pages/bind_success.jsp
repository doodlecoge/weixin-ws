<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/25
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String cp = request.getContextPath();
%>
<html>
<head>
    <title>绑定成功</title>
    <style type="text/css">
        footer {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
        }
    </style>
</head>
<body>
<p>
    <a class="button" href="<%=cp%>/schedule">预约会议</a>
</p>

<p>
    <a class="button" href="<%=cp%>/room">我的会议室</a>
</p>
</body>
</html>
