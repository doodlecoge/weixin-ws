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
<spring:message code="bind.title" var="title"/>
<spring:message code="bind.wxid" var="id"/>
<spring:message code="bind.wbx.id" var="wbx_username"/>
<spring:message code="bind.wbx.pwd" var="wbx_password"/>
<spring:message code="bind.wbx.url" var="wbx_site_url"/>
<spring:message code="bind.submit" var="submit"/>
<html>
<head>
    <title>${title}</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta content="text/html" charset="utf-8">

</head>
<body>


<h1>${title}</h1>

<form action="<%=cp%>/user/bind?t=<%=Calendar.getInstance().getTimeInMillis()%>" method="post">
    <p>
        <label>${id}</label>
        <br/>
        <input type="text" name="wxId" value="${wxid}" readonly>
    </p>

    <p>
        <label>${wbx_username}</label>
        <br/>
        <input type="text" name="wbxUsername">
    </p>

    <p>
        <label>${wbx_password}</label>
        <br/>
        <input type="password" name="wbxPassword">
    </p>

    <p>
        <label>${wbx_site_url}</label>
        <br/>
        <input type="text" name="wbxSiteUrl">
    </p>

    <p>
        <label>&nbsp;</label>
        <br/>
        <input type="submit" value="${submit}">
    </p>
</form>
</body>
</html>
