/**
 * Created by lzd on 2016.
 */

define(['jquery','avalon', 'text!./chatRoom.html','socket','swiper',"domReady!",'qqface'], function ($,avalon,_chatRoom,socket,swiper,domReady) {
    avalon.templateCache._chatRoom = _chatRoom;

    
    var _userIofo = { //用户信息待定
    	id:1
    };
    
    
    var main_socket = socket;
//    main_socket.onmessage = function(message) {
//        var msg = JSON.parse(message.data);
//        console.log("get masage:");
//        console.log(msg);
//        msg.text = replace_em(msg.text);
//        
//        if(!msg.privacy){
//        	alert(avalon.vmodels.rootController.privacyMsg.length)
//        	avalon.vmodels.rootController.privacyMsg.push(msg);
//        	mainController.pMsgCount = avalon.vmodels.rootController.privacyMsg.length;
//        	return;
//        }
//        
//        mainController.msgList.push(msg);
//
//        $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 100},500,'swing');

//        var seconds = msg.msgType*1000;
//        if(seconds != 0){
//            mainController.sreenShow = true;
//            mainController.sreenImg = text.imgUrl;
//            mainController.sreenMsg = text.msg;
//            mainController.sreenTime = text.msgType;
//            mainController.sreenShowSeconds = seconds/1000;
//            var fl = '';
//            fl = setInterval(function(){
//                mainController.sreenShowSeconds--;
//                if(mainController.sreenShowSeconds == 0){
//                    clearTimeout(fl);
//                    mainController.sreenImg = '';
//                    mainController.sreenMsg = '';
//                    mainController.sreenTime = '';
//                    mainController.sreenShow = false;
//                    return
//                }
//            },1000)
//        }
//    }



    var mainController = avalon.define({
        $id : "mainController",
        userInfo:{
        	id:1
        },
        pluginShow : false, // 显示功能区标记
        emojiShow : false, // 显示emoji标记
        popshow : false, // 显示弹出层标记
        popSendCtnShow : false, // 显示发送面板标记
        tabShowFlag : false, // tab显示切换 false = 霸屏； true = 打赏；
        isShowPhoto: false, //显示照片墙
        senttext:'', // 输入框文字
        sreenShow:false, // 霸屏显示
        forHerShow:false, // 为他霸屏
        sreenShowSeconds:0, // 倒计时
        sreenImg:'/resources/static/img/delate/photo1.jpg', // 霸屏图片
        sreenMsg:'', // 霸屏文字
        sreenTime:'', // 霸屏时间
        showUser:{},
        msgList:[],
        pMsgCount: '',//私聊消息
        tipsShow:false, // tip show
        tipsMsg:'', // tip
        togglePlugin : function(){ // 显示功能区 按钮
            mainController.emojiShow = false;
            mainController.pluginShow = !mainController.pluginShow;
        },
        toggleEmoji : function(){ // 显示emoji 按钮
            mainController.pluginShow = false;
            mainController.emojiShow = !mainController.emojiShow;
        },
        showPopSend:function(){ // 显示发送提交面板
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
                mainController.tabShowFlag = false;
            }else{
                mainController.tabShowFlag = true;
            }
        },
        richerList:function(){
            window.location.href='#!/richer';
        },
        showPhotoWall:function(user){
            if(mainController.isShowPhoto){
                $(".pop-photoWall").css('display','none');
                mainController.isShowPhoto = false;
                setTimeout(function(){
                    mainController.popshow = false;
                },300)
            }else{
            	mainController.showUser = user.$model; // 记录点击用户信息
                mainController.popshow = true;
                $(".pop-photoWall").css('display','block');
                setTimeout(function(){
                    mainController.isShowPhoto = true;
                    showPhoto();
                },10)
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
        forHer:function(id){
            if(mainController.isShowPhoto){
                mainController.showPhotoWall();
            }
            mainController.forHerShow = true;
        },
        hideForOther:function(){
            mainController.forHerShow = false;
        },
        msgType: '0',//霸屏消息类型
        msgText:'', //文字内容
        imgUrl:'', //发送图片 base64
        //forWho:'', //赠送对象
        sendMsg:function(){
            if(mainController.msgType == ''){
                alert('请选择霸屏时间');
            }else{
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
                for(var i = 0;i<blocks.length;i++){
                    var msg = undefined;
                    var type = 'msgBlock';
                    if (i==0) {
                        type = 'msg';
                        msg = {
                            msgId:1,
                            to:'',
                            blockSize:blocks.length,
                            productId:'',
                            block:blocks[0],
                        };
                    }else{
                        msg = {
                            msgId:1,
                            block:blocks[i],
                        }
                    }
                    var msgContent = type+"\n" + JSON.stringify(msg);
                    main_socket.send(msgContent);
                }

                mainController.hidePopSend();
                if(mainController.emojiShow){
                    mainController.pluginShow = false;
                    mainController.emojiShow = false;
                }

                mainController.msgText = '';
                mainController.pluginShow = false;
                mainController.imgUrl = '';
                mainController.imgViewSrc = '/resources/static/img/photo.png';
                $("#photoInput").val('');
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
        	alert(1)
            mainController.imgUrl = '';
            mainController.imgViewSrc = '/resources/static/img/photo.png';
            $("#photoInput").val('');
        }
    });
    avalon.scan();
    
    
    
    mainController.$watch("msgList", function(v) {
      $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 100},500,'swing');
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

        alert("图片大于200KB 进行压缩 压缩前：" + initSize + '压缩后：' + ndata.length);

        tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;

        return ndata;
    }

    /*  图片压缩 上传  */





    function init(){
    	mainController.msgList = avalon.vmodels.rootController._publicMsg;
    	
    	
    	main_socket = socket;
//    	main_socket.onmessage = function(message) {
//            var msg = JSON.parse(message.data);
//            console.log("get masage:");
//            console.log(msg);
//            msg.text = replace_em(msg.text);
//            
//            if(!msg.privacy){
//            	avalon.vmodels.rootController.privacyMsg.push(msg);
//            	mainController.pMsgCount = avalon.vmodels.rootController.privacyMsg.length;
//            	return;
//            }
//            
//            
//            mainController.msgList.push(msg);
//
//            $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 100},500,'swing');

//            var seconds = msg.msgType*1000;
//            if(seconds != 0){
//                mainController.sreenShow = true;
//                mainController.sreenImg = text.imgUrl;
//                mainController.sreenMsg = text.msg;
//                mainController.sreenTime = text.msgType;
//                mainController.sreenShowSeconds = seconds/1000;
//                var fl = '';
//                fl = setInterval(function(){
//                    mainController.sreenShowSeconds--;
//                    if(mainController.sreenShowSeconds == 0){
//                        clearTimeout(fl);
//                        mainController.sreenImg = '';
//                        mainController.sreenMsg = '';
//                        mainController.sreenTime = '';
//                        mainController.sreenShow = false;
//                        return
//                    }
//                },1000)
//            }
//        }
    	
        setTimeout(function(){
            $('.emoji-btn').qqFace({
                id : 'facebox', //表情容器的ID
                assign:'sendIpt', //文本框
                path:'/resources/js/qqface/face/',	//表情存放的路径
                container:'faceCtn'
            });
        },300);
    }
    init();

    // 查询消息商品
    function queryMsgGoodsData(){
      	 $.ajax({
              url: "/m/chat/msgProductList",   
              dataType: "JSON",  
              async: true,
              type: "get",
              success: function(data) {
              	   console.log(data);
              },
              complete: function() {
              },
              error: function() {
                  
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
             	   console.log(data);
             },
             complete: function() {
             },
             error: function() {
                 
             }
         });
     }
    queryGoodsData();
    
    
    function queryHistoryMsg(){
    	 $.ajax({
            url: "/m/chat/listMsg",   
            dataType: "JSON", 
            data:{
            	withChatterId:'', // 私聊对象id
            	minId:'',
            	maxId:'',
            	pageSize:20,
            },
            async: true,
            type: "post",
            success: function(data) {
            	   console.log("msg: ___________");
            	   console.log(data);
            },
            complete: function() {
            },
            error: function() {
                
            }
        });
    }
    queryHistoryMsg();
    
    
    

    // init Swiper
    function showPhoto(){
        var swiper = new Swiper('.swiper-container',{
            preventClicks : false,
            preventLinksPropagation : false,
            touchRatio : 1,
            lazyLoading : true,
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
    
    $(".msg-block-contain").bind('scroll',function(){
    	var $this =$(this),
        viewH =$(this).height(),//可见高度
        contentH =$(this).get(0).scrollHeight,//内容高度
        scrollTop =$(this).scrollTop();//滚动高度
        if(scrollTop - (contentH - viewH) == 0){
            $(this).scrollTop(contentH - viewH - 1);
        }
        if(scrollTop == 0){
            $(this).scrollTop(1);
        }
    })
    

    initData();

    // 获取初始化数据 滑动到底部
    function initData(){
        var textContain = $(".msg-block-contain");
        var textcontainer = $(".msg-block-container");
        textContain.animate({scrollTop:textcontainer.height() - textContain.height() + 100},500,'swing');
    }




    return chatRoom = {
        init:function(){
            init();
        }
    }


});
