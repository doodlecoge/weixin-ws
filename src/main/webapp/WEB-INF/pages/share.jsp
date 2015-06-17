<%@ page import="wang.huaichao.web.AppInitializer" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/22
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Share Something</title>
    <script type="text/javascript"
            src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>

<button class="button" id="btn">Hello World</button>
<script type="text/javascript">
    $(function () {

        wx.config({
            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: '${appId}', // 必填，企业号的唯一标识，此处填写企业号corpid
            timestamp: ${timestamp}, // 必填，生成签名的时间戳
            nonceStr: '${nonceStr}', // 必填，生成签名的随机串
            signature: '${signature}',// 必填，签名，见附录1
            jsApiList: [
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'hideMenuItems',
                'showMenuItems',
                'hideAllNonBaseMenuItem',
                'showAllNonBaseMenuItem',
                'translateVoice',
                'startRecord',
                'stopRecord',
                'onRecordEnd',
                'playVoice',
                'pauseVoice',
                'stopVoice',
                'uploadVoice',
                'downloadVoice',
                'chooseImage',
                'previewImage',
                'uploadImage',
                'downloadImage',
                'getNetworkType',
                'openLocation',
                'getLocation',
                'hideOptionMenu',
                'showOptionMenu',
                'closeWindow',
                'scanQRCode',
                'chooseWXPay',
                'openProductSpecificView',
                'addCard',
                'chooseCard',
                'openCard'
            ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });

        wx.ready(function () {

            // config信息验证后会执行ready方法，所有接口调用都必须在
            // config接口获得结果之后，config是一个客户端的异步操作，
            // 所以如果需要在页面加载时就调用相关接口，则须把相关接口
            // 放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，
            // 则可以直接调用，不需要放在ready函数中。



            wx.onMenuShareTimeline({
                title: 'xxxxx', // 分享标题
                link: 'http://im.sky-techcloud.com/weixin/share', // 分享链接
                imgUrl: 'http://img3.douban.com/view/movie_poster_cover/spst/public/p2166127561.jpg', // 分享图标
                success: function () {
                    // 用户确认分享后执行的回调函数
                    alert('success');
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                    alert('success');
                }
            });

            alert('已注册获取“发送给朋友”状态事件');


//                wx.onMenuShareAppMessage({
//                    title: '分享会议', // 分享标题
//                    desc: '分享会议到好友', // 分享描述
//                    link: 'http://www.a.b.cn', // 分享链接
//                    imgUrl: 'http://h.hiphotos.baidu.com/baike/g%3D0%3Bw%3D268/sign=0afb055e940a304e4222a5f1a6f595b0/472309f79052982285ea4899d4ca7bcb0b46d48b.jpg', // 分享图标
//                    type: 'link', // 分享类型,music、video或link，不填默认为link
//                    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
//                    trigger: function (res) {
//                        alert('用户点击发送给朋友');
//                    },
//                    success: function () {
//                        // 用户确认分享后执行的回调函数
//                        alert('success');
//                    },
//                    cancel: function () {
//                        // 用户取消分享后执行的回调函数
//                        alert('cancel');
//                    }
//                });
        });

        wx.error(function (res) {
            // config信息验证失败会执行error函数，如签名过期导致验证失败，
            // 具体错误信息可以打开config的debug模式查看，也可以在返回的
            // res参数中查看，对于SPA可以在这里更新签名。
            alert('error');
            alert(JSON.stringify(res));

        });


        $('#btn').click(function() {
            wx.chooseImage({
                success: function (res) {
                    var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    alert(localIds);
                }
            });

        });

    });
</script>
</body>
</html>
