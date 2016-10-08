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
    	    imgViewSrc:'/admin/resources/images/photos/upload1.png',
    	    shopList:[],
    	    queryCdt:{
    	    	name:'',
    	    	deleted:false,
    	    	pageNumber:1,
    	    	pageSize:21,
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
    	    	description:'',
    	    	picId:''
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
    	    	    	description:'',
    	        	    picId:''
    	    	}
    	    },
    	    hideNewShop:function(){
    	    	shopController.formShow = false;
    	    },
    	    deleteShop:function(id){
    	    	 if(confirm("确认删除 ？")) {
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
    	    			shopController.imgViewSrc = item.picUrl;
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
    	    	    	id:shopController.formData.id,
    	    	    	picId:shopController.formData.picId
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
    	    	
    	    },
    	    uploadImg:function(){
    	    	$("#uploadfile").click();
    	    },
    	    imgChange:function(e){
    	    	alert(1)
    	    	
    	    }
    	});
    	avalon.scan();
    	
    	
    	$("#uploadfile").live("change",function(){  
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
                        
                        
                        // 上传图片
                        $.ajaxFileUpload
                        (
                            {
                                url: '/admin/upload/file', //用于文件上传的服务器端请求地址
                                secureuri: false, //是否需要安全协议，一般设置为false
                                fileElementId: 'uploadfile', //文件上传域的ID
                                dataType: 'json', //返回值类型 一般设置为json
                                success: function (data, status)  //服务器成功响应处理函数
                                {
                                    console.log(data);
                                    alert("上传成功了！返回值" + data.result)
                	    			shopController.formData = item;
                	    			shopController.imgViewSrc = item.picUrl;
                                },
                                error: function (data, status, e)//服务器响应失败处理函数
                                {
                                    alert(e);
                                }
                            }
                        )
                        //如果图片大小小于200kb，则直接上传
                        /* if (result.length <= 400 * 1024) {
                            img = null;
                            alert("图片小于400KB 可以直接上传。");
                            upload(result, file.type);

                            return;
                        }

//                      图片加载完毕之后进行压缩，然后上传
                        if (img.complete) {
                            callback();
                        } else {
                            img.onload = callback;
                        }

                        function callback() {
                            alert("图片大于400KB 进行压缩。");
                            var data = compress(img);
                          	upload(data, file.type);

                            img = null;
                        }*/

                    };

                    reader.readAsDataURL(file);
                });
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
    	
//      图片上传，将base64的图片转成二进制对象，塞进formdata上传
        function upload(basestr, type) {
            var text = window.atob(basestr.split(",")[1]);
            var buffer = new Uint8Array(text.length);
            var pecent = 0, loop = null;

            for (var i = 0; i < text.length; i++) {
                buffer[i] = text.charCodeAt(i);
            }

            var blob = getBlob([buffer], type);

            var xhr = new XMLHttpRequest();

            var formdata = getFormData();

            formdata.append('uploadFile', blob);

            xhr.open('post', '/admin/upload/file');

            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                	var data = JSON.parse(xhr.responseText);
                	
                    console.log(JSON.parse(xhr.responseText));
                    alert("上传成功 图片ID：" + data.result)
//                    shopController.formData.picId = '';
                }
            };

            xhr.send(formdata);
        }

        /**
         * 获取blob对象的兼容性写法
         * @param buffer
         * @param format
         * @returns {*}
         */
        function getBlob(buffer, format) {
            try {
                return new Blob(buffer, {type: format});
            } catch (e) {
                var bb = new (window.BlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder);
                buffer.forEach(function(buf) {
                    bb.append(buf);
                });
                return bb.getBlob(format);
            }
        }

        /**
         * 获取formdata
         */
        function getFormData() {
            var isNeedShim = ~navigator.userAgent.indexOf('Android')
                && ~navigator.vendor.indexOf('Google')
                && !~navigator.userAgent.indexOf('Chrome')
                && navigator.userAgent.match(/AppleWebKit\/(\d+)/).pop() <= 534;

            return isNeedShim ? new FormDataShim() : new FormData()
        }

        /**
         * formdata 补丁, 给不支持formdata上传blob的android机打补丁
         * @constructor
         */
        function FormDataShim() {
            console.warn('using formdata shim');

            var o = this,
                parts = [],
                boundary = Array(21).join('-') + (+new Date() * (1e16 * Math.random())).toString(36),
                oldSend = XMLHttpRequest.prototype.send;

            this.append = function(name, value, filename) {
                parts.push('--' + boundary + '\r\nContent-Disposition: form-data; name="' + name + '"');

                if (value instanceof Blob) {
                    parts.push('; filename="' + (filename || 'blob') + '"\r\nContent-Type: ' + value.type + '\r\n\r\n');
                    parts.push(value);
                }
                else {
                    parts.push('\r\n\r\n' + value);
                }
                parts.push('\r\n');
            };

            // Override XHR send()
            XMLHttpRequest.prototype.send = function(val) {
                var fr,
                    data,
                    oXHR = this;

                if (val === o) {
                    // Append the final boundary string
                    parts.push('--' + boundary + '--\r\n');

                    // Create the blob
                    data = getBlob(parts);

                    // Set up and read the blob into an array to be sent
                    fr = new FileReader();
                    fr.onload = function() {
                        oldSend.call(oXHR, fr.result);
                    };
                    fr.onerror = function(err) {
                        throw err;
                    };
                    fr.readAsArrayBuffer(data);

                    // Set the multipart content type and boudary
                    this.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
                    XMLHttpRequest.prototype.send = oldSend;
                }
                else {
                    oldSend.call(this, val);
                }
            };
        }



        /*  图片压缩 上传  */
        console.log("load business-manage");
        
    	function initData(){
    		
    	}
    	
    	//initData();
    	
    	return business = {
    			init:initData,
    	}
    	
    })
});