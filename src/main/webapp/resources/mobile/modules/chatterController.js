/**
 * Created by lzd on 2016/10/7 0007.
 */

define(['jquery','avalon', 'text!./chatter.html',"domReady!"], function ($,avalon,_chatter,domReady) {
    avalon.templateCache._chatter = _chatter;

    var _userList = [];
    
    var chatterController=avalon.define({
            $id:"chatterController",
            tabFlag:'all',
            userList:[],
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
        getUserList();
    }
    
    function getUserList(type){
    	var data = {
    			shopId:1,
    			pageNumber:1,
    			pageSize:500,
    			gender:'',
        	}
    	
    	if(type == 'ALL'){
    	  	data = {
    			shopId:1,
    			pageNumber:1,
    			pageSize:500,
    			gender:'',
        	}
    	}else if(type == 'FEMALE'){
    		data = {
        			shopId:1,
        			pageNumber:1,
        			pageSize:500,
        			gender:'FEMALE',
            	}
    	}else if(type == 'MALE'){
    		data = {
        			shopId:1,
        			pageNumber:1,
        			pageSize:500,
        			gender:'MALE',
            	}
    	}
    	
    	
    	$.ajax({
			  type: "post",
			  url: '/m/chat/chatterList',
			  data: data,
			  success: function(data){
				  console.log(data)
				  _userList = data.results;
				  chatterController.userList = data.results;
			  },
			  dataType: 'json'
		});
    }
    getUserList();

    return chatter = {
        'init':function(){
            init();
        },
    }
});