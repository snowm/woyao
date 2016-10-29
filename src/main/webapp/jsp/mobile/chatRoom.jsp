<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我要</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="apple-mobile-web-app-title" content="I want">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <link rel="stylesheet" href="/resources/static/css/main.css"/>
    <link rel="SHORTCUT ICON" href=""/>
    <!--<script src="/resources/js/require/require.js" data-main="/resources/main"></script>  -->
    <script src="/resources/js/exif.js" > </script>
    <script src="http://apps.bdimg.com/libs/require.js/2.1.9/require.min.js" data-main="/resources/main"></script>
</head>
<body ms-controller="rootController">
	<input id="appId" type="hidden" value="${appId}"/>
	<input id="timestamp" type="hidden" value="${timestamp}"/>
	<input id="nonceStr" type="hidden" value="${nonceStr}"/>
	<input id="signature" type="hidden" value="${signature}"/>
	<div ms-if="lock" style="width:100%;height:100%;position:absolute;left:0;top:0;background:white;text-align:center;padding-top:50%;font-size:12px;color:#999;z-index:9999999999999">
   		<img src='/resources/static/img/404.png' style="width:50%;margin:0 auto;">
   		<p style="line-height:30px;margin-top:20px">链接超时了~</p>
   		<p style="line-height:30px">请确保网络</p>
   		<p style="line-height:30px">重新进入聊天室</p>
   	</div>
   	<div class="loading" ms-on-tap="hiddenLoading" ms-visible='_loading' style="position:absolute;width:100%;height:100%;background:rgba(255, 255, 255, 1);left:0;top:0;color:red;z-index:9999999999">
   		<img src="/resources/static/img/heart.gif" ms-on-tap="hiddenLoading" style="position:absolute;margin:auto;width:80px;right:0;top:0;left:0;bottom:0">
   	</div>
    <div ms-include-src="content"></div>
</body>
</html>