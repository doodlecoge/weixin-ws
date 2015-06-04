<%@ page import="java.util.Calendar" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/7
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String cp = request.getContextPath();
%>
<html>
<head>
    <title>${title}</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta content="text/html" charset="utf-8">

    <style type="text/css">
        .avatar {
            width: 60%;
            margin: 10px 20%;
        }
    </style>
</head>
<body>
<spring:message code="prifile.title" var="title"/>
<spring:message code="prifile.save" var="save"/>
<spring:message code="bind.wxid" var="id"/>
<spring:message code="bind.wbx.id" var="wbx_username"/>
<spring:message code="bind.wbx.pwd" var="wbx_password"/>
<spring:message code="bind.wbx.url" var="wbx_site_url"/>

<h1>${title}</h1>

<img src="${avatar}" class="avatar"/>

<div class="content">
    <form action="<%=cp%>/user/bind?t=<%=Calendar.getInstance().getTimeInMillis()%>" method="post">
        <p>
            <label>${id}</label>
            <br/>
            <input type="text" name="wxId" value="${wxid}" readonly>
        </p>

        <p>
            <label>${wbx_username}</label>
            <br/>
            <input type="text" name="wbxUsername" value="${user.wbxUsername}">
        </p>

        <p>
            <label>${wbx_password}</label>
            <br/>
            <input type="text" name="wbxPassword">
        </p>

        <p>
            <label>${wbx_site_url}</label>
            <br/>
            <input type="text" name="wbxSiteUrl" value="${user.wbxSiteUrl}">
        </p>

        <p>
            <label>&nbsp;</label>
            <br/>
            <input type="submit" value="${save}">
        </p>
    </form>
</div>
</body>
</html>
