/**
 * Created by Luozhongdao on 2016/9/18 0018.
 */
define([],function(){
    $(function(){
    	var shopController = avalon.define({
    	    $id: "shopController",
    	    formShow:false,
    	    business:true,
    	    businessalter:false,
    	    shopList:[],
    	    queryCdt:{
    	    	name:'',
    	    	deleted:false,
    	    	pageNumber:1,
    	    	pageSize:20,
    	    },
    	    formData:{
    	    	name:'',
    	    	address:'',
    	    	chatRoomName:'',
    	    	description:'',
    	    	latitude:'',
    	    	longitude:'',
    	    	managerName:'',
    	    	managerProfileId:'',
    	    	description:''
    	    },
    	    Alter:function(){
    	    	shopController.business=false;
    	    	shopController.businessalter=true;    	    	
    	    },
    	    save:function(){
    	    	shopController.business=true;
    	    	shopController.businessalter=false;
    	    },
    	    queryData:function(){
    	    	$.ajax({
    	      		  type: "post",
    	      		  url: '/admin/shop/search/',
    	      		  data: shopController.queryCdt,
    	      		  success: function(data){
    	      			  console.log(data)
    	      			  shopController.shopList = data.results;
    	      		  },
    	      		  dataType: 'json'
    	      		});
    	    },
    	    newShop:function(){
    	    	shopController.formShow = true;
    	    	shopController.formData = {
    	    			name:'',
    	    	    	address:'',
    	    	    	chatRoomName:'',
    	    	    	description:'',
    	    	    	latitude:'',
    	    	    	longitude:'',
    	    	    	managerName:'',
    	    	    	managerProfileId:'',
    	    	    	description:''
    	    	}
    	    },
    	    hideNewShop:function(){
    	    	shopController.formShow = false;
    	    },
    	    deleteShop:function(id){
    	    	 if(confirm("确认删除？")) {
    	    		 $.ajax({
    	  	      		  type: "put",
    	  	      		  url: '/admin/shop/delete/' + id,
    	  	      		  success: function(data){
    	  	      			  console.log(data)
    	  	      		  },
    	  	      		  dataType: 'json'
    	  	      		});
    	    	 }
    	    },
    	    updateShop:function(id){
    	    	shopController.formShow = true;
    	    	shopController.shopList.forEach(function(item){
    	    		if(item.id == id){
    	    			shopController.formData = item;
    	    		}
    	    	})
    	    },
    	    submitItem:function(){
    	    	var data = {
    	    			name:shopController.formData.name,
    	    	    	address:shopController.formData.address,
    	    	    	chatRoomName:shopController.formData.chatRoomName,
    	    	    	description:shopController.formData.description,
    	    	    	latitude:shopController.formData.latitude,
    	    	    	longitude:shopController.formData.longitude,
    	    	    	managerName:shopController.formData.managerName,
    	    	    	managerProfileId:shopController.formData.managerProfileId,
    	    	    	description:shopController.formData.description,
    	    	    	id:shopController.formData.id
    	    	}
    	    	
    	    	if(shopController.formData.id){
        	    	console.log(data);
        	    	$.ajax({
      	      		  type: "post",
      	      		  url: '/admin/shop/',
      	      		  data: data,
      	      		  success: function(data){
      	      			  console.log(data)
      	      		  },
      	      		  dataType: 'json',
      	      		});
    	    	}else{
    	    		$.ajax({
        	      		  type: "post",
        	      		  url: '/admin/shop/',
        	      		  data: data,
        	      		  success: function(data){
        	      			  console.log(data)
        	      		  },
        	      		  dataType: 'json'
        	      		});
    	    	}
    	    	
    	    }
    	});
    	avalon.scan();
    	
    	
    	
        console.log("load business-manage");
        
    	function initData(){
    		
    	}
    	
    	//initData();
    	
    	return business = {
    			init:initData,
    	}
    	
    })
});