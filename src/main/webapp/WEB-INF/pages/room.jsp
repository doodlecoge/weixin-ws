<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/25
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String cp = request.getContextPath();
%>
<html>
<head>
    <title>我的会议室</title>
    <style type="text/css">
        footer {
            display: none;
        }

        .tip_img {
            width: 100%;
            z-index: 1000;
            position: absolute;
            left: 0;
            top: 0;
        }

        .overlay {
            position: fixed;
            background: #000;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;

        }

        .t70 {
            /* IE 8 */
            -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=70)";

            /* IE 5-7 */
            filter: alpha(opacity=70);

            /* Netscape */
            -moz-opacity: 0.7;

            /* Safari 1.x */
            -khtml-opacity: 0.7;

            /* Good browsers */
            opacity: 0.7;
        }

        .avatar {
            width: 60%;
            margin: 10px 20%;
        }
    </style>
</head>
<body>
<img src="${avatar}" class="avatar"/>

<div class="overlay t70" style="display: none;"></div>
<img class="tip_img" style="display: none;" src="<%=cp%>/img/weixin_tip.png">
<script type="text/javascript">
    var browser = function () {
        var ua = window.navigator.userAgent.toLowerCase();
        var _isWeiXin = function () {
            return ua.match(/MicroMessenger/i);
        };
        var _isAndroid = function () {
            return ua.match(/android/i);
        };
        var _isIOS = function () {
            return ua.match(/ipad/i) || ua.match(/iphone os/i);
        };
        return {
            isWeixin: _isWeiXin,
            isAndroid: _isAndroid,
            isIOS: _isIOS
        }
    }();

    var roomUrl = "${roomUrl}";
    if (browser.isWeixin()) {
        $(".overlay").show();
        $(".tip_img").show();
    } else {
        location.href = roomUrl;
    }
</script>
</body>
</html>
