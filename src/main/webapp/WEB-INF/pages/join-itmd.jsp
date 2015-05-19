<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/19
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
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

        var joinUrl = "${joinUrl}";
        if (browser.isWeixin()) {
            $('#tip').show();
        } else {
            location.href = joinUrl;
        }
    </script>
</head>
<body>
<div id="tip">Open In Browser</div>
</body>
</html>
