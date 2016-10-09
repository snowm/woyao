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
            	   chatterController.userList = _userList;
               }else if(tab == 'male'){
            	   chatterController.tabFlag = 'male';
            	   var list = [];
            	   _userList.forEach(function(item){
            		   if(item.gender == 'MALE'){
            			   list.push(item);
            		   }
            	   })
            	   chatterController.userList = list;
               }else if(tab == 'female'){
            	   chatterController.tabFlag = 'female';
            	   var list = [];
            	   _userList.forEach(function(item){
            		   if(item.gender == 'FEMALE'){
            			   list.push(item);
            		   }
            	   })
            	   chatterController.userList = list;
               }else if(tab == 'msg'){
            	   chatterController.tabFlag = 'msg';
               }
            },
            chat:function (data) {
            	console.log(data)
                window.location.hash='#!/privacyChat'
            }
        });
    avalon.scan();


    function init(){
        console.log('聊天列表初始化');
        getUserList();
    }
    
    function getUserList(){
    	var data = {
    			shopId:1,
    			pageNumber:1,
    			pageSize:20,
    			gender:'',
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