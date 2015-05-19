<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/18
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%
    String cp = request.getContextPath();
%>
<spring:message code="error.title" var="title"/>
<html>
<head>
    <title>${title}</title>
    <style type="text/css">
        footer {
            display: none;
        }
    </style>
</head>
<body>
<br/>

<div>
    <div class="alert error">
        <i class="fa fa-times-circle icon"></i>
        <span class="txt">${message}</span>
    </div>

    <br/>

    <a href="javascript:history.go(-1)" class="button">
        <i class="fa fa-arrow-left"></i>
        返回
    </a>
</div>
</body>
</html>
