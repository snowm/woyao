define(["datapicker"],function(){

    $(function(){
    	var orderController = avalon.define({
    		$id:"orderController",
    		orderList:[],
    		pList:{},
    		product:false,
    		formShow:false,
    		nothing:false,
    		product:false,
    		order:false,
    		totlePage:"",
    		orderData:{ 
    			deleted:"",
    			pageNumber:1,
    			pageSize:8,
    			mintotalFee:"",
    			maxtotalFee:"",
    			statusId:"",
    			startcreationDate:"",
    			endcreationDate:""
    		},
    		queryData:function(){  
    			orderController.orderData.pageNumber=1;
    			var date = orderController.orderData;
             $.ajax({
                 type: "post",
                 url: '/admin/order/search',
                 data:date,
                 success: function(data){
//                	 data.results.forEach(function(item){
//                		 item.totalFee = item.totalFee/100;
//                	 })
                     orderController.orderList = data.results;
                     orderController.totlePage = data.totalPageCount;
                     if(orderController.orderList == ""){
                    	 orderController.nothing=true;
                    	 orderController.order=false;
                     }else if(orderController.orderList != ""){
                    	 orderController.order=true;
                    	 orderController.nothing=false;
                     }
    	      			
                 },
                 dataType: 'json'
             }); 	    	
    		},
    		details:function(id){
    			orderController.order=false;
    			orderController.product=true;
    			orderController.formShow=true;
    			orderController.orderList.forEach(function(item){    				 
    				if(item.id == id){    					
    					orderController.pList = item;    					
    	    		}
    			})    			
    		},
    		back:function(){
    			orderController.order=true;
    			orderController.product=false;
    			orderController.formShow=false;
    		},
    		page:function(page){
    			Submit(page);
    		}
    		
    	});    	
    	 function Submit(page){ 
    	    	
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
    	    			pageNumber:orderController.orderData.pageNumber,
    	    			pageSize:orderController.orderData.pageSize,
    	    			mintotalFee:orderController.orderData.mintotalFee,
    	    			maxtotalFee:orderController.orderData.maxtotalFee,
    	    			statusId:orderController.orderData.statusId,
    	    			startcreationDate:orderController.orderData.startcreationDate,
    	    			endcreationDate:orderController.orderData.endcreationDate
    	    	}
    	    	 
    	     $.ajax({
    	         type: "post",
    	         url: '/admin/order/search',
    	         data:date,
    	         success: function(data){
//    	        	 data.results.forEach(function(item){
//                		 item.totalFee = item.totalFee/100;
//                	 })
    	             orderController.orderList = data.results;
    	             orderController.totlePage = data.totalPageCount;
    	             if(orderController.orderList == ""){
    	            	 orderController.nothing=true;
    	            	 orderController.order=false;
    	             }else if(orderController.orderList != ""){
    	            	 orderController.order=true;
    	             }
    	      			
    	         },
    	         dataType: 'json'
    	     }); 	    	
    	    }
    });
    console.log("order");
    avalon.scan();
    
   
    
   	function initData(){
   		setTimeout(function(){
   			$('#date_picker1').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'cn'});
   			$('#date_picker2').datetimepicker({format: 'yyyy-mm-dd hh:ii',language: 'cn'});
   		},300);
	}
	
	initData();
	
	return orderPage = {
			init:initData,
	}
    
	
});