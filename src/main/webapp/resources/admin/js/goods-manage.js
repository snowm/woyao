/**
 * Created by Administrator on 2016/10/6 0006.
 */
define(['uploadfile'],function(){


    var goodsController=avalon.define({
        $id:"goodsController",
        goods:false,
        goodsShow:false,
        choose:true,
        goodsAdd:false,
        demand:true,
        nothing:false,
        picture:false,
        shop:false,
        totlePage:0,
        goodsList:[],
        shopList:[],
        imgViewSrc:'/admin/resources/images/photos/upload1.png',
        goodsDate:{
            name:"",
            deleted:false,
            pageNumber:1,
            pageSize:7         
        },        
        goodsChg:{ 
            name:"",
            code:"",
            description:"",
//    			mainPic:"",
            mainPicId:"",
            typeId:"",
            shopId:"",
            shopName:"",
            unitPrice:"",
            pageNumber:1,
            pageSize:12
        },
        //查询商品
        btnGoods:function(){ 
        	goodsController.goodsDate.pageNumber=1;
            goodsController.goodsAdd = false;
            goodsController.goodsShow = false; 
 	    	 var date = { 	                
 	                 name:goodsController.goodsDate.name,
 	                 deleted:goodsController.goodsDate.deleted,                
 	                 pageNumber:goodsController.goodsDate.pageNumber,
 	                 pageSize:goodsController.goodsDate.pageSize,
 	                 shopId:goodsController.goodsChg.shopId
 	             } 
             $.ajax({
                 type: "post",
                 url: '/admin/product/search',
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
        change:function(){		
			goodsController.goodsChg.shopId="";
			var data=goodsController.goodsChg;    			
			$.ajax({
	      		  type: "post",
	      		  url: '/admin/shop/search/',
	      		  data:data,
	      		  success: function(data){
	      			  console.log(data);
	      			  goodsController.shopList = data.results;
	      			  console.log(goodsController.shopList);
	      		  }
			});
			
		},
		shopSearch:function(id,name){
			console.log(id,name);   
			goodsController.goodsChg.shopId = id;
			goodsController.goodsChg.name=name;
			goodsController.shopList=[];
		},
		clickOut:function(){			
			goodsController.shopList=[];			
		},
        Alter:function(id){         	
            goodsController.goodsShow = true;
            goodsController.goods = false;
            goodsController.demand = false;            
    		goodsController.shop=true;    
    		goodsController.uploadbtn = true;
    		goodsController.goodsList.forEach(function(item){
                    if(item.id == id){
                        goodsController.goodsChg = item;                       
                        if(goodsController.goodsChg.typeId == "1"){
                        	 goodsController.picture=true;
                        	 goodsController.imgViewSrc = item.mainPic;                        	
                        }else if(goodsController.goodsChg.typeId == "2"){
	                        goodsController.picture=false;
                        }
                    }
                })
        },        
        //修改并保存
        Sava:function(){            
            var prdocut = $(".prdocut").val();
            var description = $(".description").val();
            var unitPrice = $(".unitPrice").val();
            var shopList = $(".shopList").val();
            var shopType = $(".shopType").val();           
            
            if(goodsController.goodsChg.typeId == "1"){    	
            	
            	if(goodsController.imgViewSrc=="/admin/resources/images/photos/upload1.png" && goodsController.goodsChg.id == ""){
    				alert("请选择图片");
    				return;
    			}
            	else if(goodsController.goodsChg.mainPicId == ""){
    				alert("请提交图片");
    				return;
    			}
            	else if(prdocut == ""){
    				alert("产品名称不能为空");
    				return;
    			}else if(unitPrice == ""){
    				alert("产品单价不能为空");
    				return;
    			}else if(shopList ==""){
    				alert("请选择正确的商店名称");
    				return;
    			}else{				
    				 var date={
    		                    name:goodsController.goodsChg.name,
    		                    code:goodsController.goodsChg.code,
    		                    description:goodsController.goodsChg.description,
    		                    mainPicId:goodsController.goodsChg.mainPicId,
    		                    typeId:parseInt(goodsController.goodsChg.typeId),
    		                    shopId:goodsController.goodsChg.shopId,
    		                    unitPrice:goodsController.goodsChg.unitPrice,
    		                    id:goodsController.goodsChg.id
    		                }
    		            $.ajax({
    		                type: "post",
    		                url: '/admin/product/',
    		                data:date,
    		                success: function(data){
    		                    console.log(data);
    		                    alert('提交成功');
    		                    goodsController.goodsShow = false;
    		                    goodsController.goods = true;
    		                    goodsController.goodsChg.mainPicId = '';                    
    		                    goodsController.goodsChg.shopId="";
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
            	}else if(goodsController.goodsChg.typeId == "2"){
            		
            		if(prdocut == ""){
        				alert("产品名称不能为空");
        				return;
        			}else if(unitPrice == ""){
        				alert("产品单价不能为空");
        				return;
        			}else if(shopList ==""){
        				alert("请选择正确的商店名称");
        				return;
        			}else if(shopType ==""){
        				alert("请选择正确的类型");
        				return;
        			}else{				
        				 var date={
        		                    name:goodsController.goodsChg.name,
        		                    code:goodsController.goodsChg.code,
        		                    description:goodsController.goodsChg.description,
        		                    mainPicId:goodsController.goodsChg.mainPicId,
        		                    typeId:parseInt(goodsController.goodsChg.typeId),
        		                    shopId:goodsController.goodsChg.shopId,
        		                    unitPrice:goodsController.goodsChg.unitPrice,
        		                    id:goodsController.goodsChg.id
        		                }
        		            $.ajax({
        		                type: "post",
        		                url: '/admin/product/',
        		                data:date,
        		                success: function(data){
        		                    console.log(data);
        		                    alert('提交成功');
        		                    goodsController.goodsShow = false;
        		                    goodsController.goods = true;
        		                    goodsController.goodsChg.mainPicId = '';                    
        		                    goodsController.goodsChg.shopId="";
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
        //新增
        add:function(){        	
            goodsController.goodsShow=true;
            goodsController.goods=false;
            goodsController.demand=false;
            goodsController.nothing=false;
            goodsController.picture=false;
            goodsController.shop=false;
            goodsController.imgViewSrc = '/admin/resources/images/photos/upload1.png';
            $("#uploadFileIpt").val('');  
    		goodsController.uploadbtn = true;
            goodsController.goodsChg = {
            		id:"",
                name:"",
                code:"",
                description:"",
                mainPicId:"",
                typeId:"",
                shopId:"",
                shopName:"",
                unitPrice:"" 
            }            
        },
        chooseType:function(){
        	if(goodsController.goodsChg.typeId == "2"){
        		goodsController.picture=false;
        		goodsController.shop=true;
        	}else if(goodsController.goodsChg.typeId == "1"){        		
        		goodsController.shop=true;
        		goodsController.picture=true;
        	}
        },
        deletedGoods:function(id){
            if(confirm("确认删除 ？")) {
                $.ajax({
                    type: "put",
                    url: '/admin/product/delete/' + id,
                    success: function(data){
                        console.log(data);
                        goodsController.goods=true;
                        Submit();                        
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
        hideNewShop:function(){
        	goodsController.goodsShow = false;
        	goodsController.demand = true;
        	goodsController.goods = false;
        	goodsController.nothing = false;
        	goodsController.goodsChg.mainPicId = '';
        	goodsController.goodsChg.name="";
        	goodsController.goodsChg.shopId="";
            goodsController.imgViewSrc = '/admin/resources/images/photos/upload1.png';
            $("#uploadFileIpt").val('');           
            
	    },
        chooseShopItem:function(data){
            goodsController.goodsChg.shopId = data.id;
            goodsController.goodsChg.shopName = data.name;
            $(".in").click();
            
        },
        chioseImgs:function(){
            $("#uploadFileIpt").click();
        },
        uploadbtn:true,
        uploadImg:function(){
	    	  var formData = new FormData($("#uploadForm")[0]);  
     	     $.ajax({  
     	          url: 'admin/upload/file' ,  
     	          type: 'POST',  
     	          data: formData,  
     	          async: false,  
     	          cache: false,  
     	          contentType: false,  
     	          processData: false,  
     	          success: function (data) {  
     	        	  alert("提交成功");
     	        	  goodsController.goodsChg.mainPicId = data.result.id;
     	     		  goodsController.uploadbtn = false;
     	          },  
     	          error: function (data) {  
     	        	  console.log(data);  
     	          }  
     	     });  
	    },
    });


    $("#uploadFileIpt").live("change",function(){
        if (!this.files.length) return;

        var files = Array.prototype.slice.call(this.files);

        if (files.length > 1) {
            alert("一次只能上传一张图片");
            return;
        }
        files.forEach(function(file, i) {
            if (!/\/(?:jpeg|png|gif)/i.test(file.type)) return;
            var reader = new FileReader();
            var li = document.createElement("li");
//          获取图片大小
            var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
            console.log("图片原始大小：" + size);

            reader.onload = function() {        	
                var result = this.result;
                var img = new Image();
                img.src = result;
                goodsController.imgViewSrc = result;
                goodsController.goodsChg.mainPicId = '';
            };

            reader.readAsDataURL(file);
        });
    })


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
 	                 shopId:goodsController.goodsChg.shopId
 	             }
 	    	 
 	    	 
             $.ajax({
                 type: "post",
                 url: '/admin/product/search',
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

});

