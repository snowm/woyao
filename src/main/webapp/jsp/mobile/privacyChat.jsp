<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>隔壁老王</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="apple-mobile-web-app-title" content="I want">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <script type="text/javascript" src="/resources/plugin/fastclick-master/lib/fastclick.js"></script>
    <link rel="stylesheet" href="/resources/static/css/main.css"/>
    <link rel="stylesheet" href="/resources/plugin/swiper-master/dist/css/swiper.min.css"/>
    <link rel="SHORTCUT ICON" href=""/>
    <style>
    .msg-block:nth-of-type(1) {
 	   margin-top: 55px;
	}
    </style>
</head>
<body class="user-sexy-man" ms-controller="pChatController">

<div class="pop-container" ms-class="pop-show:popshow">
    <div class="pop-forclose" ms-on-tap="closeAllPop"></div>
    <div class="pop-photoWall" ms-class="pop-phone-show:isShowPhoto">
        <div class="swiper-container pop-photoWall-ctn" ms-click="showPhotoWall">
            <div class="swiper-wrapper">
                <div class="swiper-slide pop-phones"><img class="swiper-lazy" src="/resources/static/img/delate/photo1.jpg" alt=""><div class="swiper-lazy-preloader"></div></div>
                <div class="swiper-slide pop-phones"><img class="swiper-lazy" src="/resources/static/img/diamond.png" alt=""><div class="swiper-lazy-preloader"></div></div>
                <div class="swiper-slide pop-phones"><img class="swiper-lazy" src="/resources/static/img/delate/photo1.jpg" alt=""><div class="swiper-lazy-preloader"></div></div>
                <div class="swiper-slide pop-phones"><img class="swiper-lazy" src="/resources/static/img/delate/photo1.jpg" alt=""><div class="swiper-lazy-preloader"></div></div>
                <div class="swiper-slide pop-phones"><img class="swiper-lazy" src="/resources/static/img/delate/photo1.jpg" alt=""><div class="swiper-lazy-preloader"></div></div>
            </div>
        </div>
        <div class="pop-wall-text">
            <p>1/3</p>
            <p>点击屏幕返回</p>
        </div>
    </div>
</div>
<div class="contain">
 <!--    <div class="chat-list-btn" ms-on-tap="userList" style='top:60px'>
        <img src="/resources/static/img/chats.png" alt="">
        私聊
    </div> -->
    <div class="msg-block-contain">
        <div class="msg-block-container">
            <div class="msg-block clearfix" ms-repeat-item="msgList" ms-class="msg-block-self:item.isMe == 'true'">
                <div class="msg-header">
                    <img src="/resources/static/img/head.jpg" alt=""  ms-on-tap="showPhotoWall">
                </div>
                <div class="msg-ctn clearfix">
                    <p class="msg-titles"><span class="msg-name" ms-text="item.name">name</span></p>
                    <div class="msg-contant" ms-class-msg-blue="item.sexy == 'man'" ms-class-msg-purple="item.sexy == 'weman'">
                        <img class="msg-arr" src="/resources/static/img/arblue.png" ms-if="item.sexy == 'man'" alt="">
                        <img class="msg-arr" src="/resources/static/img/arred.png" ms-if="item.sexy == 'weman'" alt="">

                        <!--<div class="msg-smgs"><span class="text-org">为 sreen 霸屏30秒：</span><span ms-text="item.msg">xxx</span></div>-->
                        <div class="msg-smgs"><span ms-html="item.msg"></span></div>
                        <div class="msg-img" ms-if="item.imgUrl">
                            <img --src="/resources/static/img/delate/photo1.jpg" ms-attr-src="item.imgUrl" alt="">
                        </div>
                        <!--<p class="msg-forhe" ms-on-tap="forHer()">给<span ms-if="item.sexy == 'man'">他</span><span ms-if="item.sexy == 'weman'">她</span>送礼物</p>-->
                        <p class="msg-forhe" ms-on-tap="forHer()" ms-if="item.imgUrl">送礼物</p>
                        <img class="msg-icon-right" src="/resources/static/img/cgrt.png" ms-if="item.sexy == 'man'" alt="">
                        <img class="msg-icon-right" src="/resources/static/img/lips.png" ms-if="item.sexy == 'weman'" alt="">
                    </div>
                </div>
                <p class="msg-time"><span ms-if=""></span>{{ item.time | date("dd:HH")}}</p>
            </div>
        </div>

    </div>
    <div class="module-block"  ms-class-show-emoji="emojiShow" ms-class-show-plugin="pluginShow">
        <div class="entry-contain">
            <table width="100%">
                <tr>
                    <td class="entry-more" ms-on-tap="togglePlugin">
                        <div class="entry-more-btn">
                        </div>
                    </td>
                    <td class="entry-input-ctn">
                        <input id="sendIpt" class="entry-input" type="text"  ms-duplex="msgText">
                    </td>
                    <td class="entry-send-ctn">
                        <div class="emoji-btn" ms-on-tap="toggleEmoji">
                        </div>
                        <div class="send-btn-ctn">
                            <div class="send-btn" id="iwant" ms-on-tap="sendMsg">我 要</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <table class="bottom-plug" ms-if="pluginShow">
            <tr>
                <td class="plug-btn">
                    <div class="plug-photo">
                        <input type="file" value='' ms-change="fileChange(this)" style="width: 60px;height: 60px;position: absolute;left: 0;top: 0;z-index: 9;opacity:0;">
                        <img src="/resources/static/img/photo.png" ms-attr-src="imgViewSrc" alt="" style="border-radius: 5px;">
                    </div>
                </td>
                <td> </td>
                <td> </td>
                <td> </td>
            </tr>
        </table>
        <div class="bottom-emoji" ms-if="emojiShow">
            <div class="emoji-ctn">
                <div class="faceCtn"></div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="/resources/plugin/avalon-master/dist/avalon.mobile.min.js"></script>
<script type="text/javascript" src="/resources/plugin/jquery-1.9.1/jquery.js"></script>
<script type="text/javascript" src="/resources/plugin/swiper-master/dist/js/swiper.jquery.min.js"></script>
<script type="text/javascript" src="/resources/plugin/qqface/jquery.qqFace.js"></script>
<script type="text/javascript" src="/resources/static/js/main.js"></script>
<script type="text/javascript" src="/resources/static/js/chat.js"></script>
</html>