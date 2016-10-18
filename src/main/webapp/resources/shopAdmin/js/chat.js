define(['jquery','datapicker','datapicker.cn'],function(){
	$(function(){
    	var chatController=avalon.define({
    		$id:"chatController",    	
    		chatList:[],
    		nameList:[],
    		totlePage:"",
    		chatName:{
    			nickname:""
    		},
    		chatDate:{
    			from:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:10,
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
    			chatController.chatDate.from=id;
    			chatController.chatName.nickname=name;
    			chatController.nameList=[];
    		},
    		btnChat:function(){
    			chatController.chatDate.pageNumber=1;
    			var date = {
	    				from:chatController.chatDate.from,
	    				deleted:chatController.chatDate.deleted,
	    				pageNumber:chatController.chatDate.pageNumber,
	    				pageSize:chatController.chatDate.pageSize,
	    				startcreationDate:chatController.chatDate.startcreationDate,
	    				endcreationDate:chatController.chatDate.endcreationDate,
	    				free:chatController.chatDate.free
	    			}
	    			$.ajax({
	  	      		  type: "post",
	  	      		  url: '/admin/chatMsg/search',
	  	      		  data:date,
	  	      		  success: function(data){
	  	      			  console.log(data);
	  	      			  chatController.totlePage = data.totalPageCount;
	  	      			  chatController.chatList = data.results;	  	      			
	  	      		  },
	  	      		  dataType: 'json'
	  	      		});
    		},
    		page:function(page){
    			Seach(page)
    		},
    		deletedMsg:function(id){
    				if(confirm("确认删除 ？")) {
       	    		 $.ajax({
       	  	      		  type: "put",
       	  	      		  url: '/admin/chatMsg/delete/' + id,
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
	    				from:chatController.chatDate.from,
	    				deleted:chatController.chatDate.deleted,
	    				pageNumber:chatController.chatDate.pageNumber,
	    				pageSize:chatController.chatDate.pageSize,
	    				startcreationDate:chatController.chatDate.startcreationDate,
	    				endcreationDate:chatController.chatDate.endcreationDate,
	    				free:chatController.chatDate.free
	    			}
	    			$.ajax({
	  	      		  type: "post",
	  	      		  url: '/admin/chatMsg/search',
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
    	setTimeout(function(){
    		$('#date_picker1').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'zh-CN'});
    		$('#date_picker2').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'zh-CN'});
    	},300);
    	
});