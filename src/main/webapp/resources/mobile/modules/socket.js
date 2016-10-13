
define(['jquery','avalon',"domReady!"], function ($,avalon,domReady) {
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
    
    
    socket.onmessage = function(message) {
        var msg = JSON.parse(message.data);
        console.log("get masage:");
        console.log(msg);
        msg.text = replace_em(msg.text);
        
        if(msg.privacy){
        	if(window.location.hash == '#!/privacyChat'){
        		if(msg.sender.id == avalon.vmodels.pChatController.toWho.id || msg.command == 'smACK'){
            		avalon.vmodels.pChatController.pMsgList.push(msg);
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
            		$(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 200},100,'swing');
            		avalon.vmodels.mainController.pageDownBtn = false;
            	}else{
            		avalon.vmodels.mainController.pageDownBtn = true;
            	}
        	}
        	
        }

    }
    

    // 监听Socket的关闭
    socket.onclose = function(event) { 
    	avalon.vmodels.rootController.lock = true;
    }; 
    
    
    // compile QQ faceCode
    function replace_em(str){
        str = str.replace(/\</g,'&lt;');
        str = str.replace(/\>/g,'&gt;');
        str = str.replace(/\n/g,'<br/>');
        str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/js/qqface/face/$1.gif'/>");
        return str;
    };


    /* qqface */
    
    return socket
});
