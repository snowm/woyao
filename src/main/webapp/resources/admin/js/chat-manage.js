/**
 * Created by Administrator on 2016/10/6 0006.
 */
define(['jquery','datapicker','datapicker.cn'],function($){
    $(function(){
    	var chatController=avalon.define({
    		$id:"chatController",    		
    		chatList:[],
    		shopList:[],
    		freeDate:{
    			free:"",
    			name:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:10,
    	    	shopId:"",
    	    	creationDate:"",
    	    	lastModifiedDate:""
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
    		
    		btnChat:function(){     			
    			chatController.chat=true;    			
    			var data = { 
    					free:chatController.freeDate.free,
    					deleted:chatController.freeDate.deleted,
    	    			pageNumber:chatController.freeDate.pageNumber,
    	    	    	pageSize:chatController.freeDate.pageSize,
    	    	    	shopId:chatController.freeDate.shopId,
    	    	    	creationDate:chatController.freeDate.creationDate,
    	    	    	lastModifiedDate:chatController.freeDate.lastModifiedDate
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
    	
    
    	setTimeout(function(){
    		$('#date_picker1').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'zh-CN'});
    		$('#date_picker2').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'zh-CN'});
    	},300)
        
    });
    
    

});