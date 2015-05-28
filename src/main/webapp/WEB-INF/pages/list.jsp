<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/18
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String cp = request.getContextPath();
%>
<spring:message code="list.title" var="title"/>
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

<ul>
    <c:forEach var="session" items="${sessions}">
        <li>
            <a href="<%=cp%>/list/${session.sessionKey}">
                    ${session.topic}
            </a>,
            ${session.status},
            ${session.hostWebExId},
            ${session.startTime},
            ${session.durationInMinutes}

        </li>
    </c:forEach>
</ul>
<jsp:include page="bottom-bar.jsp"></jsp:include>
</body>
</html>
