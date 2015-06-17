<%@ page import="java.util.Calendar" %>
<%--
  Created by IntelliJ IDEA.
  User: hch
  Date: 2014/5/28
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
    String cp = request.getContextPath();
    long ts = Calendar.getInstance().getTimeInMillis();
%>
<spring:message code="ts" var="ts"/>
<spring:message code="site.title" var="siteTitle"/>
<!doctype html>
<html>
<head>
    <title><decorator:title/> - ${siteTitle}</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">

    <%--<meta http-equiv="Cache-Control"--%>
          <%--content="no-cache,no-store,must-revalidate"/>--%>
    <%--<meta http-equiv="Pragma" content="no-cache"/>--%>
    <%--<meta http-equiv="Expires" content="0"/>--%>

    <link rel="stylesheet" charset="utf-8"
          href="<%=cp%>/css/site.css?t=<%=ts%>">
    <link rel="stylesheet" charset="utf-8"
          href="<%=cp%>/css/site-rsp.css?t=<%=ts%>">
    <link rel="stylesheet" charset="utf-8"
          href="<%=cp%>/css/font-awesome.min.css?t=<%=ts%>">
    <script type="text/javascript"
            src="<%=cp%>/js/jquery-1.11.3.min.js?t=<%=ts%>"></script>
    <%--<script type="text/javascript"--%>
            <%--src="<%=cp%>/js/jquery-ui-core.js?t=<%=ts%>"></script>--%>
    <%--<script type="text/javascript"--%>
            <%--src="<%=cp%>/js/xhr-form.js?t=<%=ts%>"></script>--%>
    <decorator:head/>
</head>
<body>
<header>
    <div class="c">
        <img src="<%=cp%>/img/ketian-logo.png" class="logo"/>
        <br/><%=ts%>
    </div>
</header>
<div id="content">
    <div class="c">
        <decorator:body/>
    </div>
</div>
<footer>
    <div class="c">
        版权所有&copy;广州科天智慧云 2015
    </div>
</footer>
</body>
</html>
