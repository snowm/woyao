<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="/shopAdmin/resources/img/logo0.png" type="image/png">
    <title>我要酒吧后台管理系统</title>
    <link href="/shopAdmin/resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/shopAdmin/resources/css/flat-ui.min.css" rel="stylesheet">
    <link href="/shopAdmin/resources/css/main.css" rel="stylesheet">
</head>
<script data-main="/shopAdmin/resources/js/main.js" src="/shopAdmin/resources/js/plugin/require.js"></script>
<body ms-controller="root">
<div class="m-header-ctn">

</div>
<div class="m-nav-ctn">
    <div class="m-nav-logo">
        <img src="/shopAdmin/resources/img/logo0.png" alt="">
    </div>
    <ul class="m-nav">
        <li>
            <i class="fui-home"></i>
            <a href="#!setting">首页</a>
        </li>
        <li>
            <i class="fui-search"></i>
            <a href="#!/order">订单查询</a>
        </li>
        <li>
            <i class="fui-bubble"></i>
            <a href="#!/chat">消息查询</a>
        </li>
        <li>
            <i class="fa fa-fire"></i>
            <a href="#!/demo">demo</a>
        </li>
    </ul>
</div>

<div class="m-container">
    <div class="m-content" ms-include-src="content">

    </div>
</div>
</body>
</html>