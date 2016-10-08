/**
 * Created by Administrator on 2016/10/6 0006.
 */
define([],function(){
	 

    $(function(){
    	var goodsController=avalon.define({
    		$id:"goodsController",
    		goods:false,
    		goodsShow:false,
    		choose:false,
    		goodsAdd:false,
    		goodsList:[],
    		shopList:[],
    	    imgViewSrc:'/admin/resources/images/photos/upload1.png',
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
    		//查询商品
    		btnGoods:function(){
    			goodsController.goods=true; 
    			goodsController.goodsAdd = false;
    			goodsController.goodsShow = false;
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
     	   //修改并保存
     	    Sava:function(){  
     	    	goodsController.goodsShow=false;
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
    	      		  },
    	      		  dataType: 'json',	 
    	      		});
     	    },
     	    //新增
     	    add:function(){     	    	
     	    	goodsController.goodsShow=true;
     	    	goodsController.goods=false;
     	    	goodsController.goodsChg = {
     	    			name:"",
     	    			code:"",
     	    			description:"",
     	    			mainPic:"",
     	    			mainPicId:"",
     	    			typeId:"",
     	    			shopId:"",
     	    			shopName:"",
     	    			unitPrice:""
    	    	}    
     	    },
     	    //新增保存
     	    addSave:function(){
     	    	goodsController.goodsAdd=false;	 
     	    	goodsController.goods=false;	 
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
     	    	console.log(date);     	    	
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
    		//选择商店
    		chooseShop:function(){
    			$.ajax({
  	      		  type: "post",
  	      		  url: '/admin/shop/search',
  	      		  data:{
  	      			  name:"",
  	      			  deleted:false,
  	      			pageNumber:1,
  	    	    	pageSize:21
  	      		  },
  	      		  success: function(data){
  	      			  console.log(data);
  	      			goodsController.shopList=data.results;  	      			
	      			       			  
  	      		  },
  	      		  dataType: 'json',	 
  	      		});
    		},
    		chooseShopItem:function(data){
	    			goodsController.goodsChg.shopId = data.id;
	    			goodsController.goodsChg.shopName = data.name;
    		},
    	    uploadImg:function(){
    	    	$("#uploadfile").click();
    	    }
    	})
    	});
    	console.log("load goods-manage");
    	avalon.scan();
        
    });

