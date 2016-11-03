define(['jquery','datapicker','datapicker.cn'],function(){
	$(function(){
		$.ajax({
			  type: "get",
			  url: '/shop/admin/detail/search',
			  success: function(data){
				  chatController.chatDate.shopId=data.id;	
			  },
			  dataType: 'json'
			});
    	var chatController=avalon.define({
    		$id:"chatController",    	
    		chatList:[],
    		nameList:[],
    		totlePage:"",
    		nothing:false,
    		chatName:{
    			nickname:""
    		},
    		chatDate:{
    			shopId:"",
    			from:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:10,
    	    	nicknameId:"",
    	    	startcreationDate:"",
    	    	endcreationDate:"",
    	    	free:""
    		},
    		chang:function(){
    			var date = {
    				nickname:chatController.chatName.nickname,
    				pageNumber:chatController.chatDate.pageNumber,
    				pageSize:chatController.chatDate.pageSize
    			}
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/profileWX/search',
  	      		  data:date,
  	      		  success: function(data){
  	      			  	console.log(data);    	      			 
  	      			  chatController.nameList = data.results;       			 
  	      			     	      			   	      			  
  	      		  }
  			});
    		},
    		chooseName:function(id,name){
    			chatController.chatDate.nicknameId=id;
    			chatController.chatName.nickname=name;
    			chatController.nameList=[];
    		},
    		btnChat:function(){
    			chatController.chatDate.pageNumber=1;
    			var date = {
    					shopId:chatController.chatDate.shopId,
    					nicknameId:chatController.chatDate.nicknameId,
	    				deleted:chatController.chatDate.deleted,
	    				pageNumber:chatController.chatDate.pageNumber,
	    				pageSize:chatController.chatDate.pageSize,
	    				startcreationDate:chatController.chatDate.startcreationDate,
	    				endcreationDate:chatController.chatDate.endcreationDate,
	    				free:chatController.chatDate.free
	    			}
	    			$.ajax({
	  	      		  type: "post",
	  	      		  url: '/shop/admin/chatMsg/search',
	  	      		  data:date,
	  	      		  success: function(data){
	  	      			  chatController.chatList = data.results;
	  	      			chatController.totlePage = data.totalPageCount;
	  	      			  if(chatController.chatList != ""){
	  	      					chatController.nothing=false;
		  	      			}else if(chatController.chatList == ""){
		  	      				chatController.nothing=true;
		  	      			}
	  	      		  },
	  	      		  dataType: 'json'
	  	      		});
    		},
    		Page:function(page){
    			Seach(page);
    		},
    		deletedMsg:function(id){
    				if(confirm("确认删除 ？")) {
       	    		 $.ajax({
       	  	      		  type: "put",
       	  	      		  url: '/shop/admin/chatMsg/delete/' + id,
       	  	      		  success: function(data){       	  	      			 
       	  	      			  Seach();
       	  	      		  },
       	  	      		  dataType: 'json'
       	  	      	});
       	    	 }
    			}
    		
    		});
    		avalon.scan();
	    	$(document).click(function(){    		
	    		chatController.nameList=[];
	    	});
	    	function Seach(page){
	    		if(page == "upPage"){
  	  	    		if(chatController.chatDate.pageNumber == 1){
  	  	    			alert("已是第一页");
  	  	    			return;
  	  	    		}
  	  	    		chatController.chatDate.pageNumber--;
	      			}else if(page == "nextPage"){
	  	    		if(chatController.chatDate.pageNumber == chatController.totlePage){
	  	    			alert("已是最后一页");
	  	    			return;
	  	    		}
	  	    		chatController.chatDate.pageNumber++;
	      			}
	    		
	    		var date = {
	    				shopId:chatController.chatDate.shopId,
	    				nicknameId:chatController.chatDate.nicknameId,
	    				deleted:chatController.chatDate.deleted,
	    				pageNumber:chatController.chatDate.pageNumber,
	    				pageSize:chatController.chatDate.pageSize,
	    				startcreationDate:chatController.chatDate.startcreationDate,
	    				endcreationDate:chatController.chatDate.endcreationDate,
	    				free:chatController.chatDate.free
	    			}
	    		
	    			$.ajax({
	  	      		  type: "post",
	  	      		  url: '/shop/admin/chatMsg/search',
	  	      		  data:date,
	  	      		  success: function(data){
	  	      			  console.log(data);	  	      			  
	  	      			  chatController.totlePage = data.totalPageCount;
	  	      			  chatController.chatList = data.results;	
	  	      			
	  	      			
	  	      		  },
	  	      		  dataType: 'json'
	  	      		});
	    	}
    	});    	
    	
    	
    	
    	
    	
    	function init(){
        	console.log("init order chat");
        	setTimeout(function(){
        		$('#date_picker1').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'cn'});
        		$('#date_picker2').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'cn'});
        	},300);
        }
        
        init();
        
        return chat = {
    		init:function(){
    			init();
    		}
        }
});