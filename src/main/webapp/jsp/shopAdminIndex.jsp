<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link href="/shopAdmin/resources/css/flat-ui.css" rel="stylesheet">
    <link href="/shopAdmin/resources/css/main.css" rel="stylesheet">
</head>
<script data-main="/shopAdmin/resources/js/main.js" src="/shopAdmin/resources/js/plugin/require.js"></script>
<body ms-controller="root">
	<c:set var="logout_url">
		<c:url value="/admin/logout" />
	</c:set>
<div class="m-header-ctn">
    <div class="col-xs-2">
    	<img src="/shopAdmin/resources/img/luokeW.png" style="position:relative;display:block;height:30px;margin:10px" alt="">
    </div>
    <div class="col-xs-10 text-right">
    	<img ms-attr-src="shopDetail.picUrl" alt="" style="width:25px;height:25px;position:relative;top:-0px;right:20px;border-radius:50%;">
    	<span ms-text="shopDetail.managerName">您好！</span><span>您好！</span>
    	<form action="${logout_url}" method="POST" style="display:inline;cursor: pointer;">
				<a style="color:white"> <span class="fui-power" style="font-size:20px;padding: 0 0 0 40px;position:relative;top:4px;"></span><input  style="text-align: left;background:rgba(0,0,0,0);border:0;outline:0;color:#94989d" type='submit' value="退出"></a>				                   		
		</form>
       
    </div>
</div>
<div class="m-nav-ctn">
    <div class="m-nav-logo">
    </div>
    <ul class="m-nav">
        <li>
        	<a style="width:100%;height:100%;display:block" href="#!/home" ms-class-avtive="nav=='home'" ms-click="navtab('home')">
	            <i class="fui-home"></i>
	            <p>首页</p>
            </a>
        </li>
        <li>
        	<a style="width:100%;height:100%;display:block" href="#!/order" ms-class-avtive="nav=='order'" ms-click="navtab('order')">
	            <i class="fui-search"></i>
	            <p>订单查询</p>
	        </a>
        </li>
        <li>
       		<a style="width:100%;height:100%;display:block" href="#!/chat" ms-class-avtive="nav=='chat'" ms-click="navtab('chat')">
	            <i class="fui-bubble"></i>
	            <p>消息查询</p>
	        </a>
        </li>
        <li>
        	<a style="width:100%;height:100%;display:block" href="#!/goods" ms-class-avtive="nav=='goods'" ms-click="navtab('goods')">
	            <i class="fui-calendar-solid"></i>
	            <p>商品管理</p>
	        </a>
        </li>
        <li>
       		<a style="width:100%;height:100%;display:block" href="#!/shop" ms-class-avtive="nav=='shop'" ms-click="navtab('shop')">
            <i class="fui-star-2"></i>
	            <p>商店管理</p>
	        </a>
        </li>
        <!-- <li>
            <i class="fa fa-fire"></i>
            <a href="#!/demo">demo</a>
        </li> -->
        <li>
        	<a class="btn btn-block btn-lg btn-info" style="color:white;width:96%" href="/shop/admin/dapin" target="view_window">
            	<i class="fui-play"></i>
	            <p style="margin:0">大屏入口</p>
	        </a>
        </li>
    </ul>
</div>

<div class="m-container">
    <div class="m-content" ms-include-src="content"></div> 
</div>
</body>
</html>
