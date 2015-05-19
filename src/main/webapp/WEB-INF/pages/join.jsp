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
<spring:message code="join.title" var="title"/>
<spring:message code="join.meeting.no" var="no"/>
<spring:message code="join.meeting.pwd" var="pwd"/>
<spring:message code="join.submit" var="submit"/>
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
<h1>${title}</h1>

<form action="<%=cp%>/save_join" method="post">
    <p>
        <label>${no}</label>
        <br/>
        <input type="text" name="meetingNo">
    </p>

    <p>
        <label>${pwd}</label>
        <br/>
        <input type="text" name="meetingPwd">
    </p>

    <p>
        <label>&nbsp;</label>
        <br/>
        <input type="submit" value="${submit}">
    </p>
</form>


<jsp:include page="bottom-bar.jsp"></jsp:include>
</body>
</html>
