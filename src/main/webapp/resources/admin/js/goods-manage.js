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
        checked:"checked",
        totlePage:-1,
        goodsList:[],
        shopList:[],
        imgViewSrc:'/admin/resources/images/photos/upload1.png',
        goodsDate:{
            name:"",
            deleted:false,
            pageNumber:1,
            pageSize:7,
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
            unitPrice:""
        },
        //查询商品
        btnGoods:function(page){
            goodsController.goods=true;
            goodsController.goodsAdd = false;
            goodsController.goodsShow = false;
            var date = {
                name:goodsController.goodsDate.name,
                deleted:goodsController.goodsDate.deleted,                
                pageNumber:goodsController.goodsDate.pageNumber,
                pageSize:goodsController.goodsDate.pageSize
            }        	   	    	
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

            $.ajax({
                type: "post",
                url: '/admin/product/search',
                data:date,
                success: function(data){
                    console.log(data);
                    goodsController.goodsList = data.results;
                    goodsController.totlePage = data.totalPageCount;
                },
                dataType: 'json'
            });
        },
        Alter:function(id){
            goodsController.goodsShow = true;
            goodsController.goods = false;
            goodsController.demand = false;
            goodsController.goodsList.forEach(function(item){
                if(item.id == id){
                    goodsController.goodsChg = item;
                }
            })

        },
        //修改并保存
        Sava:function(){
            goodsController.goodsShow=false;
            goodsController.demand=true;
            var date={
                name:goodsController.goodsChg.name,
                code:goodsController.goodsChg.code,
                description:goodsController.goodsChg.description,
//     	    			mainPic:goodsController.goodsChg.mainPic,
                mainPicId:goodsController.goodsChg.mainPicId,
                typeId:parseInt(goodsController.goodsChg.typeId),
                shopId:goodsController.goodsChg.shopId,
//     	    			shopName:goodsController.goodsChg.shopName,
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
//                    window.location.reload();
                },
                dataType: 'json',
            });
        },
        //新增
        add:function(){        	
            goodsController.goodsShow=true;
            goodsController.goods=false;
            goodsController.demand=false;            
            goodsController.goodsChg = {
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
        hideNewShop:function(){
        	goodsController.goodsShow = false;
        	goodsController.demand = true;
	    },
        chooseShopItem:function(data){
            goodsController.goodsChg.shopId = data.id;
            goodsController.goodsChg.shopName = data.name;
            $(".in").click();
            
        },
        uploadImg:function(){
            $("#goodsUploadfile").click();
        }
    });


    $("#goodsUploadfile").live("change",function(){
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
                // 上传图片
                $.ajaxFileUpload
                (
                    {
                        url: '/admin/upload/file', //用于文件上传的服务器端请求地址
                        secureuri: false, //是否需要安全协议，一般设置为false
                        fileElementId: 'goodsUploadfile', //文件上传域的ID
                        dataType: 'json', //返回值类型 一般设置为json
                        success: function (data, status)  //服务器成功响应处理函数
                        {
                            console.log(data);
                            alert("上传成功了！返回值" + data.result)
                            goodsController.goodsChg.mainPicId = data.result.id;
//            	    			shopController.formData = item;
//            	    			shopController.imgViewSrc = item.picUrl;
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

                 //                  图片加载完毕之后进行压缩，然后上传
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


    console.log("load goods-manage");
    avalon.scan();

});

