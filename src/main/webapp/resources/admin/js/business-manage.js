/**
 * Created by Luozhongdao  on 2016/9/19 0018.
 */
define(['uploadfile'],function(){
    $(function(){
    	var shopController = avalon.define({
    	    $id: "shopController",
    	    formShow:false,
    	    business:true,
    	    businessalter:false,
    	    nothing:false,
    	    disabled:true,
    	    reset:false,
    	    managename:true,
    	    imgViewSrc:'/admin/resources/images/photos/upload1.png',
    	    shopList:[],    	    
    	    totlePage:-1,
    	    queryCdt:{
    	    	name:'',
    	    	deleted:false,
    	    	pageNumber:1,
    	    	pageSize:7,
    	    }, 
    	    formData:{
    	    	name:'',
    	    	address:'',
//    	    	chatRoomName:'',
    	    	description:'',
    	    	latitude:'',
    	    	longitude:'',
    	    	managerName:'',
    	    	managerProfileId:'',
    	    	description:'',
    	    	picId:'', 
    	    	managerPwd:'',
    	    	id:""	
    	    },
    	    uploadbtn:true,
    	    Alter:function(){
    	    	shopController.business=false;
    	    	shopController.businessalter=true;    	  
	            shopController.uploadbtn = true;
    	    },
    	    save:function(){
    	    	shopController.business=true;
    	    	shopController.businessalter=false;
    	    },
    	    queryData:function(){
    	    	shopController.queryCdt.pageNumber=1;
    	    	var data = shopController.queryCdt; 
    	    	
    	    	$.ajax({
    	      		  type: "post",
    	      		  url: '/admin/shop/search/',
    	      		  data:data,
    	      		  success: function(data){
    	      			  shopController.totlePage = data.totalPageCount;
    	      			  shopController.shopList = data.results;
    	      			  console.log(shopController.shopList = data.results);
    	      			if(shopController.shopList.length != 0){
    	      				shopController.nothing=false;	   
      	      			}else if(shopController.shopList.length == 0){  	      				
      	      				shopController.nothing=true;
      	      			}
    	      		  },
    	      		  dataType: 'json'
    	      		});
    	    },
    	    newShop:function(){
    	    	shopController.managename = true;
    	    	shopController.reset = false;
    	    	shopController.formShow = true;
    	    	shopController.nothing=false;
    	    	shopController.uploadbtn=true;
    	    	shopController.formData = {
    	    			name:'',
    	    	    	address:'',
//    	    	    	chatRoomName:'',
    	    	    	description:'',
    	    	    	latitude:'',
    	    	    	longitude:'',
    	    	    	managerName:'',
    	    	    	managerProfileId:'',
    	    	    	description:'',
    	        	    picId:'',
    	        	    managerPwd:'',
    	    	}
    	    },
    	    hideNewShop:function(){
    	    	shopController.formShow = false;
    	    	  shopController.formData.picId = '';
	              shopController.uploadbtn = true;
      	    	  shopController.imgViewSrc = '/admin/resources/images/photos/upload1.png';
                  $("#uploadFileIpt").val('');
    	    },
    	    deleteShop:function(id){
    	    	 if(confirm("确认删除 ？")) {
    	    		 $.ajax({
    	  	      		  type: "put",
    	  	      		  url: '/admin/shop/delete/' + id,
    	  	      		  success: function(data){
    	  	      			  console.log(data);
    	  	      			  queryData();
    	  	      		  },
    	  	      		  dataType: 'json'
    	  	      		});
    	    	 }
    	    	 queryData();
    	    },	    
    	 
    	    updateShop:function(id){
//    	    	$(".managerName").attr("readonly","readonly");
//    	    	$(".managerPwd").attr("readonly","readonly");
    	    	shopController.formShow = true;
	            shopController.uploadbtn = true;
	            shopController.managename = false;
	            shopController.reset = true;
    	    	shopController.shopList.forEach(function(item){
    	    		if(item.id == id){
    	    			shopController.formData = item;
    	    			shopController.imgViewSrc = item.picUrl;
    	    		}
    	    	})
    	    },
    	    page:function(page){
    	    	queryData(page);
    	    },
    	    cPwd:function(){
    	    	var shopId=shopController.formData.id ;
   	    		 $.ajax({
   	  	      		  type: "get",
   	  	      		  url: '/admin/profile/reset/' + shopId,
   	  	      		  success: function(data){
   	  	      			  alert("密码已重置，默认为6个8");
   	  	      		  },
   	  	      		  dataType: 'json'
   	  	      		});
    	    },
    	    submitItem:function(){    	    	
    	    	//经度正则
    	    	var longitude =/^[\-\+]?(0?\d{1,2}\.\d{1,5}|1[0-7]?\d{1}\.\d{1,5}|180\.0{1,5})$/;
    	    	var latitude =/^[\-\+]?([0-8]?\d{1}\.\d{1,5}|90\.0{1,5})$/;
    				var tel = $(".longitude").val();
    				var tit = $(".latitude").val();
    				var shopName = $(".shopName").val();
    				var adss =  $(".adss").val();
    				var managerName = $(".managerName").val();
    				var managerPwd = $(".managerPwd").val();
    				if(shopController.imgViewSrc=="/admin/resources/images/photos/upload1.png"){
    					alert("请选择图片");
    					return;
    				}else if(shopController.formData.picId == ''){
    					alert("请上传图片");
    					return;
    				}else if(shopName ==""){
    					alert("请输入正确商店名称");
    					return;
    				}else if(adss ==""){
    					alert("请输入正确地址");
    					return;
    				}else{
    				 	
    	    	    	var data = {
    	    	    			name:shopController.formData.name,
    	    	    	    	address:shopController.formData.address,
//    	    	    	    	chatRoomName:shopController.formData.chatRoomName,
    	    	    	    	description:shopController.formData.description,
    	    	    	    	latitude:shopController.formData.latitude,
    	    	    	    	longitude:shopController.formData.longitude,
    	    	    	    	managerName:shopController.formData.managerName,
    	    	    	    	managerProfileId:shopController.formData.managerProfileId,
    	    	    	    	description:shopController.formData.description,
    	    	    	    	id:shopController.formData.id,
    	    	    	    	picId:shopController.formData.picId,
    	    	    	    	managerPwd:shopController.formData.managerPwd,
    	    	    	}
    	    	    	//性别 -------------------------------------------------------------------------------------------------
//    	    	    	data.managerType = 1;
    	    	    	if(shopController.formData.id){
    	        	    	console.log(data);  	        	    	
    	        	    	$.ajax({
    	      	      		  type: "post",
    	      	      		  url: '/admin/shop/',
    	      	      		  data: data,
    	      	      		  success: function(data){
    	     	      			 alert('提交成功')
    	     	      	    	  shopController.formShow = false;
    		   	      	    	  shopController.formData.picId = '';
    			      	    	  shopController.imgViewSrc = '/admin/resources/images/photos/upload1.png';    			      	    	  
    			                  $("#uploadFileIpt").val('');
    	      	      			  console.log(data);
    	      	      			  queryData();
    	      	      		  },
    	      	      		  dataType: 'json',
    	      	      		});
    	    	    	}else{
    	    	    		$.ajax({
    	        	      		  type: "post",
    	        	      		  url: '/admin/shop/',
    	        	      		  data: data,
    	        	      		  success: function(data){
    	        	      			  alert('提交成功')
    	        	      	    	  shopController.formShow = false;
    	        	      	    	  shopController.formData.picId = '';
    	        	      	    	  shopController.imgViewSrc = '/admin/resources/images/photos/upload1.png';
    	        	                  $("#uploadFileIpt").val('');
    	        	      			  console.log(data);
    	        	      			  queryData();
    	        	      		  },
    	        	      		  dataType: 'json'
    	        	      		});
    	    	    	}
    				}   			
    	   
    	    },
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
            	              shopController.formData.picId = data.result.id;
            	              shopController.uploadbtn = false;
            	          },  
            	          error: function (data) {  
            	        	  console.log(data);  
            	          }  
            	     });  
    	    },
    	    chioseImgs:function(){
    	    	$("#uploadFileIpt").click();
    	    }
    	});
    	
    	avalon.scan();
    	
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
//              获取图片大小
                var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
                console.log("图片原始大小：" + size);

                reader.onload = function() {        	
                    var result = this.result;
                    var img = new Image();
                    img.src = result;
                    shopController.imgViewSrc = result;
                    shopController.formData.picId = '';
                };
                reader.readAsDataURL(file);
            });
    	})
    	
    	
    	function queryData(page){
    		
    		var data = shopController.queryCdt;    	
	    	if(page == "upPage"){
	    		if(shopController.queryCdt.pageNumber == 1){
	    			alert("已是第一页");
	    			return;
	    		}
	    		shopController.queryCdt.pageNumber--;
	    	}else if(page == "nextPage"){
	    		if(shopController.queryCdt.pageNumber == shopController.totlePage){
	    			alert("已是最后一页");
	    			return;
	    		}
	    		shopController.queryCdt.pageNumber++;
	    	}
	    	 
	    	
	    	$.ajax({
	      		  type: "post",
	      		  url: '/admin/shop/search/',
	      		  data:data,
	      		  success: function(data){
	      			  shopController.totlePage = data.totalPageCount;
	      			  shopController.shopList = data.results;
	      			  console.log(shopController.shopList = data.results);
	      			if(shopController.shopList.length != 0){
	      				shopController.nothing=false;	   
  	      			}else if(shopController.shopList.length == 0){  	      				
  	      				shopController.nothing=true;
  	      			}
	      		  },
	      		  dataType: 'json'
	      		});
    	}
    	
    	
    	
    	
    	})
    	
    	
    	 /*  图片压缩 上传 */
        //    用于压缩图片的canvas
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext('2d');

        //    瓦片canvas
        var tCanvas = document.createElement("canvas");
        var tctx = tCanvas.getContext("2d");

        //    使用canvas对大图片进行压缩
        function compress(img) {
            var initSize = img.src.length;
            var width = img.width;
            var height = img.height;

            //如果图片大于四百万像素，计算压缩比并将大小压至400万以下
            var ratio;
            if ((ratio = width * height / 4000000) > 1) {
                ratio = Math.sqrt(ratio);
                width /= ratio;
                height /= ratio;
            } else {
                ratio = 1;
            }

            canvas.width = width;
            canvas.height = height;

//        铺底色
            ctx.fillStyle = "#fff";
            ctx.fillRect(0, 0, canvas.width, canvas.height);

            //如果图片像素大于100万则使用瓦片绘制
            var count;
            if ((count = width * height / 1000000) > 1) {
                count = ~~(Math.sqrt(count) + 1); //计算要分成多少块瓦片

//            计算每块瓦片的宽和高
                var nw = ~~(width / count);
                var nh = ~~(height / count);

                tCanvas.width = nw;
                tCanvas.height = nh;

                for (var i = 0; i < count; i++) {
                    for (var j = 0; j < count; j++) {
                        tctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio, nh * ratio, 0, 0, nw, nh);

                        ctx.drawImage(tCanvas, i * nw, j * nh, nw, nh);
                    }
                }
            } else {
                ctx.drawImage(img, 0, 0, width, height);
            }

            //进行最小压缩
            var ndata = canvas.toDataURL('image/jpeg', 0.1);

            alert('压缩前：' + initSize + '压缩后：' + ndata.length + '压缩率：' + ~~(100 * (initSize - ndata.length) / initSize) + "%" );

            tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;

            return ndata;
        }
        
        
        
    	function initData(){
            console.log("load business-manage");
    	}
    	
    	//initData();
    	
    	return business = {
    			init:initData,
    	}
    	
});