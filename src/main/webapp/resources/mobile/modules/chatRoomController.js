/**
 * Created by lzd on 2016.
 */

define(['jquery','avalon', 'text!./chatRoom.html','socket','swiper',"wxsdk","domReady!",'qqface'], function ($,avalon,_chatRoom,socket,swiper,wx,domReady) {
    avalon.templateCache._chatRoom = _chatRoom;


    var main_socket = socket;


    var mainController = avalon.define({
        $id : "mainController",
        userInfo: {},
        pluginShow : false, // 显示功能区标记
        emojiShow : false, // 显示emoji标记
        popshow : false, // 显示弹出层标记
        popSendCtnShow : false, // 显示发送面板标记
        tabShowFlag : false, // tab显示切换 false = 霸屏； true = 打赏；
        isShowPhoto: false, //显示照片墙
        senttext:'', // 输入框文字
        sreenShow:false, // 霸屏显示
        forHerShow:false, // 为他霸屏
        sreenGender:'',// 性别
        sreenName:'',// 昵称
        sreenHead:'',// 头像
        sreenShowSeconds:0, // 倒计时
        sreenImg:'/resources/static/img/delate/photo1.jpg', // 霸屏图片
        sreenMsg:'', // 霸屏文字
        sreenTime:'', // 霸屏时间
        sreenItem:[], //霸屏队列
        showUser:{},
        payMsgTypes:[], // 付费消息类型
        payGoodsTypes:[],// 付费商品类型
        goodsType:{},// 所选择的赠送商品对象
        msgList:[],
        payCount:0, // 支付金额
        pMsgCount: '',//私聊消息
        tipsShow:false, // tip show
        tipsMsg:'', // tip
        chatterList:[], // 在线用户列表
        toChatter:'', //赠送对象id
        topRicher:{}, // 今日土豪
        togglePlugin : function(){ // 显示功能区 按钮
            mainController.emojiShow = false;
            mainController.pluginShow = !mainController.pluginShow;
        },
        toggleEmoji : function(){ // 显示emoji 按钮
            mainController.pluginShow = false;
            mainController.emojiShow = !mainController.emojiShow;
        },
        showPopSend:function(){ // 显示发送提交面板
            mainController.goodsType = {};
            mainController.msgType = '0';
            mainController.tabShowFlag = false;

            mainController.popshow = true;
            setTimeout(function(){
                mainController.popSendCtnShow = true;
            },10)
        },
        stopPropagation:function(e){
            e.stopPropagation();
        },
        stopPreventDefault:function(e){
            e.preventDefault();
        },
        closeAllPop: function (e) {
            e.preventDefault();
            if(mainController.popSendCtnShow){
                mainController.popSendCtnShow = false;
                setTimeout(function(){
                    mainController.popshow = false;
                },300);
            }
        },
        hidePopSend:function(){
            mainController.popSendCtnShow = false;
            setTimeout(function(){
                mainController.popshow = false;
            },300);
        },
        tabChange:function(type){
            if(type == 1){
                mainController.goodsType = {};
                mainController.tabShowFlag = false;
            }else{
                mainController.msgType = '0';
                mainController.tabShowFlag = true;
            }
            mainController.payCount = 0;
        },
        richerList:function(){
            window.location.href='#!/richer';
        },
        showPhotoWall:function(user){
            if(mainController.isShowPhoto){
                $(".pop-photoWall").css('display','none');
            	mainController.photoLists = [];
                mainController.isShowPhoto = false;
                setTimeout(function(){
                    mainController.popshow = false;
                },300)
            }else{
                mainController.showUser = user.$model; // 记录点击用户信息
                mainController.popshow = true;
                $(".pop-photoWall").css('display','block');
                setTimeout(function(){
                    showPhoto(user.$model.headImg,user.$model.id);
                },100)
            }
        },
        userList:function(){
            window.location.hash='#!/chatter';
        },
        privateChat:function(){
            mainController.showPhotoWall();
            avalon.vmodels.rootController.toWho = mainController.showUser;
            window.location.hash='#!/privacyChat'
        },
        forHer:function(){
            if(mainController.isShowPhoto){
                mainController.showPhotoWall();
            }
            mainController.forHerShow = true;
        },
        hideForOther:function(){
            avalon.vmodels.rootController.toWho = {};
            mainController.forHerShow = false;
        },
        msgType: '0',//霸屏消息类型
        msgText:'', //文字内容
        imgUrl:'', //发送图片 base64
        pageDownBtn: false,
        sendMsg:function(){
            var productsId = '';
            if(!mainController.tabShowFlag){
                if(mainController.msgType == ''){
                    alert('请选择霸屏时间');
                    return;
                }
                if(mainController.msgText == '' && mainController.imgUrl == ''){
                    alert('请输入文字或者添加图片')
                    return;
                }
                productsId = mainController.msgType;
            }else{
                if(isEmptyObject(mainController.goodsType.$model)){
                    alert('请选择礼物');
                    return;
                }
                if(mainController.toChatter == ''){
                    alert('请选择赠送对象');
                    return;
                }
                mainController.msgText = '';
                mainController.imgUrl = '';
                productsId = mainController.goodsType.id;
            }

            var content = {
                text:mainController.msgText,
                pic:mainController.imgUrl,
            };
            function sliceContent(content){
                var strings = JSON.stringify(content);
                var sLength = strings.length;
                var size = 2000;
                var mod = sLength%size;
                var l = Math.ceil(sLength/size);
                var blocks = new Array();
                for(var i = 0;i<l;i++){
                    blocks[i] = {
                        block:strings.substr(size*i, size),
                        seq:i
                    }
                }
                return blocks;
            }

            var blocks = sliceContent(content);
            var msgId = ++avalon.vmodels.rootController._msgIndex;
            for(var i = 0;i<blocks.length;i++){
                var msg = undefined;
                var type = 'msgBlock';
                if (i==0) {
                    type = 'msg';
                    msg = {
                        msgId:msgId,
                        to:'',
                        blockSize:blocks.length,
                        productId:productsId,
                        block:blocks[0],
                    };
                    console.log(msg);
                }else{
                    msg = {
                        msgId:msgId,
                        block:blocks[i],
                    }
                }
                var msgContent = type + "\n" + JSON.stringify(msg);
                main_socket.send(msgContent);
                mainController.imgUrl = '';
            }

            
            // 判断是否为付费类型 如果是：拦截，并发起订单请求；等待回调参数，以发起支付请求
            if(productsId != 0){
            	alert("发送支付消息");
            	avalon.vmodels.rootController._loading = true;
            }else{
            	initView();
            }
        }
        ,
        imgViewSrc:'/resources/static/img/photo.png',
        fileChange:function(e){
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
                var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
                reader.onload = function() {
                    var result = this.result;
                    var img = new Image();
                    img.src = result;
                    mainController.imgViewSrc = result;
                    //如果图片大小小于200kb，则直接上传
                    if (result.length <= 200 * 1024) {
                        img = null;
                        mainController.imgUrl = result;
                        return;
                    }
//                  图片加载完毕之后进行压缩，然后上传
                    if (img.complete) {
                        callback();
                    } else {
                        img.onload = callback;
                    }
                    function callback() {
                        var data = compress(img);
                        mainController.imgUrl = data;
                        img = null;
                    }
                };
                reader.readAsDataURL(file);
            });
        },
        clearImg:function(){
            mainController.imgUrl = '';
            mainController.imgViewSrc = '/resources/static/img/photo.png';
            $("#photoInput").val('');
        }
        ,
        choiceGoods:function(id){
            mainController.payGoodsTypes.forEach(function(item){
                if(item.id == id){
                    mainController.goodsType = item;
                    mainController.payCount = item.unitPrice;
                }
            })
        },
        pageDown:function(){
        	$(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() - $(".msg-block-contain").height() + 100},600,'swing');
    		mainController.pageDownBtn = false;
        },
        queryHistoryInfo:{
        	 withChatterId:'', 
             minId:'',
             maxId:-1,
             pageSize:8,
        },
        queryHistoryIng:false,
        photoLists:[], // 照片墙
        showPics:function(msg,url){
        	wx.previewImage({
        	    current: msg, // 当前显示图片的http链接
        	    urls: ['http://www.luoke30.com' + url], // 需要预览的图片http链接列表
        	});
        }
    });


    function initView(){
    	mainController.hidePopSend();
        if(mainController.emojiShow){
            mainController.pluginShow = false;
            mainController.emojiShow = false;
        }
        mainController.payCount = 0;
        mainController.msgText = '';
        mainController.pluginShow = false;
        mainController.imgUrl = '';
        mainController.imgViewSrc = '/resources/static/img/photo.png';
        $("#photoInput").val('');
    }
    
    
    mainController.$watch("msgType", function(t) {
        if(mainController.msgType == ''){
            mainController.payCount == 0;
        }
        mainController.payMsgTypes.forEach(function(item){
            if(mainController.msgType == item.id){
                mainController.payCount = item.unitPrice;
            }
        })
    })



    function isEmptyObject(e) {
        var t;
        for (t in e)
            return !1;
        return !0
    }

   

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

        //alert("图片大于200KB 进行压缩 压缩前：" + initSize + '压缩后：' + ndata.length);

        tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;

        return ndata;
    }

    /*  图片压缩 上传  */





    function init(){
    	
    	mainController.userInfo = avalon.vmodels.rootController._userInfo.$model;
    	console.log(mainController.userInfo)
        mainController.msgList = avalon.vmodels.rootController._publicMsg;

        main_socket = socket;

    	$(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() - $(".msg-block-contain").height() + 100},300,'swing');
    	
        setTimeout(function(){
            $('.emoji-btn').qqFace({
                id : 'facebox', //表情容器的ID
                assign:'sendIpt', //文本框
                path:'/resources/js/qqface/face/',	//表情存放的路径
                container:'faceCtn'
            });
            
            $(".msg-block-contain").scroll(function() {
                var $this =$(this),
                    viewH =$(this).height(),//可见高度
                    contentH =$(this).get(0).scrollHeight,//内容高度
                    scrollTop =$(this).scrollTop();//滚动高度
                if(scrollTop - (contentH - viewH) >= 0){
                    $(this).scrollTop(contentH - viewH - 1);
                    mainController.pageDownBtn = false;
                }
                if(scrollTop <= 0){
                    $(this).scrollTop(1);
                    if(!mainController.queryHistoryIng){
                    	mainController.queryHistoryIng = true;
                    	mainController.queryHistoryInfo.maxId = mainController.msgList[0].id;
                        queryHistoryMsg();
                    }
                }
            });
        },300);
    }
    
    init();

    function queryChatter(){
        var data = {
            pageNumber:1,
            pageSize:100,
            gender:'',
        }
        $.ajax({
            type: "post",
            url: '/m/chat/chatterList',
            data: data,
            success: function(data){
                mainController.chatterList = data.results;
            },
            dataType: 'json',
            error: function() {
                alert("获取信息失败");
            }
        });
    }
    
    queryChatter();

    // 查询消息商品
    function queryMsgGoodsData(){
        $.ajax({
            url: "/m/chat/msgProductList",
            dataType: "JSON",
            async: true,
            type: "get",
            success: function(data) {
                mainController.payMsgTypes = data;
            },
            complete: function() {
            },
            error: function() {
                alert("获取消息类型失败");
            }
        });
    }
    
    queryMsgGoodsData();

    // 查询商品
    function queryGoodsData(){
        $.ajax({
            url: "/m/chat/productList",
            dataType: "JSON",
            async: true,
            type: "get",
            success: function(data) {
                mainController.payGoodsTypes = data;
            },
            complete: function() {
            },
            error: function() {
                alert("获取商品类型失败");
            }
        });
    }
    queryGoodsData();

    function queryHistoryMsg(){

        
        $.ajax({
            url: "/m/chat/listMsg",
            dataType: "JSON",
            data: mainController.queryHistoryInfo,
            async: true,
            type: "post",
            success: function(data) {
                console.log("msg: _____");
                console.log(data);
                var msg = data;
                for(var i = 0;i < msg.length ; i++){
                    msg[i].text = replace_em(msg[i].text);
                    if(msg[i].privacy){
//                    	alert("这里不应该收到私聊消息");
//                        avalon.vmodels.rootController._privacyMsg.unshift(msg[i]);
//                        mainController.pMsgCount = 0;
//                        for(var j = 0; j < avalon.vmodels.rootController._privacyMsg.$model.length; j++){
//                        	if(avalon.vmodels.rootController._privacyMsg.$model[j].command != 'smACK'){
//                                mainController.pMsgCount++;
//                            }
//                        }
                    }else{
                    	avalon.vmodels.rootController._publicMsg.unshift(msg[i]);
                        mainController.msgList.unshift(msg[i]);
                        
                        if($(".msg-block-container").height() - $(".msg-block-contain").height() - $(".msg-block-contain").scrollTop() < 600){
                    		$(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 200},0,'swing');
                    		mainController.pageDownBtn = false;
                    	}else{
                    		mainController.pageDownBtn = true;
                    	}
                        
                    }
                };
                setTimeout(function(){
                    mainController.queryHistoryIng = false;
                },800)
                
            },
            complete: function() {
            	
            },
            error: function() {

            }
        });
    }
    
    queryHistoryMsg();

    // 今日最土豪
    function queryTopRicher(){
        $.ajax({
            url: "/m/chat/richerList",
            dataType: "JSON",
            async: true,
            data:{
                richerType:"DAY",
                pageNumber:1,
                pageSize:1
            },
            type: "POST",
            success: function(data) {
                if(data.results.length == 0){
                    mainController.topRicher = {
                        nickname:' ',
                        headImg:'/resources/static/img/head.jpg',
                        gender:'',
                        payMsgCount:'0',
                    };
                }else{
                    data.results[0].chatterDTO.payMsgCount = data.results[0].payMsgCount;
                    mainController.topRicher = data.results[0].chatterDTO;
                }

            },
            complete: function() {
            },
            error: function() {

            }
        });
    }

    queryTopRicher();

    
    
    // init Swiper
    function showPhoto(pic,id){
    	mainController.photoLists = [];
    	$.ajax({
            url: "/m/chat/chatPicList/" + id + "/1/50",
            dataType: "JSON",
            async: true,
            type: "get",
            success: function(data) {
            	data.unshift({picUrl:pic})
            	mainController.photoLists = data;

                mainController.isShowPhoto = true;
            	setTimeout(function(){
            		var swiper = new Swiper('.swiper-container',{
                        preventClicks : false,
                        preventLinksPropagation : false,
                        touchRatio : 1,
                        lazyLoading : true,
                        observer:true,
                        pagination: '.swiper-pagination',
                        paginationType: 'fraction'
                    });
            	},500)
            	
            	
            },
            complete: function() {
            },
            error: function() {

            }
        });
    	
        
    }


    /* qqface */
    // init QQ face
    setTimeout(function(){
        $('.emoji-btn').qqFace({
            id : 'facebox', //表情容器的ID
            assign:'sendIpt', //文本框
            path:'/resources/js/qqface/face/',	//表情存放的路径
            container:'faceCtn'
        });
    },300);

    // compile QQ faceCode
    function replace_em(str){
        str = str.replace(/\</g,'&lt;');
        str = str.replace(/\>/g,'&gt;');
        str = str.replace(/\n/g,'<br/>');
        str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/js/qqface/face/$1.gif'/>");
        return str;
    };
    /* qqface */


    var textContain = $(".msg-block-contain");
    var textcontainer = $(".msg-block-container");

   
    
    initData();

    
    // 获取初始化数据 滑动到底部
    function initData(){
        var textContain = $(".msg-block-contain");
        var textcontainer = $(".msg-block-container");
        textContain.animate({scrollTop:textcontainer.height() - textContain.height() + 100},500,'swing');
    }


    avalon.scan();

    return chatRoom = {
        init:function(){
            init();
        }
    }


});
