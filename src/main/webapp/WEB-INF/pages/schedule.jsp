<%@ page import="java.util.Calendar" %>
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
<spring:message code="schedule.start_date" var="startDate"/>
<spring:message code="schedule.start_time" var="startTime"/>
<spring:message code="schedule.password" var="password"/>
<spring:message code="schedule.confirmp" var="confirmp"/>
<spring:message code="schedule.submit" var="submit"/>


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

<form action="<%=cp%>/save_schedule?t=<%=Calendar.getInstance().getTimeInMillis()%>" method="post">
    <p>
        <label>${subject}</label>
        <br/>
        <input type="text" name="subject">
    </p>

    <p>
        <label>${startDate}</label>
        <br/>
        <input type="date" name="startDate">
    </p>

    <p>
        <label>${startTime}</label>
        <br/>
        <input type="time" name="startTime">
    </p>

    <p>
        <label>${password}</label>
        <br/>
        <input type="password" name="password">
    </p>

    <p>
        <label>${confirmp}</label>
        <br/>
        <input type="password" name="confirmp">
    </p>

    <p id="error" style="display: none"></p>

    <p>
        <label>&nbsp;</label>
        <br/>
        <input type="submit" value="${submit}">
    </p>
</form>


<%--<script type="text/javascript">--%>
    <%--$('form').xhrForm({--%>
        <%--beforeSubmit: function () {--%>
            <%--$('#error').hide();--%>
        <%--},--%>
        <%--fail: function (data) {--%>
            <%--$('#error').html(data.message||'schedule failed').show();--%>
        <%--},--%>
        <%--success: function (data) {--%>
            <%--if (data.error)--%>
                <%--$('#error').html(data.message).show();--%>
            <%--else--%>
                <%--$('#error').html(data.message).show();--%>
        <%--}--%>
    <%--});--%>
<%--</script>--%>


<jsp:include page="bottom-bar.jsp"></jsp:include>
</body>
</html>
