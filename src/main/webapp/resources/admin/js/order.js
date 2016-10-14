define([],function(){

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
    			pageNumber:1,
    			pageSize:2,
    			mintotalFee:"",
    			maxtotalFee:"",
    			statusId:""
    		},
    		queryData:function(page){  
    			orderController.order=true;
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
    
	
});