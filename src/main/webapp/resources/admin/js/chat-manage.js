/**
 * Created by Administrator on 2016/10/6 0006.
 */
define(['jquery','datapicker','datapicker.cn'],function($){
    $(function(){
    	var chatController=avalon.define({
    		$id:"chatController",    		
    		chatList:[],
    		shopList:[],
    		nothing:false,    		
    		freeDate:{
    			free:"",
    			name:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:10,
    	    	shopId:"",    	    	
    	    	startcreationDate:"",
    	    	endcreationDate:""
    		},
    		change:function(){
    			console.log(chatController.freeDate.name);
    			var data=chatController.freeDate;    			
    			$.ajax({
    	      		  type: "post",
    	      		  url: '/admin/shop/search/',
    	      		  data:data,
    	      		  success: function(data){
    	      			  console.log(data);    	      			 
    	      			  chatController.shopList = data.results;
    	      			  console.log(chatController.shopList);    	      			   	      			  
    	      		  }
    			});
    			
    		},
    		chooseShop:function(id,name){
    			console.log(id,name);   
    			chatController.freeDate.shopId = id;
    			chatController.freeDate.name=name;
    			chatController.shopList=[];
    		},
    		btns:function(){
    			chatController.shopList=[];
    		},
    		
    		btnChat:function(){     			
    			chatController.chat=true;     			
    			var data = { 
    					free:chatController.freeDate.free,
    					deleted:chatController.freeDate.deleted,
    	    			pageNumber:chatController.freeDate.pageNumber,
    	    	    	pageSize:chatController.freeDate.pageSize,
    	    	    	shopId:chatController.freeDate.shopId,
    	    	    	startcreationDate:chatController.freeDate.startcreationDate,
    	    	    	endcreationDate:chatController.freeDate.endcreationDate    	    	    	
    	    	    	}    			
    			   
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/chatMsg/search/',
  	      		  data:data,
  	      		  success: function(data){
  	      			  console.log(data); 	
  	      			chatController.chatList = data.results;  	      			
//  	      			for(var i = 0;i <= chatController.chatList.length;i++){
//	  	      			if(chatController.chatList[i].free == "true" ){
//	  	      				chatController.block=true;
//	  	      			}else if(chatController.chatList[i].free == "false"){
//	  	      				chatController.none=true;
//	  	      			}
//  	      			}
  	      			
  	      			
  	      			if(chatController.chatList.length != 0){
  	      				chatController.nothing=false;
  	      				console.log(1);
  	      			}else if(chatController.chatList.length == 0){  	      				
  	      				chatController.nothing=true;
  	      				console.log(2);
  	      			}
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
//    	$(".mainpanel").click({
//    		chatController.shopList=[];
//    	});
    	console.log("load chat-manage");    
    	avalon.scan();    	
    	
    	
    	setTimeout(function(){
    		$('#date_picker1').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'zh-CN'});
    		$('#date_picker2').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'zh-CN'});
    	},300)
        
    });
    
    

});