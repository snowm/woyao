/**
 * Created by Luozhongdao on 2016/9/15 0015.
 */
define(['jquery','datapicker','datapicker.cn'],function(){
    $(function(){
    	$.ajax({
  		  type: "get",
  		  url: '/shop/admin/detail/search',
  		  success: function(data){
  			  console.log(data);
  			orderController.orderData.shopId=data.id;	
  		  },
  		  dataType: 'json'
  		});
       var orderController = avalon.define({
    	   	$id:"orderController",
    	   	orderList:[],
    	   	nameList:[],    	   
    	   	shopDetail:{},
    	   	totlePage:0,
    	   	product:false,
    	   	list:true,
    	   	top:true,
    	   	nothing: false,
    	   	deailId:"",
    	   	orderName:{
    	   	nickname:""
    	   	},
    	   	orderData:{
    	   		shopId:"",
    	   		nicknameId:"",
    	   		deleted:false,    	   		
    			pageNumber:1,
    			pageSize:15,
    			mintotalFee:"",
    			maxtotalFee:"",
    			statusId:"",
    			startcreationDate:"",
    			endcreationDate:""
    	   	},
    	   	chang:function(){
    			var date = {
    				nickname:orderController.orderName.nickname,
    				pageNumber:orderController.orderData.pageNumber,
    				pageSize:orderController.orderData.pageSize
    			}
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/profileWX/search',
  	      		  data:date,
  	      		  success: function(data){
  	      			  	console.log(data);    	      			 
  	      			orderController.nameList = data.results;       			 
  	      			     	      			   	      			  
  	      		  }
  			});
    		},
    		chooseName:function(id,name){
    			orderController.orderData.nicknameId=id;
    			orderController.orderName.nickname=name;
    			orderController.nameList=[];
    		},
    		btnOrder:function(){
    			orderController.orderData.pageNumber=1;
    			var date = {
    					shopId:orderController.orderData.shopId,
    					statusId:orderController.orderData.statusId,
    					nicknameId:orderController.orderData.nicknameId,
	    				deleted:orderController.orderData.deleted,
	    				pageNumber:orderController.orderData.pageNumber,
	    				pageSize:orderController.orderData.pageSize,
	    				startcreationDate:orderController.orderData.startcreationDate,
	    				endcreationDate:orderController.orderData.endcreationDate,
	    				mintotalFee:orderController.orderData.mintotalFee,
	    				maxtotalFee:orderController.orderData.maxtotalFee
	    			}
	    			$.ajax({
	  	      		  type: "post",
	  	      		  url: '/shop/admin/order/search',
	  	      		  data:date,
	  	      		  success: function(data){
	  	      			  console.log(data);
	  	      			data.results.forEach(function(item){
	  	      				item.totalFee = item.totalFee/100;
	  	      			})
	  	      			orderController.orderList = data.results;
	  	      			orderController.totlePage = data.totalPageCount;
	  	      			if(orderController.orderList != ""){
	  	      			orderController.nothing=false;
	  	      			}else if(orderController.orderList == ""){
	  	      			orderController.nothing=true;
	  	      			}
	  	      			
	  	      		  },
	  	      		  dataType: 'json'
	  	      		});
    		},
    		page:function(page){
    			Seach(page);
    		},
    		detail:function(id){
    			orderController.product=true;
    			orderController.list=false;
    			orderController.top=false;
    				orderController.orderList.forEach(function(item){    				 
        				if(item.id == id){ 		
        					orderController.deailId = item.id; 
        					console.log(orderController.deailId);        					
        					var data={
        							orderId:orderController.deailId
        					}
        					$.ajax({
        		  	      		  type: "post",
        		  	      		  url: '/shop/admin/order/detil',
        		  	      		  data:data,
        		  	      		  success: function(data){
        		  	      			  console.log(data);
        		  	      			orderController.shopDetail=data;
        		  	      		  },
        		  	      		  dataType: 'json'
        		  	      		});
        	    		}
        			});  
    		},
    		back:function(){    			
    			orderController.product=false;
    			orderController.list=true;
    			orderController.top=true;
    		}
       });
       	avalon.scan();
	   	$(document).click(function(){    		
	   		orderController.nameList=[];
	   	});
	   	function Seach(page){	   	
    		if(page == "upPage"){
	  	    		if(orderController.orderData.pageNumber == 1){
	  	    			alert("已是第一页");
	  	    			return;
	  	    		}
	  	    		orderController.orderData.pageNumber--;
      			}else if(page == "nextPage"){
  	    		if(orderController.orderData.pageNumber == orderController.totlePage){
  	    			alert("已是最后一页");
  	    			return;
  	    		}
  	    		orderController.orderData.pageNumber++;
      			}
    		var date = {
    				shopId:orderController.orderData.shopId,
    				statusId:orderController.orderData.statusId,
    				nicknameId:orderController.orderData.nicknameId,
    				deleted:orderController.orderData.deleted,
    				pageNumber:orderController.orderData.pageNumber,
    				pageSize:orderController.orderData.pageSize,
    				startcreationDate:orderController.orderData.startcreationDate,
    				endcreationDate:orderController.orderData.endcreationDate,
    				mintotalFee:orderController.orderData.mintotalFee,
    				maxtotalFee:orderController.orderData.maxtotalFee
    			}
    	
    			$.ajax({
	      		  type: "post",
	      		  url: '/shop/admin/order/search',
	      		  data:date,
	      		  success: function(data){
	      			  console.log(data);	  	      			 
	      			orderController.orderList = data.results;
	      			orderController.totlePage = data.totalPageCount;
	      			if(orderController.orderList != ""){
	  	      			orderController.nothing=false;
	  	      			}else if(orderController.orderList == ""){
	  	      			orderController.nothing=true;
	  	      			}
	      			
	      		  },
	      		  dataType: 'json'
	      		});
    	}
    });
 
    
    function init(){
    	console.log("init order model");
    	   setTimeout(function(){
    			$('#date_picker1').datetimepicker({
    				format: 'yyyy-mm-dd',
    				language: 'cn',
    				todayBtn:true,
    				todayHighlight:true,
    				forceParse:true,
    				onClose: function(dateText, inst) { 
    			    } 	
    			});
    			$('#date_picker2').datetimepicker({
    				format: 'yyyy-mm-dd',
    				language: 'cn',
    				todayBtn:true,
    				todayHighlight:true,
    				forceParse:true,
    				onClose: function(dateText, inst) { 
    			    } 
    			});
    		},300);
    }
    
    init();
    
    return order = {
		init:function(){
			init();
		}
    }
});