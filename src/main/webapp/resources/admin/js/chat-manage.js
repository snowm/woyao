/**
 * Created by Administrator on 2016/10/6 0006.
 */
define([],function(){

    $(function(){
    	var chatController=avalon.define({
    		$id:"chatController",    		
    		chatList:[],
    		freeDate:{
    			free:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:21,
    	    	shopId:1
    		},
    		btnChat:function(){
    			chatController.chat=true;
    			console.log(chatController.freeDate.free);
    			var data = { 
    					free:chatController.freeDate.free,
    					deleted:chatController.freeDate.deleted,
    	    			pageNumber:chatController.freeDate.pageNumber,
    	    	    	pageSize:chatController.freeDate.pageSize,
    	    	    	shopId:chatController.freeDate.shopId
    			}    			
    			   
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/chatMsg/search',
  	      		  data:data,
  	      		  success: function(data){
  	      			  console.log(data);
  	      			  chatController.chatList = data.results;
//  	      			  if(chatController.chatList==""){
//  	      				  var tables=document.getElementsByClassName("table-responsive");
//  	      				  var p = document.createElement("div");
//  	      				  p.style.margin="auto";
//  	      				  p.innerText="暂时没有数据";
//  	      				 tables.appendChild(p);  	      				
//  	      			  }
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