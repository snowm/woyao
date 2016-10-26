
define(['jquery','avalon','wxsdk',"domReady!"], function ($,avalon,wx,domReady) {
    var socket = undefined;
    
    var socket = {
        ws: undefined,
        initilized : false,
        path:'/mobile/chat/socket',
        init: function(){
          if (!this.initilized) {
            this.initilized = true;

            // 创建一个Socket实例
            var isHttps = ('https:' == window.location.protocol);
            var protocol = 'ws://';
            if (isHttps) {
                protocol = 'wss://';
            }
            this.ws = new WebSocket(protocol + window.location.host + '/mobile/chat/socket');
         // 打开Socket 
            this.ws.onopen = function(event) {
                if(window.location.hash == '#!/'){
                    setTimeout(function(){
                        avalon.vmodels.mainController.tipsShow = true;
                        avalon.vmodels.mainController.tipsMsg = '欢迎进入聊天室';
                        console.log("聊天室连接成功");

                        setTimeout(function(){
                            avalon.vmodels.mainController.tipsShow = false;
                        },3000)
                    },300)
                };
            };


            this.ws.onmessage = function(message) {

                var msg = JSON.parse(message.data);
                console.log("get masage:");
                console.log(msg);

                if(msg.command == 'err'){
                    alert(msg.reason);
                    return
                }
                if(msg.command == 'selfInfo'){

                    avalon.vmodels.rootController._userInfo = msg;
                    avalon.vmodels.mainController.userInfo = msg;
                	
                	$.ajax({
                        url: "/m/chat/listMsg",
                        dataType: "JSON",
                        data: {
                       	 withChatterId:'', 
                         minId:'',
                         maxId:-1,
                         pageSize:8,
                    },
                        async: true,
                        type: "post",
                        success: function(data) {
                            console.log("msg: _____");
                            console.log(data);
                            var msg = data;
                            for(var i = 0;i < msg.length ; i++){
                                msg[i].text = replace_em(msg[i].text);
                                if(msg[i].privacy){
                                	
                                }else{
                                	avalon.vmodels.rootController._publicMsg.unshift(msg[i]);
                                	avalon.vmodels.mainController.msgList.unshift(msg[i]);
                                    
                                    if($(".msg-block-container").height() - $(".msg-block-contain").height() - $(".msg-block-contain").scrollTop() < 600){
                                		$(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 200},0,'swing');
                                		avalon.vmodels.mainController.pageDownBtn = false;
                                	}else{
                                		avalon.vmodels.mainController.pageDownBtn = true;
                                	}
                                }
                            };

                            setTimeout(function(){
                            	avalon.vmodels.mainController.queryHistoryIng = false;
                            },800)

                            avalon.scan();
                        },
                        complete: function() {
                        	
                        },
                        error: function() {

                        }
                    });
                	
                    return;
                }
                if(msg.command == 'roomInfo'){
                    avalon.vmodels.rootController._roomInfo = msg;
                    return;
                }
                if(msg.command == 'prePay'){
                    
                    var data = msg.prepayInfo;

                    wx.chooseWXPay({
                        'timestamp': data.timeStamp, 
                        'nonceStr': data.nonceStr, 
                        'package': data.packageStr,
                        'signType': data.signType,
                        'paySign': data.paySign, 
                        success: function (res) {
                            // 支付成功后的回调函数
                            avalon.vmodels.rootController._loading = false;
                            avalon.vmodels.mainController.hidePopSend();
                            if(avalon.vmodels.mainController.emojiShow){
                                avalon.vmodels.mainController.pluginShow = false;
                                avalon.vmodels.mainController.emojiShow = false;
                            }
                            avalon.vmodels.mainController.payCount = 0;
                            avalon.vmodels.mainController.msgText = '';
                            avalon.vmodels.mainController.pluginShow = false;
                            avalon.vmodels.mainController.imgUrl = '';
                            avalon.vmodels.mainController.imgViewSrc = '/resources/static/img/photo.png';
                            $("#photoInput").val('');
                        },
                        cancel: function(){
                        	avalon.vmodels.rootController._loading = false;
                            avalon.vmodels.mainController.hidePopSend();
                            if(avalon.vmodels.mainController.emojiShow){
                                avalon.vmodels.mainController.pluginShow = false;
                                avalon.vmodels.mainController.emojiShow = false;
                            }
                            avalon.vmodels.mainController.payCount = 0;
                            avalon.vmodels.mainController.msgText = '';
                            avalon.vmodels.mainController.pluginShow = false;
                            avalon.vmodels.mainController.imgUrl = '';
                            avalon.vmodels.mainController.imgViewSrc = '/resources/static/img/photo.png';
                            $("#photoInput").val('');
                        },
                        error:function(msg){
                        	alert("支付失败");
                        	avalon.vmodels.rootController._loading = false;
                            avalon.vmodels.mainController.hidePopSend();
                            if(avalon.vmodels.mainController.emojiShow){
                                avalon.vmodels.mainController.pluginShow = false;
                                avalon.vmodels.mainController.emojiShow = false;
                            }
                            avalon.vmodels.mainController.payCount = 0;
                            avalon.vmodels.mainController.msgText = '';
                            avalon.vmodels.mainController.pluginShow = false;
                            avalon.vmodels.mainController.imgUrl = '';
                            avalon.vmodels.mainController.imgViewSrc = '/resources/static/img/photo.png';
                            $("#photoInput").val('');
                        }
                    });
                }
                

                msg.text = replace_em(msg.text);
                if(msg.privacy){
                    if(window.location.hash == '#!/privacyChat'){
                        if(msg.sender.id == avalon.vmodels.pChatController.toWho.id || msg.command == 'smACK'){
                            avalon.vmodels.pChatController.pMsgList.push(msg);
                            $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 100},300,'swing');
                        }
                    };
                    avalon.vmodels.rootController._privacyMsg.push(msg);
                    avalon.vmodels.mainController.pMsgCount = 0;
                    avalon.vmodels.rootController._privacyMsg.forEach(function(item){
                        if(item.command != 'smACK'){
                            avalon.vmodels.mainController.pMsgCount++;
                        }
                    });
                }else{
                    avalon.vmodels.rootController._publicMsg.push(msg);
                    avalon.vmodels.mainController.msgList.push(msg);

                    if(window.location.hash == '#!/'){
                        if($(".msg-block-container").height() - $(".msg-block-contain").height() - $(".msg-block-contain").scrollTop() < 600){
                            $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 200},200,'swing');
                            avalon.vmodels.mainController.pageDownBtn = false;
                            if(msg.duration != 0){
                                if(avalon.vmodels.mainController.sreenShow){
                                    msg.text = replace_em_null(msg.text);
                                    avalon.vmodels.mainController.sreenItem.push(msg);
                                    return
                                }else{
                                    msg.text = replace_em_null(msg.text);
                                    sreenPop(msg);
                                }
                            }
                        }else{
                            avalon.vmodels.mainController.pageDownBtn = true;
                        }
                    }

                }

            };

            // 监听Socket的关闭
            this.ws.onclose = function(event) {
                avalon.vmodels.rootController.lock = true;
            };
          }
        },

        
    }
//    socket = new WebSocket(protocol + window.location.host + '/mobile/chat/socket');

    // 霸屏排队
    function sreenPop(item){
   	 var seconds = item.duration;
        if(seconds != 0){
            avalon.vmodels.mainController.sreenShow = true;
            avalon.vmodels.mainController.sreenImg = item.pic;
            avalon.vmodels.mainController.sreenMsg = item.text;
            avalon.vmodels.mainController.sreenTime = item.duration;
            avalon.vmodels.mainController.sreenHead = item.sender.headImg;
            avalon.vmodels.mainController.sreenName = item.sender.nickname;
            avalon.vmodels.mainController.sreenGender = item.sender.gender;
            avalon.vmodels.mainController.sreenShowSeconds = item.duration;
            
            avalon.vmodels.mainController.sreenIsToOther = true;
            avalon.vmodels.mainController.sreenIsToOtherMsg = item.to;
            
            var fl = '';
            fl = setInterval(function(){
                avalon.vmodels. mainController.sreenShowSeconds--;
                if(avalon.vmodels.mainController.sreenShowSeconds == 0){
                    clearTimeout(fl);
                    avalon.vmodels. mainController.sreenImg = '';
                    avalon.vmodels. mainController.sreenMsg = '';
                    avalon.vmodels. mainController.sreenTime = '';
                    avalon.vmodels.mainController.sreenShow = false;
                    

                    avalon.vmodels.mainController.sreenIsToOther = false;
                    avalon.vmodels.mainController.sreenIsToOtherMsg = {};
                    
                    if(avalon.vmodels.mainController.sreenItem.length != 0){
                   	 sreenPop(avalon.vmodels.mainController.sreenItem[0]);
                   	 avalon.vmodels.mainController.sreenItem.splice(0,1);
                    }
                    return
                }
            },1000)
        }
   }
    

    // compile QQ faceCode
    function replace_em(str){
        str = str.replace(/\</g,'&lt;');
        str = str.replace(/\>/g,'&gt;');
        str = str.replace(/\n/g,'<br/>');
        str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/js/qqface/face/$1.gif'/>");
        return str;
    };
    
    function replace_em_null(str){
        str = str.replace(/\</g,'&lt;');
        str = str.replace(/\>/g,'&gt;');
        str = str.replace(/\n/g,'<br/>');
        str = str.replace(/\[em_([0-9]*)\]/g,"");
        return str;
    };
    /* qqface */

    return socket
});
