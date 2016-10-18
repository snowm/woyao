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
    		totlePage:0,
    		orderData:{ 
    			deleted:"",
    			pageNumber:1,
    			pageSize:2,
    			mintotalFee:"",
    			maxtotalFee:"",
    			statusId:"",
    			startcreationDate:"",
    			endcreationDate:""
    		},
    		queryData:function(page){     			
    			var date = orderController.orderData;

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
    	    	
    	    	 
             $.ajax({
                 type: "post",
                 url: '/admin/order/search',
                 data:date,
                 success: function(data){
                     console.log(data);
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
    		}
    		
    	});    	
    
    });
    console.log("order");
    avalon.scan();
    
    function Submit(page){ 
    	 
    }
    
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