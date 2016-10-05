<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
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
    <script type="text/javascript" src="../plugin/fastclick-master/lib/fastclick.js"></script>
    <link rel="stylesheet" href="../static/css/screen.css"/>
    <link rel="SHORTCUT ICON" href=""/>
</head>
<body ms-controller="butTom">
    <div class="head"><img src="../static/img/本月土豪_02.gif" alt=""></div>
    <div class="rank">
        <p class="p" ms-on-tap="dayRank" ms-class="bottom:showBottom">今日土豪</p>
        <p class="p" ms-on-tap="weekRank" ms-class="bottom:showBottoms">本周土豪</p>
        <p class="p" ms-on-tap="monthRank" ms-class="bottom:showBottomss">本月土豪</p>
         <!--今日土豪-->
        <div ms-if="everydayRanKs">
            <div class="ranks">
                <div class="rank-list">
                    <div class="fist">
                        <img class="fist-img" src="../static/img/本月土豪_12.png" alt="">
                        <!--<p class="number-one">1</p>-->
                        <img class="number-one" src="../static/img/1.png" alt="">
                        <div class="rank-name">
                            <div><span>给我你的心</span><img class="rank-name-img" src="../static/img/本月土豪_05.gif" alt="">
                            </div>
                            <div class="rank-number">霸屏5次</div>
                        </div>
                        <div class="rank-right">
                            <img src="../static/img/本月土豪_07.gif" alt="">
                            <span>距离8千米</span>
                            <div class="msg" ms-on-tap="shsa">发消息</div>
                        </div>
                    </div>
                </div>
                <div class="rank-list">
                    <div class="fist">
                        <img class="fist-img" src="../static/img/本月土豪_12.png" alt="">
                        <!--<p class="number-one">2</p>-->
                        <img class="number-one" src="../static/img/2.png" alt="">
                        <div class="rank-name">
                            <div><span>给我你的心</span><img class="rank-name-img" src="../static/img/本月土豪_05.gif" alt="">
                            </div>
                            <div class="rank-number">霸屏5次</div>
                        </div>
                        <div class="rank-right">
                            <img src="../static/img/本月土豪_07.gif" alt="">
                            <span>距离8千米</span>
                            <div class="msg" ms-on-tap="shsa">发消息</div>
                        </div>
                    </div>
                </div>
                <div class="rank-list">
                <div class="fist">
                    <img class="fist-img" src="../static/img/本月土豪_12.png" alt="">
                    <!--<p class="number-one">3</p>-->
                    <img class="number-one" src="../static/img/3.png" alt="">
                    <div class="rank-name">
                        <div><span>给我你的心</span><img class="rank-name-img" src="../static/img/本月土豪_05.gif" alt="">
                        </div>
                        <div class="rank-number">霸屏3次</div>
                    </div>
                    <div class="rank-right">
                        <img src="../static/img/本月土豪_07.gif" alt="">
                        <span>距离3千米</span>
                        <div class="msg" ms-on-tap="shsa">发消息</div>
                    </div>
                </div>
            </div>
            </div>
        </div>
        <!--本周土豪-->
        <div class="ranks" ms-if="WeekRank">
            <div class="rank-list">
                <div class="fist">
                    <img class="fist-img" src="../static/img/本月土豪_12.png" alt="">
                    <img class="number-one" src="../static/img/1.png" alt="">
                    <!--<p class="number-one">3</p>-->
                    <div class="rank-name">
                        <div><span>给我你的心</span><img class="rank-name-img" src="../static/img/本月土豪_05.gif" alt="">
                        </div>
                        <div class="rank-number">霸屏3次</div>
                    </div>
                    <div class="rank-right">
                        <img src="../static/img/本月土豪_07.gif" alt="">
                        <span>距离3千米</span>
                        <div class="msg" ms-on-tap="shsa">发消息</div>
                    </div>
                </div>
            </div>
        </div>
        <!--本月土豪-->
        <div class="ranks" ms-if="MonthRank">
            <div class="rank-list">
                <div class="fist">
                    <img class="fist-img" src="../static/img/本月土豪_12.png" alt="">
                    <img class="number-one" src="../static/img/1.png" alt="">
                    <!--<p class="number-one">3</p>-->
                    <div class="rank-name">
                        <div><span>给我你的心</span><img class="rank-name-img" src="../static/img/本月土豪_05.gif" alt="">
                        </div>
                        <div class="rank-number">霸屏3次</div>
                    </div>
                    <div class="rank-right">
                        <img src="../static/img/本月土豪_07.gif" alt="">
                        <span>距离3千米</span>
                        <div class="msg" ms-on-tap="shsa">发消息</div>
                    </div>
                </div>
            </div>
            <div class="rank-list">
                <div class="fist">
                    <img class="fist-img" src="../static/img/本月土豪_12.png" alt="">
                    <img class="number-one" src="../static/img/2.png" alt="">
                    <!--<p class="number-one">3</p>-->
                    <div class="rank-name">
                        <div><span>给我你的心</span><img class="rank-name-img" src="../static/img/本月土豪_05.gif" alt="">
                        </div>
                        <div class="rank-number">霸屏3次</div>
                    </div>
                    <div class="rank-right">
                        <img src="../static/img/本月土豪_07.gif" alt="">
                        <span>距离3千米</span>
                        <div class="msg" ms-on-tap="shsa">发消息</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="../plugin/avalon-master/dist/avalon.mobile.min.js"></script>
<script src="../plugin/jquery-1.9.1/jquery.min.js"></script>
<script src="../static/js/screen.js"></script>
</html>