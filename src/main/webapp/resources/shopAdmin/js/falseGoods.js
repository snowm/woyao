/**
 * Created by Administrator on 2016/10/6 0006.
 */
define(['uploadfile'],function(){
//	$.ajax({
//		  type: "get",
//		  url: '/shop/admin/detail/search',
//		  success: function(data){
//			  console.log(data);
//			  goodsController.goodsDate.shopId=data.id;	
//		  },
//		  dataType: 'json'
//		});


    var goodsController = avalon.define({
        $id:"goodsController",
        goods:false,
        goodsShow:false,
        choose:true,
        goodsAdd:false,
        demand:true,
        nothing:false,
//        picture:false,
        shop:false,
        nothing:false,
        totlePage:0,
        goodsList:[],
        shopList:[],
        imgViewSrc:'/admin/resources/images/photos/upload1.png',
        goodsDate:{
        	shopId:"",
            name:"",
            deleted:false,
            pageNumber:1,
            pageSize:7         
        },        
        goodsChg:{ 
            name:"",
            holdTime:"",
            effectCode:"",
            description:"",
//    			mainPic:"",
            mainPicId:"",
            typeId:"",
            shopId:"",
            shopName:"",
            unitPrice:"",
            holdTime:"",
            effectCode:"",
            pageNumber:1,
            pageSize:12
        },
        //查询商品
        btnGoods:function(){          
            goodsController.goodsAdd = false;
            goodsController.goodsShow = false;     
            goodsController.goodsDate.pageNumber=1;
 	    	 var date = { 	                
 	                 name:goodsController.goodsDate.name,
 	                 deleted:goodsController.goodsDate.deleted,                
 	                 pageNumber:goodsController.goodsDate.pageNumber,
 	                 pageSize:goodsController.goodsDate.pageSize,
// 	                 shopId:goodsController.goodsChg.shopId
 	             }
             $.ajax({
                 type: "post",
                 url: '/shop/admin/product/search',
                 data:date,
                 success: function(data){
                     console.log(data);
                     goodsController.goodsList = data.results;
                     goodsController.totlePage = data.totalPageCount; 
   	      			if(goodsController.goodsList.length != 0){
   	      				goodsController.nothing=false;
   	      				goodsController.goods=true;
   	      				console.log(1);
   	      			}else if(goodsController.goodsList.length == 0){  	      				
   	      				goodsController.nothing=true;
   	      				goodsController.goods=false;
   	      				console.log(2);
   	      			}
                 },
                 dataType: 'json'
             });
        },
        page:function(page){
        	Submit(page);
        },
		clickOut:function(){			
			goodsController.shopList=[];			
		},
        Alter:function(id){         	
            goodsController.goodsShow = true;
            goodsController.goods = false;
            goodsController.demand = false;            
    		goodsController.shop=true;
    		goodsController.goodsList.forEach(function(item){
                    if(item.id == id){
                        goodsController.goodsChg = item;
                        goodsController.imgViewSrc = item.mainPic;
                        $(".prdocut").attr("disabled","disabled").css({backgroundColor:"#999",color:"white"});
                        $(".description").attr("disabled","disabled").css({backgroundColor:"#999",color:"white"});
                        $(".effectCode").attr("disabled","disabled").css({backgroundColor:"#999",color:"white"});
                        $(".holdTime").attr("disabled","disabled").css({backgroundColor:"#999",color:"white"});
                    }
                })
        },        
        //修改并保存
        Sava:function(){            
            var prdocut = $(".prdocut").val();
            var description = $(".description").val();
            var unitPrice = $(".unitPrice").val();
            var shopType = $(".shopType").val(); 
            if(goodsController.goodsChg.typeId == "2"){
            		console.log(1111)
            		if(prdocut == ""){
        				alert("产品名称不能为空");
        				return;
        			}else if(unitPrice == ""){
        				alert("产品单价不能为空");
        				return;
        			}else{		
        				if(goodsController.goodsChg.effectCode == null || goodsController.goodsChg.holdTime == null){
        					goodsController.goodsChg.effectCode = '';
        					goodsController.goodsChg.holdTime = '';
        				}
        				 var date={
        		                    name:goodsController.goodsChg.name,        		                    
        		                    description:goodsController.goodsChg.description,
//        		                    mainPicId:goodsController.goodsChg.mainPicId,
        		                    typeId:parseInt(goodsController.goodsChg.typeId),
//        		                    shopId:goodsController.goodsDate.shopId,
        		                    unitPrice:goodsController.goodsChg.unitPrice,
        		                    id:goodsController.goodsChg.id,
        		                    holdTime:goodsController.goodsChg.holdTime,
        		                    effectCode:goodsController.goodsChg.effectCode,
        		                }
        				 
        		            $.ajax({
        		                type: "post",
        		                url: '/shop/admin/product/',
        		                data:date,
        		                success: function(data){
        		                    console.log(data);
        		                    alert('提交成功');
        		                    goodsController.goodsShow = false;
        		                    goodsController.goods = true;
        		                    goodsController.goodsChg.mainPicId = '';    
        		                    goodsController.goodsChg.name="";
        		                    goodsController.imgViewSrc = '/admin/resources/images/photos/upload1.png';
        		                    $("#uploadFileIpt").val('');
        		                    goodsController.goodsShow=false;
        		    	            goodsController.demand=true;
        		                    Submit();
        		                },
        		                dataType: 'json',
        		            });
        				} 
            	} 
        },
        hideNewShop:function(){
        	goodsController.goodsShow = false;
        	goodsController.demand = true;
        	goodsController.goods = true;
        	goodsController.nothing = false;
        	goodsController.goodsChg.mainPicId = '';
        	goodsController.goodsChg.name="";
        	goodsController.goodsChg.shopId="";
            goodsController.imgViewSrc = '/admin/resources/images/photos/upload1.png';
            $("#uploadFileIpt").val('');
	    }, 
    });
    console.log("load goods-manage");
    avalon.scan();
    function Submit(page){    	
 	    	if(page == "upPage"){
 	    		if(goodsController.goodsDate.pageNumber == 1){
 	    			alert("已是第一页");
 	    			return;
 	    		}
 	    		goodsController.goodsDate.pageNumber--;
 	    	}else if(page == "nextPage"){
 	    		if(goodsController.goodsDate.pageNumber == goodsController.totlePage){
 	    			alert("已是最后一页");
 	    			return; 
 	    		}
 	    		goodsController.goodsDate.pageNumber++;
 	    	} 	    	
 	       
 	    	 var date = { 	                
 	                 name:goodsController.goodsChg.name,
 	                 deleted:goodsController.goodsDate.deleted,                
 	                 pageNumber:goodsController.goodsDate.pageNumber,
 	                 pageSize:goodsController.goodsDate.pageSize,
// 	                 shopId:goodsController.goodsChg.shopId
 	             }
 	    	 
 	    	 
             $.ajax({
                 type: "post",
                 url: '/shop/admin/product/search',
                 data:date,
                 success: function(data){
                     console.log(data);
                     goodsController.goodsList = data.results;
                     goodsController.totlePage = data.totalPageCount;
   	      			if(goodsController.goodsList.length != 0){
   	      				goodsController.nothing=false;
   	      				console.log(1);
   	      			}else if(goodsController.goodsList.length == 0){  	      				
   	      				goodsController.nothing=true;
   	      				console.log(2);
   	      			}
                 },
                 dataType: 'json'
             });
    }
    
	function initData(){
        console.log("load goods");
	}
	
	initData();
	
	return goods = {
			init:initData,
	}

});

