/**
 * Created by Luozhongdao on 2016/9/15 0015.
 */
define(['jquery','datapicker','datapicker.cn'],function(){
    $(function(){
       var orderController = avalon.define({
    	   	$id:"orderController",
    	   	orderList:[],
    	   	nameList:[],
    	   	pList:{},
    	   	totlePage:0,
    	   	product:false,
    	   	list:true,
    	   	top:true,
    	   	ordername:{
    	   		nickname:""
    	   	},
    	   	orderData:{
    	   		from:"",
    	   		deleted:false,    	   		
    			pageNumber:1,
    			pageSize:8,
    			mintotalFee:"",
    			maxtotalFee:"",
    			statusId:"",
    			startcreationDate:"",
    			endcreationDate:""
    	   	},
    	   	chang:function(){
    			var date = {
    				nickname:orderController.orderData.nickname,
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
    			orderController.orderData.from=id;
    			orderController.ordername.nickname=name;
    			orderController.nameList=[];
    		},
    		btnOrder:function(){
    			orderController.orderData.pageNumber=1;
    			var date = {
    					statusId:orderController.orderData.statusId,
    					from:orderController.orderData.from,
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
	  	      			
	  	      		  },
	  	      		  dataType: 'json'
	  	      		});
    		},
    		page:function(page){
    			Seach(page);
    		},
    		details:function(id){    			
    			orderController.product=true;
    			orderController.list=false;
    			orderController.top=false;
    			orderController.orderList.forEach(function(item){    				 
    				if(item.id == id){    					
    					orderController.pList = item;    					
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
    				statusId:orderController.orderData.statusId,
					from:orderController.orderData.from,
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
	      		  url: '/admin/order/search',
	      		  data:date,
	      		  success: function(data){
	      			  console.log(data);	  	      			 
	      			orderController.orderList = data.results;
	      			orderController.totlePage = data.totalPageCount;
	      			
	      		  },
	      		  dataType: 'json'
	      		});
    	}
    });
    setTimeout(function(){
		$('#date_picker1').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'cn'});
		$('#date_picker2').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'cn'});
	},300);
});