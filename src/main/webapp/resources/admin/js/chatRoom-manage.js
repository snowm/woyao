/**
 * Created by Administrator on 2016/10/6 0006.
 */
define([],function(){
	

    $(function(){
    	var chatRoomController=avalon.define({
    		$id:"chatRoomController",
    		chatRoom:false,
    		chatRoomList:[],
    		roomDate:{
    			name:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:21
    		},
    		btnChatroom:function(){
    			chatRoomController.chatRoom=true;
    			console.log(chatRoomController.roomDate.name);
    			var date = {
    					name:chatRoomController.roomDate.name,
    					deleted:chatRoomController.roomDate.deleted,
    	    			pageNumber:chatRoomController.roomDate.pageNumber,
    	    	    	pageSize:chatRoomController.roomDate.pageSize
    			}    			
    			
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/chatroom/search',
  	      		  data:date,
  	      		  success: function(data){
  	      			  console.log(data)
//  	      			  chatController.chatList = data;  	      			  
  	      		  },
  	      		  dataType: 'json'
  	      		});
    		},
    	});
    	console.log("load chatroom-manage");
    	avalon.scan();
        
    });


});