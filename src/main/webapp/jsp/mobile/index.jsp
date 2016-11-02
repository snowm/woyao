<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>酒吧列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="apple-mobile-web-app-title" content="I want">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <script type="text/javascript" src="/resources/js/fastclick.js"></script>
    <link rel="stylesheet" href="/resources/static/css/bar-list.css"/>
    <link rel="SHORTCUT ICON" href=""/>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=mZm3GQOvw7AFyZIKrkeomWMbhMbpP2Cc&s=1"></script>
    <!-- <script type="text/javascript" src="https://developer.baidu.com/map/jsdemo/demo/convertor.js"></script> -->
</head>
<body ms-controller="barListController">
	<input id="appId" type="hidden" value="${appId}"/>
	<input id="timestamp" type="hidden" value="${timestamp}"/>
	<input id="nonceStr" type="hidden" value="${nonceStr}"/>
	<input id="signature" type="hidden" value="${signature}"/>
	<input id="rootContext" type="hidden" value="<c:url value='/'/>"/>
    <div class="top-tab">
        <table>
            <tr>
                <td ms-on-tap="tabChange('map')" ms-class-tab-active="listShow == 'map'">地图查找</td>
                <td ms-on-tap="tabChange('list')" ms-class-tab-active="listShow == 'list'">酒吧列表</td>
            </tr>
        </table>
    </div>
    <div class="list-ctn" ms-if="listShow == 'list'">
        <div>
            <div class="block-ctn" ms-on-tap="toChatRooms(p.id)" ms-repeat-p="barList">
                <div class="block-ctn-m">
                    <div class="metro-ctn">
                        <img ms-src="p.picURL" alt="" style="position:absolute;width:100%;height:100%;left:0;right:0">
                        <p class="b-name" ms-text="p.name">name</p>
                        <span class="hots"><img src="/resources/static/img/hot.png" alt=""> 热度:60</span>
                        <span class="lct"><img src="/resources/static/img/dits.png" alt=""> 距离:50km</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="map-ctn" style="" id="map" ms-show="listShow == 'map'"></div>
</body>
<script type="text/javascript" src="/resources/js/avalon/avalon.mobile.js"></script>
<script type="text/javascript" src="/resources/js/jquery-1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/wxsdk.js"></script>
<!-- <script type="text/javascript" src="/resources/static/js/main.js"></script> -->
<script type="text/javascript" src="/resources/modules/bar-list.js"></script>
</html>