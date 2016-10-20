<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>大屏幕</title>
</head>
<link rel="stylesheet" href="/show/resources/show.css">
<script src="/show/resources/jquery-1.9.1/jquery.min.js"></script>
<body>
<div class="wall-all-ctn">
    <div class="wall-all">
        <div class="wall-all-left">
            <div>
                <img class="wall-all-left-img" src="/show/resources/img/man.png" alt="">
                <p><span class="wall-name-text"></span><img src="/show/resources/img/woman.png" class="wall-sexy" alt=""></p>
                <p class="wall-p"><span class="wall-text">我要霸屏<span class="wall-times"></span>秒</span> <span class="wall-texts"></span></p>
            </div>
        </div>
        <div class="wall-all-right">
            <img class="wall-all-right-img" src="/show/resources/img/1_07.png" alt="">
            <!--<div class="across-one"></div>-->
            <!--<div class="across-two"></div>-->
            <!--<div class="stand-one"></div>-->
            <!--<div class="stand-two"></div>-->
        </div>
    </div>
</div>
<div class="l-code">
    <img class="msg-img" src="/show/resources/img/code.png" alt="">
</div>
<div class="m-contain">
    <div class="m-top">
        音乐小房子酒吧
    </div>
    <div class="m-msg-ctn">
        <div class="msg-block m0">
            <div class="msg-h">
                <img class="msg-hd" src="/show/resources/img/head.jpg" alt="">
            </div>
            <div class='msg-c'>
                <div class="msg-name"><span class="msg-name-text">隔壁老王</span> <img src="/show/resources/img/woman.png" alt=""></div>
                <div class="msg-text">
                    <div class="msg-img-c" style="display: none">
                        <img class="msg-img" src="/show/resources/img/head.jpg" alt="">
                    </div>
                    <div class="msg-text-c">
                        我要霸屏！
                    </div>
                </div>
            </div>
        </div>
        <div class="msg-block m1">
            <div class="msg-h">
                <img class="msg-hd" src="/show/resources/img/head.jpg" alt="">
            </div>
            <div class='msg-c'>
                <div class="msg-name"><span class="msg-name-text">隔壁老王</span>  <img src="/show/resources/img/woman.png" alt=""></div>
                <div class="msg-text">
                    <div class="msg-img-c" style="display: none">
                        <img class="msg-img" src="/show/resources/img/head.jpg" alt="">
                    </div>
                    <div class="msg-text-c">
                        我要霸屏！
                    </div>
                </div>
            </div>
        </div>
        <div class="msg-block m2">
            <div class="msg-h">
                <img class="msg-hd" src="/show/resources/img/head.jpg" alt="">
            </div>
            <div class='msg-c'>
                <div class="msg-name"><span class="msg-name-text">隔壁老王</span> <img src="/show/resources/img/woman.png" alt=""></div>
                <div class="msg-text">
                    <div class="msg-img-c" style="display: none">
                        <img class="msg-img" src="/show/resources/img/head.jpg" alt="">
                    </div>
                    <div class="msg-text-c">
                        我要霸屏！
                    </div>
                </div>
            </div>
        </div>
        <div class="msg-block m3">
            <div class="msg-h">
                <img class="msg-hd" src="/show/resources/img/head.jpg" alt="">
            </div>
            <div class='msg-c'>
                <div class="msg-name"><span class="msg-name-text">隔壁老王</span>  <img src="/show/resources/img/woman.png" alt=""></div>
                <div class="msg-text">
                    <div class="msg-img-c" style="display: none">
                        <img class="msg-img" src="/show/resources/img/head.jpg" alt="">
                    </div>
                    <div class="msg-text-c">
                        我要霸屏！
                    </div>
                </div>
            </div>
        </div>
        <div class="msg-block m4">
            <div class="msg-h">
                <img class="msg-hd" src="/show/resources/img/head.jpg" alt="">
            </div>
            <div class='msg-c'>
                <div class="msg-name"><span class="msg-name-text">隔壁老王</span>  <img src="/show/resources/img/woman.png" alt=""></div>
                <div class="msg-text">
                    <div class="msg-img-c" style="display: none">
                        <img class="msg-img" src="/show/resources/img/head.jpg" alt="">
                    </div>
                    <div class="msg-text-c">
                        我要霸屏！
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    window.onload = function () {

    	(function () {

            var psdWidth = 1024,
                rootFontSize = 100;
            var calc = function () {
                var docElement = document.documentElement;
                var clientWidthValue = docElement.clientWidth > psdWidth ? psdWidth : docElement.clientWidth;
                //设置根节点字体大小
                docElement.style.fontSize = rootFontSize * (clientWidthValue / psdWidth) + 'px';
            };
            calc();
            window.addEventListener('resize', calc);
        })();
        
    	
    	
    	var allMsgList = [];
        var allMsgIndex = 0;

        //轮播部分
        var msgLi=$('.msg-block');
        var msgClassArr = ["m0","m1","m2","m3","m4"];
        function allAdd(){
            var last=msgClassArr.pop();
            msgClassArr.unshift(last);
            for (var i = 0; i < msgClassArr.length; i++) {
                msgLi[i].className = msgClassArr[i];
            }
            if(allMsgIndex == allMsgList.length - 1){
                allMsgIndex = 0;
            }else{
                allMsgIndex++;
            }
            var changeBlock = $('.m4');
              changeBlock.find('.msg-hd').attr('src',allMsgList[allMsgIndex].sender.headImg);
              if(allMsgList[allMsgIndex].text.length <= 5){
                  changeBlock.find('.msg-text-c').html(allMsgList[allMsgIndex].text).attr('style','font-size:1.2rem');
              }else{
            	  changeBlock.find('.msg-text-c').html(allMsgList[allMsgIndex].text).attr('style','font-size:0.5rem');
              }
              changeBlock.find('.msg-name-text').html(allMsgList[allMsgIndex].sender.nickname);
              if(allMsgList[allMsgIndex].pic){
            	  changeBlock.find('.msg-img-c').attr('style','display:block');
            	  changeBlock.find('.msg-img').attr('src',allMsgList[allMsgIndex].pic);
              }else{
            	  changeBlock.find('.msg-img-c').attr('style','display:none');
            	  changeBlock.find('.msg-img').attr('src','');
              }
        }
        
        window.setInterval(function(){
        	if(allMsgList.length == 0){
        		return;
        	}
            allAdd();
        },2000);
        

        var socket = undefined;
        // 创建一个Socket实例
        var isHttps = ('https:' == window.location.protocol);
        var protocol = 'ws://';
        if (isHttps) {
            protocol = 'wss://';
        }
        socket = new WebSocket(protocol + window.location.host + '/mobile/chat/socket');

        // 打开Socket
        socket.onopen = function(event) {
            console.log("聊天室连接成功");
        };

        socket.onmessage = function(message) {
            var msg = JSON.parse(message.data);
            console.log("get masage:");
            console.log(msg);

            if(msg.command == 'err'){
                return
            }
            if(msg.command == 'selfInfo'){
                return;
            }
            if(msg.command == 'roomInfo'){
                return;
            }
            if(msg.command == 'prePay'){
            	 return;
            }
            if(!msg.privacy){
            	allMsgList.push(msg);
            	console.log(allMsgList);
            	//msg.duration = 10;
            	if(msg.duration != 0){
                    if(sreenShow){
                        sreenItem.push(msg);
                        return
                    }else{
                        //msg.text = replace_em_null(msg.text);
                        sreenPop(msg);
                    }
                }
            }
        };
        
     // 霸屏排队
     	var sreenShow = false;
     	var sreenItem = [];
        function sreenPop(item){
         var sreenShowSeconds = item.duration;
       	 var seconds = item.duration;
            if(seconds != 0){
            	sreenShow = true;
                $(".wall-all-ctn").show();
                $(".wall-times").html(item.duration);
                if(item.pic){
                	$('.wall-all-right-img').attr('src',item.pic);
                }else{
                	$('.wall-all-right-img').attr('src','/show/resources/img/init.jpg');
                }
                
                $('.wall-texts').html(item.text);
                $('.wall-name-text').html(item.sender.nickname);
                $('.wall-all-left-img').attr('src',item.sender.headImg);
                //item.sender.gender;
                //item.duration;
                var fl = '';
                fl = setInterval(function(){
                    sreenShowSeconds--;
                    if(sreenShowSeconds == 0){
                        clearTimeout(fl);
                        $(".wall-all-ctn").hide();
                        $('.wall-all-right-img').attr('src','');
                        $('.wall-texts').html('');
                        $('.wall-name-text').html('');
                        $('.wall-all-left-img').attr('src',"");
                        sreenShow = false;
                        if(sreenItem.length != 0){
                       	   sreenPop(sreenItem[0]);
                       	   sreenItem.splice(0,1);
                        }
                        return
                    }
                },1000)
            }
       }
    }
</script>
</html>