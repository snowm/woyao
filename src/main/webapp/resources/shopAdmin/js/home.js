/**
 * Created by Luozhongdao on 2016/9/20 0020.
 */
define([],function(){
		
    $(function(){
    	$.ajax({
	  		  type: "post",
	  		  url: "/shop/admin/order/main",
	  		  success: function(data){   
	  			  console.log(data);
	  			  pageController.income=data;
	  		  },
	  		  dataType: 'json'
	  	});
    	var pageController=avalon.define({
    		$id:"pageController",    	
    		income:{}  
    	});
    	
    	 avalon.scan();
    	 
    	 
    	
    });
//    
//    function win(){
//    	$.ajax({
//	  		  type: "post",
//	  		  url: "/shop/admin/order/main",
//	  		  success: function(data){   
//	  			  console.log(data);
//	  			  pageController.income=data;
//	  		  },
//	  		  dataType: 'json'
//	  	});
//    }
//	win();
	
//	return home = {
//	    init:function(){
//	    	console.log("home init");
//	    	win();
//	    },
//	   }
   
});