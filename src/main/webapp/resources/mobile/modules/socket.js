
define(['jquery','avalon','wxsdk',"domReady!","cookie"], function ($,avalon,wx,domReady) {
    var socket = undefined;
    
   
    
    var socket = {
        ws: undefined,
        initilized : false,
        path:'/mobile/chat/socket',
        initShareFlag:false,
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
            var that = this.ws;
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
                    
                    /*send head msg*/
                    
                    function sendGPS(ws){
                    	wx.getLocation({
                    	    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                    	    success: function (res) {
                    	        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                    	        var longitude = res.longitude ; // 经度，浮点数，范围为180 ~ -180。
                    	        var speed = res.speed; // 速度，以米/每秒计
                    	        var accuracy = res.accuracy; // 位置精度
                    	        var urls = "http://api.map.baidu.com/geoconv/v1/?coords=" +　longitude + "," + latitude + "&from=1&to=5&ak=mZm3GQOvw7AFyZIKrkeomWMbhMbpP2Cc";
                    	        $.ajax({
                    	            url: urls,   
                    	            dataType: "JSONP",  
                    	            async: true, 
                    	            type: "get",
                    	            success: function(data) {
                    	            	console.log("send head msg:");
                    	            	var text = 'gps\n';
                    	            	text += JSON.stringify({longitude:data.result[0].x,latitude:data.result[0].y})
                                    	ws.send(text);
                    	            },
                    	            complete: function() {
                    	            },
                    	            error: function() {
                    	            	alert("GPS坐标转换百度坐标失败");
                    	            }
                    	        });
                    	    },
                    	    error : function(){
                    	    },
                            fail : function(){
                            },
                            failure : function(){
                            }
                    	});
                    };
                    setTimeout(sendGPS(that),2000);
                    setInterval(sendGPS(that),1000 * 60 * 2);//2分钟发一次 地理位置
                    /*send head msg*/
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
                	msg.id = msg.self.id;
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
                	
                	/*var roomLink = 'http://www.luoke30.com/m/chatRoom/' + msg.statistics.id + '#!/';
                	// 存一个房间链接在cookie 刷新的时候用；
                	$.cookie("roomLink",roomLink,{
	            		 "path":"/",
	            		 "expires":1
            		});*/
            		
                    avalon.vmodels.rootController._roomInfo = msg;
                    if(window.location.hash == '#!/'){
                    	
                    	if(!this.initShareFlag){
                        	wx.hideMenuItems({
                        	    menuList: ['menuItem:share:qq','menuItem:share:QZone','menuItem:share:weiboApp','menuItem:share:facebook','menuItem:share:QZone','menuItem:openWithSafari','menuItem:openWithQQBrowser','menuItem:readMode','menuItem:originPage','menuItem:copyUrl'] // 要隐藏的菜单项，所有menu项见附录3
                        	});
                    		wx.onMenuShareAppMessage({
                        	    title: '点击进入-我要聊天室', // 分享标题
                        	    desc: '欢迎来到我要酒吧聊天室，聊天、晒照、交友。', // 分享描述
                        	    link: 'http://www.luoke30.com/m/chatRoom/' + msg.statistics.id + '#!/', // 分享链接
                        	    imgUrl: 'http://www.luoke30.com/show/resources/img/logo.png', // 分享图标
                        	    type: '', // 分享类型,music、video或link，不填默认为link
                        	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                        	    success: function () {
                        	        alert("谢谢您的分享。")
                        	    },
                        	    cancel: function () {
                        	        // 用户取消分享后执行的回调函数
                        	    }
                        	});
                    		wx.onMenuShareTimeline({
                    		    title: '点击进入-我要聊天室', // 分享标题
                    		    link: 'http://www.luoke30.com/m/chatRoom/' + msg.statistics.id + '#!/', // 分享链接
                    		    imgUrl: 'http://www.luoke30.com/show/resources/img/logo.png', // 分享图标
                    		    success: function () { 
                    		        // 用户确认分享后执行的回调函数
                    		    },
                    		    cancel: function () { 
                    		        // 用户取消分享后执行的回调函数
                    		    }
                    		});
                    		this.initShareFlag = true;
                    	}
                    	
                    	 var data = {
                                 pageNumber:1,
                                 pageSize:300,
                                 gender:'',
                             }
                             $.ajax({
                                 type: "post",
                                 url: '/m/chat/chatterList',
                                 data: data,
                                 success: function(data){
                                 	avalon.vmodels.mainController.chatterList = data.results;
                                 },
                                 dataType: 'json',
                                 error: function(){
                                     alert("获取用户列表失败失败");
                                 }
                             });
                    	}
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
                            avalon.vmodels.mainController.hideForOther();
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
                            avalon.vmodels.mainController.hideForOther();
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
                            avalon.vmodels.mainController.hideForOther();
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
                	if(msg.effectCode != null){
                		if(msg.to.id == msg.sender.id){
                    		msg.to = {
                    			gender:'',
                    			nickname:'全场观众',
                    			headImg:'/resources/static/img/all.png',
                        	}
                    	}  
                	}
                	
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
            if(item.effectCode == null){
            	item.to = null;
            	avalon.vmodels.mainController.sreenIsToOther = false;
        	}else{
        		avalon.vmodels.mainController.sreenIsToOther = true;
                avalon.vmodels.mainController.sreenIsToOtherMsg = item.to;       		
        	}
            
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
