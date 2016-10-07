/**
 * Created by Administrator on 2016/10/6 0006.
 */
define([],function(){
	 

    $(function(){
    	var goodsController=avalon.define({
    		$id:"goodsController",
    		goods:false,
    		goodsShow:false,
    		goodsAdd:false,
    		goodsList:[],
    		goodsDate:{
    			name:"",
    			deleted:false,
    			pageNumber:1,
    	    	pageSize:21,    	    	
    		},
    		goodsChg:{
    			name:"",
    			code:"",
    			description:"",
    			mainPic:"",
    			mainPicId:"",
    			typeId:"",
    			shopId:"",
    			shopName:"",
    			unitPrice:""
    		},
    		btnGoods:function(){
    			goodsController.goods=true;    		
    			var date = {
    					name:goodsController.goodsDate.name,
    					deleted:goodsController.goodsDate.deleted,
    	    			pageNumber:goodsController.goodsDate.pageNumber,
    	    	    	pageSize:goodsController.goodsDate.pageSize
    			}   			
    			
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/product/search',
  	      		  data:date,
  	      		  success: function(data){
  	      			  console.log(data);
  	      			  goodsController.goodsList = data.results;  	      			  
  	      		  },
  	      		  dataType: 'json'
  	      		});
    		},
    		 Alter:function(id){
    			 goodsController.goodsShow = true;
    			 goodsController.goods = false;
    			 goodsController.goodsList.forEach(function(item){
     	    		if(item.id == id){
     	    			goodsController.goodsChg = item;
     	    		}
     	    	})
    			 
     	    },
     	    Sava:function(){     	    	
     	    	var date={
     	    			name:goodsController.goodsChg.name,
     	    			code:goodsController.goodsChg.code,
     	    			description:goodsController.goodsChg.description,
     	    			mainPic:goodsController.goodsChg.mainPic,
     	    			mainPicId:goodsController.goodsChg.mainPicId,
     	    			typeId:goodsController.goodsChg.typeId,
     	    			shopId:goodsController.goodsChg.shopId,
     	    			shopName:goodsController.goodsChg.shopName,
     	    			unitPrice:goodsController.goodsChg.unitPrice,
     	    			id:goodsController.goodsChg.id
     	    	}
     	    	console.log(date);
     	    	$.ajax({
    	      		  type: "post",
    	      		  url: '/admin/product/',
    	      		  data:date,
    	      		  success: function(data){
    	      			  console.log(data);
//    	      			  goodsController.goodsList = data.results;  	      			  
    	      		  },
    	      		  dataType: 'json'
    	      		});
     	    },
     	    add:function(){
     	    	goodsController.goodsAdd=true;
     	    },
     	    addSave:function(){
     	    	goodsController.goodsAdd=false;
     	    	var date={
     	    			name:goodsController.goodsChg.name,
     	    			code:goodsController.goodsChg.code,
     	    			description:goodsController.goodsChg.description,
     	    			mainPic:goodsController.goodsChg.mainPic,
     	    			mainPicId:goodsController.goodsChg.mainPicId,
     	    			typeId:goodsController.goodsChg.typeId,
     	    			shopId:goodsController.goodsChg.shopId,
     	    			shopName:goodsController.goodsChg.shopName,
     	    			unitPrice:goodsController.goodsChg.unitPrice     	    			
     	    	}
     	    	$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/product/',
  	      		  data:date,
  	      		  success: function(data){
  	      			  console.log(data);
//  	      			  goodsController.goodsList = data.results;  	      			  
  	      		  },
  	      		  dataType: 'json'
  	      		});
     	    },
    		deletedGoods:function(id){
    			if(confirm("确认删除 ？")) {
   	    		 $.ajax({
   	  	      		  type: "put",
   	  	      		  url: '/admin/product/delete/' + id,
   	  	      		  success: function(data){
   	  	      			  console.log(data)
   	  	      		  },
   	  	      		  dataType: 'json'
   	  	      		});
   	    	 }
    		},
    	})
    	});
    	console.log("load goods-manage");
    	avalon.scan();
        
    });

