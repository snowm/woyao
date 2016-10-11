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
    		totlePage:-1,
    		freeDate:{
    			free:"",
    			name:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:3,
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
    		
    		btnChat:function(page){     			
    			chatController.chat=true;     			
    			Seach(page);
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
    	},300);
    	function Seach(page){
    		
    		if(page == "upPage"){
	    		if(chatController.freeDate.pageNumber == 1){
	    			alert("已是第一页");
	    			return;
	    		}
	    		chatController.freeDate.pageNumber--;
	    	}else if(page == "nextPage"){
	    		if(chatController.freeDate.pageNumber == chatController.totlePage){
	    			alert("已是最后一页");
	    			return;
	    		}
	    		chatController.freeDate.pageNumber++;
	    	}
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
	      			chatController.totlePage = data.totalPageCount;
	      			chatController.freeDate.name="";
	      			chatController.freeDate.shopId="";
	      			chatController.freeDate.startcreationDate="";
	      			
	      			chatController.freeDate.endcreationDate="";
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
    	}
        
    });
    
    

});