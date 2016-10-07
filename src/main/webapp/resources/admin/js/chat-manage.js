/**
 * Created by Administrator on 2016/10/6 0006.
 */
define([],function(){

    $(function(){
    	var chatController=avalon.define({
    		$id:"chatController",
    		chat:false,
    		chatList:[],
    		freeDate:{
    			free:"",
    			pageNumber:1,
    	    	pageSize:21
    		},
    		btnChat:function(){
    			chatController.chat=true;
    			console.log(chatController.freeDate.free);
    			var data = { 
    					free:chatController.freeDate.free,
    	    			pageNumber:chatController.freeDate.pageNumber,
    	    	    	pageSize:chatController.freeDate.pageSize
    			}    			
    			   
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/chatMsg/search',
  	      		  data:data,
  	      		  success: function(data){
  	      			  console.log(data);
  	      			  chatController.chatList = data.results;  
  	      			  console.log(chatController.chatList);
  	      		  },
  	      		  dataType: 'json'
  	      		});
    		},
    		 deleteChat:function(id){
    			 console.log(id);
    	    	 if(confirm("确认删除 ？")) {
    	    		 $.ajax({
    	  	      		  type: "put",
    	  	      		  url: '/admin/chatMsg/delete/' + id,
    	  	      		  success: function(data){
    	  	      			  console.log(data);
    	  	      		  },
    	  	      		  dataType: 'json'
    	  	      	});
    	    	 }
    	    },
    	});
    	console.log("load chat-manage");
    	avalon.scan();
        
    });


});