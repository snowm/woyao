<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <script type="text/javascript" src="/resources/plugin/fastclick-master/lib/fastclick.js"></script>
    <!--<script type="text/javascript" src="/resources/node_modules/socket.io/lib/socket.web.js"></script>-->
    <link rel="stylesheet" href="/resources/static/css/bar-list.css"/>
    <link rel="stylesheet" href="/resources/plugin/swiper-master/dist/css/swiper.min.css"/>
    <link rel="SHORTCUT ICON" href=""/>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=mZm3GQOvw7AFyZIKrkeomWMbhMbpP2Cc"></script>
    <script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
</head>
<body ms-controller="barListController">
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
            <div class="block-ctn" ms-on-tap="toChatRooms(1)">
                <div class="block-ctn-m">
                    <div class="metro-ctn" style="background: url('/resources/static/img/delate/0813131.jpg') center;">
                        <p class="b-name">兰桂坊</p>
                        <span class="hots"><img src="/resources/static/img/hot.png" alt=""> 热度:60</span>
                        <span class="lct"><img src="/resources/static/img/dits.png" alt=""> 距离:50km</span>
                    </div>
                </div>
            </div>
            <div class="block-ctn">
                <div class="block-ctn-m">
                    <div class="metro-ctn" style="background: url('/resources/static/img/delate/1.jpg') center;">
                        <p class="b-name">兰桂坊</p>
                        <span class="hots"><img src="/resources/static/img/hot.png" alt=""> 热度:60</span>
                        <span class="lct"><img src="/resources/static/img/dits.png" alt=""> 距离:50km</span>
                    </div>
                </div>
            </div>
            <div class="block-ctn">
                <div class="block-ctn-m">
                    <div class="metro-ctn" style="background: url('/resources/static/img/delate/3.jpg') center;">
                        <p class="b-name">兰桂坊</p>
                        <span class="hots"><img src="/resources/static/img/hot.png" alt=""> 热度:60</span>
                        <span class="lct"><img src="/resources/static/img/dits.png" alt=""> 距离:50km</span>
                    </div>
                </div>
            </div>
            <div class="block-ctn">
                <div class="block-ctn-m">
                    <div class="metro-ctn" style="background: url('/resources/static/img/delate/2.jpg') center;">
                        <p class="b-name">兰桂坊</p>
                        <span class="hots"><img src="/resources/static/img/hot.png" alt=""> 热度:60</span>
                        <span class="lct"><img src="/resources/static/img/dits.png" alt=""> 距离:50km</span>
                    </div>
                </div>
            </div>
            <div class="block-ctn">
                <div class="block-ctn-m">
                    <div class="metro-ctn" style="background: url('/resources/static/img/delate/4.jpg') center;">
                        <p class="b-name">兰桂坊</p>
                        <span class="hots"><img src="/resources/static/img/hot.png" alt=""> 热度:60</span>
                        <span class="lct"><img src="/resources/static/img/dits.png" alt=""> 距离:50km</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="map-ctn" id="map" ms-if="listShow == 'map'"></div>
</body>
<script type="text/javascript" src="/resources/plugin/avalon-master/dist/avalon.mobile.min.js"></script>
<script type="text/javascript" src="/resources/plugin/jquery-1.9.1/jquery.js"></script>
<!--<script type="text/javascript" src="/resources/plugin/swiper-master/dist/js/swiper.jquery.min.js"></script>-->
<script type="text/javascript" src="/resources/static/js/main.js"></script>
<script type="text/javascript" src="/resources/static/js/bar-list.js"></script>
</html>