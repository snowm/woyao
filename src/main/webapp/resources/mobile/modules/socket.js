
define(['jquery','avalon',"domReady!"], function ($,avalon,domReady) {
	var socket = undefined;
	 // 创建一个Socket实例
    socket = new WebSocket("ws://"+window.location.host + '/mobile/chat/socket');
    	
    // 打开Socket 
    socket.onopen = function(event) { 
    	setTimeout(function(){
        	avalon.vmodels.mainController.tipsShow = true;
        	avalon.vmodels.mainController.tipsMsg = '欢迎进入聊天室';
            console.log("聊天室连接成功");
            
            setTimeout(function(){
            	avalon.vmodels.mainController.tipsShow = false;
        	},3000)
    	},300)
    };
    // 监听Socket的关闭
    socket.onclose = function(event) { 
    	avalon.vmodels.mainController.tipsShow = true;
    	avalon.vmodels.mainController.tipsMsg = '网络断开,请刷新页面或重新进入聊天室';
        console.log("链接断开"); 
    }; 
    return socket
});
