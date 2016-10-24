/**
 * Created by Luozhongdao  on 2016/9/19 0018.
 */
define(['uploadfile'],function(){	
	$(function(){ 
		ShopDetail();
    	var shopController = avalon.define({
    	    $id: "shopController",
    	    formShow:true,
    	    product:true,
    	    business:true,
    	    businessalter:false,
    	    nothing:false,
    	    disabled:true,
    	    reset:false,
    	    password:false,
    	    imgViewSrc:'/admin/resources/images/photos/upload1.png',
    	    shopDetail:{},
    	    shopList:[],
    	    queryCdt:{
    	    	name:'',
    	    	deleted:false,
    	    	pageNumber:1,
    	    	pageSize:7    	    	
    	    }, 
    	    formData:{
    	    	oldPwd:"",
    	    	newPwd:"",
    	    	againPwd:"",
    	    	id:"",
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
    	    	managerPwd:''
    	    },
    	    uploadbtn:true, 
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
    	    	    	    	description:shopController.formData.description,
    	    	    	    	latitude:shopController.formData.latitude,
    	    	    	    	longitude:shopController.formData.longitude,
//    	    	    	    	managerName:shopController.formData.managerName,
    	    	    	    	managerProfileId:shopController.formData.managerProfileId,
    	    	    	    	description:shopController.formData.description,
    	    	    	    	picId:shopController.formData.picId,
//    	    	    	    	managerPwd:shopController.formData.managerPwd,
    	    	    	}
    	    	    	//性别 -------------------------------------------------------------------------------------------------
    	    	    	$.ajax({
    	        	      		  type: "post",
    	        	      		  url: '/shop/admin/detail/',
    	        	      		  data: data,
    	        	      		  success: function(data){
    	        	      			  alert('提交成功')    	        	      	    	 
    	        	                  $("#uploadFileIpt").val('');
    	        	      			  console.log(data);
    	        	      			  ShopDetail();
    	        	      			shopController.formShow = true;
    	        	    	    	shopController.reset = false;
    	        	      			  
    	        	      		  },
    	        	      		  dataType: 'json'
    	        	      		});
    	    	    
    				}   			
    	   
    	    },
    	    rested:function(){
    	    	shopController.formShow = false;
    	    	shopController.reset = true;
    	    },
    	    back:function(){    	    	
    	    	shopController.formShow = true;
    	    	shopController.reset = false;
    	    	
    	    },
    	    pwd:function(){
    	    	shopController.formShow = false;
    	    	shopController.password = true;
    	    },
    	    make:function(){
    	    	var data={
    	    			oldPwd:shopController.formData.oldPwd,
    	    			newPwd:shopController.formData.newPwd,
    	    			againPwd:shopController.formData.againPwd
    	    	}
    	    	$.ajax({
  	      		  type: "post",
  	      		  url: '/shop/admin/manager/',
  	      		  data: data,
  	      		  success: function(data){
  	      			  console.log(data);
  	      			if(data == null){
  	      				alert('输入的文本框为空');
  	      			} else if(data == 0){
  	      				alert('修改密码成功');
  	      			 }else if(data == 1){
  	      				alert('输入的原密码不匹配');
  	      			 } else if(data == 2){
  	      				alert('两次输入的密码不一致');
  	      			 } 
  	      			window.location.reload();
  	      		  },
  	      		  dataType: 'json'
  	      		});
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
            	        	  console.log(data);
            	              shopController.imgViewSrc= data.result.url;
            	              shopController.formData.picId= data.result.id;
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
    	function ShopDetail(){
    		 
    		    	$.ajax({
    		    		  type: "get",
    		    		  url: '/shop/admin/detail/search',
    		    		  success: function(data){
    		    			  console.log(data);
    		    			   shopController.shopDetail = data;
    		    			   shopController.imgViewSrc = data.picUrl;
    		    			   shopController.formData.name = data.name,
    		    			   shopController.formData.address = data.address,
    		    			   shopController.formData.description = data.description,
    		    			   shopController.formData.latitude=data.latitude,
    		    			   shopController.formData.longitude = data.longitude,
    		    			   shopController.formData.managerName = data.managerName,
    		    			   shopController.formData.managerProfileId =data.managerProfileId,
    		    			   shopController.formData.picId=data.picId
    		    		  },
    		    		  dataType: 'json'
    		    		});
    	}
    	
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
                };
                reader.readAsDataURL(file);
            });
    	}) 
    	});
    	
    	
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
            console.log("load shop");
    	}
    	
    	initData();
    	
    	return shop = {
    			init:initData,
    	}
    	
});