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
<spring:message code="list.key" var="kkk"/>

<html>
<head>
    <title>${title}</title>
    <style type="text/css">
        @media screen and (max-width: 600px) {
            .c {
                padding: 0;
            }
        }

        footer {
            display: none;
        }

        dl.meeting-list {
            outline: none;
            margin: 0;
        }

        dl.meeting-list dt {
            border-bottom: 1px solid silver;
            background: #eee;
            padding: .6em 1em;
        }

        dl.meeting-list dd {
            border: 1px dashed silver;
            border-top: 0;
            margin: 0 0 10px 0;
            padding: 10px 0 10px 10px;
            display: none;
            background: #f8f8f8;
        }

        table tr td:first-child {
            font-weight: bold;
        }

        .topic {
            font-size: 1.5em;
            font-weight: bold;
            display: block;
        }
    </style>

    <script type="text/javascript">
        $(function () {
            $('dl.meeting-list').on('click', function (e) {
                var el = $(e.target).closest('dt');
                var n = el.next();
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
    <c:forEach var="entry" items="${sessions}">
        <dt>
            <span>
                    ${entry.key.weekDay}
            </span>
            <span style="float: right;">
                    ${entry.key.dateString}
            </span>
        </dt>
        <dd>
            <c:forEach var="meeting" items="${entry.value}">
                <span class="topic">${meeting.topic}</span>
                <span>${meeting.duration}</span>
                <br/>
                <span>${meeting.host}</span>
                <br/>
                <span>${meeting.meetingNO}</span>
                <br/>
                <a href="<%=cp%>/join/${meeting.sessionKey}/${wxid}">${join}</a>
                <br/>
            </c:forEach>
        </dd>
    </c:forEach>
</dl>

<jsp:include page="bottom-bar.jsp"></jsp:include>
</body>
</html>
