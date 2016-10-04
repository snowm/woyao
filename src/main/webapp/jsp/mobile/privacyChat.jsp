<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我要-聊天室</title>
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
</head>
<body class="user-sexy-man" ms-controller="mainController">
<div class="pop-sreen" ms-class-pop-sreen-show="sreenShow">
<!--<div class="pop-sreen" ms-class-pop-sreen-show="1">-->
    <img class="pop-sreen-img blur" --src="/resources/static/img/delate/photo1.jpg" ms-attr-src="sreenImg" alt="">
    <div class="pop-sreen-img-flt"></div>
    <div class="pop-sreen-bg">
        <div class="pop-sreen-block">
            <img class="pop-sreen-head" src="/resources/static/img/head.jpg" alt="">
            <p class="pop-sreen-name">隔壁老王 <span><img class="header-sexy" src="/resources/static/img/weman.png" alt=""></span></p>
        </div>
        <div class="pop-sreen-block">
            <img class="pop-sreen-head" src="/resources/static/img/head.jpg" alt="">
            <p class="pop-sreen-name">隔壁老王 <span><img class="header-sexy" src="/resources/static/img/weman.png" alt=""></span></p>
        </div>
        <div class="pop-sreen-msg">
            <span class="text-org">我要霸屏<span ms-text="sreenTime"></span>秒：</span><span ms-text="sreenMsg"></span>
        </div>
        <span class="sreen-timer">倒计时：<span ms-text="sreenShowSeconds"></span></span>
    </div>
</div>
<div class="pop-forOther" ms-class="pop-show:forHerShow">
    <div class="pop-forOther-close" ms-on-tap="hideForOther"></div>
    <div class="pop-forOther-ctn">
        <div class="pop-forOther-title">为 <span class="text-blue">隔壁老王</span> 送礼物</div>
        <div class="pop-gift-ctn" style="margin-top: 10px">
            <div class="gift-ctn">
                <ul class="gift-list">
                    <li>
                        <img class="gift-img" src="/resources/static/img/num.png" alt="">
                        <div class="gift-msg">
                            <p class="gift-name">空瓶子</p>
                            <p class="gift-transilate">霸屏15秒</p>
                        </div>
                        <div class="gift-cost">
                            ￥15
                        </div>
                        <div class="radio-ctn">
                            <div class="radio-back">
                                <span class="radio-checked"></span>
                            </div>
                        </div>
                    </li>
                    <div class="clearfix"></div>
                    <li>
                        <img src="/resources/static/img/mac.png" alt="">
                        <div class="gift-msg">
                            <p class="gift-name">麦霸</p>
                            <p class="gift-transilate">霸屏15秒</p>
                        </div>
                        <div class="gift-cost">
                            ￥15
                        </div>
                        <div class="radio-ctn">
                            <div class="radio-back">
                                <span class="radio-checked"></span>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </li>
                    <div class="clearfix"></div>
                    <li>
                        <img src="/resources/static/img/plane.png" alt="">
                        <div class="gift-msg">
                            <p class="gift-name">飞机</p>
                            <p class="gift-transilate">霸屏15秒</p>
                        </div>
                        <div class="gift-cost">
                            ￥15
                        </div>
                        <div class="radio-ctn">
                            <div class="radio-back">
                                <span class="radio-checked"></span>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </li>
                    <div class="clearfix"></div>
                    <div class="clearfix"></div>
                    <li>
                        <img src="/resources/static/img/mac.png" alt="">
                        <div class="gift-msg">
                            <p class="gift-name">麦霸</p>
                            <p class="gift-transilate">霸屏15秒</p>
                        </div>
                        <div class="gift-cost">
                            ￥15
                        </div>
                        <div class="radio-ctn">
                            <div class="radio-back">
                                <span class="radio-checked"></span>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </li>
                    <div class="clearfix"></div>
                    <li>
                        <img src="/resources/static/img/plane.png" alt="">
                        <div class="gift-msg">
                            <p class="gift-name">飞机</p>
                            <p class="gift-transilate">霸屏15秒</p>
                        </div>
                        <div class="gift-cost">
                            ￥15
                        </div>
                        <div class="radio-ctn">
                            <div class="radio-back">
                                <span class="radio-checked"></span>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </li>
                    <div class="clearfix"></div>
                </ul>
            </div>
        </div>
        <div class="tab-bp-ctn clearfix" style="margin-top: 20px">
            <select class="bp-text" ms-duplex="forWho" style="width: 95%;height: 30px">
                <option value="">请选择赠送对象</option>
                <option value="0">采蘑菇的小姑娘</option>
            </select>
        </div>
        <div class="price-ctn" style="text-align: left">
            <p>需微信支付</p>
            <p>￥0</p>
        </div>
        <div class="pop-bottom">
            <div class="bp-sub" ms-on-tap="hideForOther">确 认</div>
            <div class="bp-cancel" ms-on-tap="hideForOther">取 消</div>
        </div>
    </div>
</div>
<div class="pop-container" ms-class="pop-show:popshow">
    <div class="pop-forclose" ms-on-tap="closeAllPop"></div>
    <div class="pop-send-ctn" ms-class="pop-send-show:popSendCtnShow">
        <div class="pop-tab-nav">
            <div ms-class-pop-tab-active="!tabShowFlag" ms-on-tap="tabChange(1)">霸屏</div>
            <div ms-class-pop-tab-active="tabShowFlag" ms-on-tap="tabChange(2)">送礼物</div>
        </div>
        <div class="pop-tab-temp" ms-if="!tabShowFlag">
            <select class="tab-select" ms-duplex="msgType" style="margin: auto 6%">
                <option value="">请输入霸屏时间</option>
                <option value="0">免费消息</option>
                <option value="15">霸屏15秒 50块</option>
                <option value="30">霸屏30秒 80块</option>
            </select>
            <div class="tab-bp-ctn clearfix">
                <textarea class="bp-text" name="" id="" cols="" rows="" ms-duplex="msgText"></textarea>
                <div class="bp-img">
                    <div class="bp-img-ctn">
                        <input type="file" value='' ms-change="fileChange(this)" style="width: 60px;height: 60px;position: absolute;left: 0;top: 0;z-index: 9;opacity:0;">
                        <img src="/resources/static/img/photo.png" ms-attr-src="imgViewSrc" alt="" style="border-radius: 5px;">
                    </div>
                </div>
            </div>
            <div class="price-ctn">
                <p>需微信支付</p>
                <p>￥0</p>
            </div>
            <div class="pop-bottom">
                <div class="bp-sub" ms-on-tap="sendMsg">霸 屏</div>
                <div class="bp-cancel" ms-on-tap="hidePopSend">取 消</div>
            </div>
        </div>
        <div class="pop-tab-temp" ms-if="tabShowFlag">
            <div class="tab-select">
                请选择送礼对象
            </div>
            <div class="pop-gift-ctn">
                <div class="gift-ctn">
                    <ul class="gift-list">
                        <li>
                            <img class="gift-img" src="/resources/static/img/num.png" alt="">
                            <div class="gift-msg">
                                <p class="gift-name">空瓶子</p>
                                <p class="gift-transilate">霸屏15秒</p>
                            </div>
                            <div class="gift-cost">
                                ￥15
                            </div>
                            <div class="radio-ctn">
                                <div class="radio-back">
                                    <span class="radio-checked"></span>
                                </div>
                            </div>
                        </li>
                        <div class="clearfix"></div>
                        <li>
                            <img src="/resources/static/img/mac.png" alt="">
                            <div class="gift-msg">
                                <p class="gift-name">麦霸</p>
                                <p class="gift-transilate">霸屏15秒</p>
                            </div>
                            <div class="gift-cost">
                                ￥15
                            </div>
                            <div class="radio-ctn">
                                <div class="radio-back">
                                    <span class="radio-checked"></span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </li>
                        <div class="clearfix"></div>
                        <li>
                            <img src="/resources/static/img/plane.png" alt="">
                            <div class="gift-msg">
                                <p class="gift-name">飞机</p>
                                <p class="gift-transilate">霸屏15秒</p>
                            </div>
                            <div class="gift-cost">
                                ￥15
                            </div>
                            <div class="radio-ctn">
                                <div class="radio-back">
                                    <span class="radio-checked"></span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </li>
                        <div class="clearfix"></div>
                        <div class="clearfix"></div>
                        <li>
                            <img src="/resources/static/img/mac.png" alt="">
                            <div class="gift-msg">
                                <p class="gift-name">麦霸</p>
                                <p class="gift-transilate">霸屏15秒</p>
                            </div>
                            <div class="gift-cost">
                                ￥15
                            </div>
                            <div class="radio-ctn">
                                <div class="radio-back">
                                    <span class="radio-checked"></span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </li>
                        <div class="clearfix"></div>
                        <li>
                            <img src="/resources/static/img/plane.png" alt="">
                            <div class="gift-msg">
                                <p class="gift-name">飞机</p>
                                <p class="gift-transilate">霸屏15秒</p>
                            </div>
                            <div class="gift-cost">
                                ￥15
                            </div>
                            <div class="radio-ctn">
                                <div class="radio-back">
                                    <span class="radio-checked"></span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </li>
                        <div class="clearfix"></div>
                    </ul>
                </div>
            </div>
            <div class="price-ctn">
                <p>需微信支付</p>
                <p>￥0</p>
            </div>
            <div class="pop-bottom">
                <div class="bp-sub" ms-on-tap="sendMsg">确 认</div>
                <div class="bp-cancel" ms-on-tap="hidePopSend">取 消</div>
            </div>
        </div>
    </div>
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
        <div class="pop-wall-msg">
            <table>
                <tr>
                    <td><span class="pop-wall-name">奥特曼</span> <img class="pop-wall-sexy" src="/resources/static/img/weman.png" alt=""></td>
                    <td width="70"><div class="pop-wall-chat" ms-on-tap="forHer()">送 礼</div></td>
                    <td width="70"><div class="pop-wall-chat" ms-on-tap="privateChat">搭 讪</div></td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="contain">
    <div class="richer-list-btn" ms-on-tap="richerList">
        <img src="/resources/static/img/diamond.png" alt="">
        土豪榜
    </div>
    <div class="chat-list-btn" ms-on-tap="userList">
        <img src="/resources/static/img/chats.png" alt="">
        私聊
    </div>
    <div class="header-contain">
        <table class="header-ctn">
            <tr>
                <td width="102">
                    <span class="header-rich-title">今日土豪</span>
                </td>
                <td>
                    <img class="header-richer" src="/resources/static/img/head.jpg" alt="">
                    <span class="text-heightlight">隔壁老王</span> <img ms-if="false" class="header-sexy" src="/resources/static/img/man.png" alt=""><img class="header-sexy" src="/resources/static/img/weman.png" alt="">
                </td>
                <td width="80">
                    <span>霸屏<span class="text-dark"> 5 </span>次</span>
                </td>
            </tr>
        </table>
    </div>
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
                            <div class="send-btn" id="iwant" ms-on-tap="showPopSend">我 要</div>
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
<script type="text/javascript" src="/resources/static/js/index.js"></script>
</html>