/**
 * Created by Luozhongdao on 2016/9/20 0020.
 */
define(["highcharts","exporting","dark-unica"],function(){
		
    $(function(){
    	$.ajax({
			  type: "get",
			  url: '/shop/admin/detail/search',
			  success: function(data){
				  pageController.name=data.name;
			  },
			  dataType: 'json'
			});
    	
    	var pageController=avalon.define({
    		$id:"pageController",    	
    		income:{},
    		shopOrderList:[],
    		name:""
    	});    	
    	 avalon.scan();    	 


		 var date = [];
		 var price = [];
		 $.ajax({
 	  		  type: "post",
 	  		  url: "/shop/admin/order/main",
 	  		  success: function(data){   
 	  			  console.log(data);
 	  			  pageController.income=data;
 	  			  pageController.shopOrderList=data.shopOrders;
	  	  			 pageController.shopOrderList.forEach(function(items){
	  	  				 date.push(items.yearOrder +"/"+ items.monthOrder +"/"+ items.dayOrder);
	  	  				 price.push(items.totalOrder);
	  	  				 initCharts();
	  	  			 });
 	  		  },
 	  		  dataType: 'json'
	  	  	});
		function initCharts(){
     		$('#container').highcharts({
    	        title: {
    	            text:  pageController.name + "收入走势图",
    	            x: -20 //center
    	        },
//    	        subtitle: {
//    	            text: ' ',
//    	            x: -20
//    	        },
    	        xAxis: {
    	            categories:date,
    	        },
    	        yAxis: {
    	            title: {
    	                text: '金额(单位/￥)'
    	            },
    	            plotLines: [{
    	                value: 0,
    	                width: 2,
    	                color: '#808080'
    	            }]
    	        },
    	        tooltip: {
    	            valueSuffix: '￥'
    	        },
    	        legend: {
    	            layout: 'vertical',
    	            align: 'right',
    	            verticalAlign: 'middle',
    	            borderWidth: 0
    	        },
    	        credits:{
    	            enabled:false // 禁用版权信息
    	       },
    	        series: [{
    	            name: pageController.name,
    	            data: price
    	        }]
    	    });
     	}
     	
    	 
    	
    });
    
   function win(){
    	$.ajax({
	  		  type: "post",
	  		  url: "/shop/admin/order/main",
	  		  success: function(data){   
	  			  console.log(data);
	  			  pageController.income=data;
	  		  },
	  		  dataType: 'json'
	  	});
    }
	win();
	
	return home = {
	    init:function(){
	    	console.log("home init");
	    	win();
	    },
	   }
   
});