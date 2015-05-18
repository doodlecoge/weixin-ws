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
<spring:message code="schedule.title" var="title"/>
<spring:message code="schedule.subject" var="subject"/>
<spring:message code="schedule.start_time" var="startTime"/>
<spring:message code="schedule.end_time" var="endTime"/>
<spring:message code="schedule.submit" var="submit"/>


<html>
<head>
    <title>Schedule</title>
    <style type="text/css">
        footer {
            display: none;
        }
    </style>
</head>
<body>


<h1>${title}</h1>

<form action="<%=cp%>/save_schedule" method="post">
    <p>
        <label>${subject}</label>
        <br/>
        <input type="text" name="subject">
    </p>

    <p>
        <label>${startTime}</label>
        <br/>
        <input type="text" name="startTime">
    </p>

    <%--<p>--%>
        <%--<label>${endTime}</label>--%>
        <%--<br/>--%>
        <%--<input type="text" name="endTime">--%>
    <%--</p>--%>

    <p>
        <label>&nbsp;</label>
        <br/>
        <input type="submit" value="${submit}">
    </p>
</form>


<jsp:include page="bottom-bar.jsp"></jsp:include>
</body>
</html>
