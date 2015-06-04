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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String cp = request.getContextPath();
%>
<spring:message code="list.title" var="title"/>
<spring:message code="meeting.start_time" var="startTime"/>
<spring:message code="meeting.host" var="host"/>
<spring:message code="meeting.status" var="status"/>
<spring:message code="meeting.duration" var="duration"/>
<spring:message code="join.title" var="join"/>

<html>
<head>
    <title>${title}</title>
    <style type="text/css">
        footer {
            display: none;
        }

        dl.meeting-list {
            outline: none;
        }

        dl.meeting-list dt {
            border-bottom: 1px solid silver;
            background: #eee;
            padding: 1em 0 1em 10px;
        }

        dl.meeting-list dd {
            border: 1px dotted silver;
            border-top: 0;
            margin: 0 0 10px 0;
            padding: 10px 0 10px 10px;
            display: none;
            background: #f8f8f8;
        }

        table tr td:first-child {
            font-weight: bold;
        }
    </style>

    <script type="text/javascript">
        $(function () {
            $('dl.meeting-list').on('click', function (e) {
                if (e.target.nodeName.toLowerCase() != 'dt') return;
                var n = $(e.target).next();
                if (n.is(':visible')) {
                    n.slideUp();
                } else {
                    n.slideDown();
                }
            });
        });
    </script>
</head>
<body>

<dl class="meeting-list">
    <c:forEach var="session" items="${sessions}">
        <dt>
                ${session.topic}
        </dt>
        <dd>
            <table>
                <tr>
                    <td>${status} </td>
                    <td>${session.status}</td>
                </tr>
                <tr>
                    <td>${host} </td>
                    <td>${session.hostWebExId}</td>
                </tr>
                <tr>
                    <td>${startTime} </td>
                    <td>
                        <fmt:formatDate value="${session.startTime}"
                                        pattern="yyyy-MM-dd HH:mm"/>
                    </td>
                </tr>
                <tr>
                    <td>${duration} </td>
                    <td>${session.durationInMinutes}</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <a href="<%=cp%>/join/${session.sessionKey}/${wxid}">${join}</a>
                    </td>
                </tr>
            </table>
        </dd>
    </c:forEach>
</dl>

<jsp:include page="bottom-bar.jsp"></jsp:include>
</body>
</html>
