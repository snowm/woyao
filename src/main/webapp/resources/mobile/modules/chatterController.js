/**
 * Created by lzd on 2016/10/7 0007.
 */

define(['jquery','avalon', 'text!./chatter.html',"domReady!",'socket'], function ($,avalon,_chatter,domReady,socket) {
    avalon.templateCache._chatter = _chatter;
    
    socket.init();
    
    var chatterController=avalon.define({
            $id:"chatterController",
            tabFlag:'all',
            userList:[],
            senderList:[],
            userId:'',
            userInfo:{},
            pMsgCount:0,
            tabChange:function(tab){
               if(tab == 'all'){
            	   chatterController.tabFlag = 'all';
            	   getUserList('ALL');
               }else if(tab == 'male'){
            	   chatterController.tabFlag = 'male';
            	   var list = [];
            	   getUserList('MALE');
               }else if(tab == 'female'){
            	   chatterController.tabFlag = 'female';
            	   var list = [];
            	   getUserList('FEMALE');
               }else if(tab == 'msg'){
            	   chatterController.tabFlag = 'msg';
               }
            },
            chat:function (data) {
            	console.log(data)
                avalon.vmodels.rootController.toWho = data;
                window.location.hash='#!/privacyChat'
            }
        });
    avalon.scan();


    function init(){
        console.log('聊天列表初始化');
        
        chatterController.pMsgCount = avalon.vmodels.mainController.pMsgCount;
        
        var msgs = avalon.vmodels.rootController._privacyMsg.$model;
        var senderList = [];
        
        for(var i = 0; i < msgs.length ; i++ ){
        	var flag = false;
        	for(var j = 0; j < senderList.length ; j++ ){
        		if(msgs[i].sender.id == senderList[j].id){
        			senderList[j].msgCount ++;
        			senderList[j].lastMsg = msgs[i].text;
        			flag = !flag;
        		}
        	}
        	if(!flag){
        		senderList.push({id:msgs[i].sender.id,msgCount:1,imgUrl:msgs[i].sender.headImg,nickname:msgs[i].sender.nickname,gender:msgs[i].sender.gender,lastMsg:msgs[i].text})
        	}
        }
        
        console.log(senderList);
        
        chatterController.senderList = senderList;
        
        chatterController.userInfo = avalon.vmodels.rootController._userInfo;
        
        getUserList();
    }
    
    init();
    
    function getUserList(type){
    	var data = {
    			pageNumber:1,
    			pageSize:500,
    			gender:'',
        	}
    	
    	if(type == 'ALL'){
    	  	data = {
    			pageNumber:1,
    			pageSize:500,
    			gender:'',
        	}
    	}
    	if(type == 'FEMALE'){
    		data = {
        			pageNumber:1,
        			pageSize:500,
        			gender:'FEMALE',
            	}
    	}
    	if(type == 'MALE'){
    		data = {
    				pageNumber:1,
        			pageSize:500,
        			gender:'MALE',
            	}
    	}
    	
    	
    	$.ajax({
			  type: "post",
			  url: '/m/chat/chatterList',
			  data: data,
			  success: function(res){
				  chatterController.userList = [];
				  var data = res.results;
	        	   if(avalon.vmodels.rootController._userInfo != undefined){
	        		   data.forEach(function(item){
	            		   if(item.id != avalon.vmodels.rootController._userInfo.id){
	            			   chatterController.userList.push(item);
	            		   }
	            	   })
	        	   }else{
	        		   chatterController.userList = data;
	        	   }
	        	   
			  },
			  dataType: 'json'
		});
    }

    return chatter = {
        'init':function(){
            init();
        },
    }
});