
/**
 * Created by lzd on 2016.
 */

define(['jquery','avalon', 'text!./privacyChat.html','socket','swiper','wxsdk',"domReady!",'qqface'], function ($,avalon,_privacyChat,socket,swiper,wx,domReady) {
    avalon.templateCache._privacyChat = _privacyChat;

    socket.init();
    var privacySocket = socket.ws;
    
        var pChatController = avalon.define({
            $id : "pChatController",
            pluginShow : false, // 显示功能区标记
            emojiShow : false, // 显示emoji标记
            popshow : false, // 显示弹出层标记
            popSendCtnShow : false, // 显示发送面板标记
            tabShowFlag : false, // tab显示切换 false = 霸屏； true = 打赏；
            isShowPhoto: false, //显示照片墙
            senttext:'', // 输入框文字
            sreenShow:false, // 霸屏显示
            forHerShow:false, // 为他霸屏
            toWho:{},
            sreenShowSeconds:0, // 倒计时
            sreenImg:'/resources/static/img/delate/photo1.jpg', // 霸屏图片
            sreenMsg:'', // 霸屏文字
            sreenTime:'', // 霸屏时间
            pMsgList:[],
            photoLists:[],
            userInfo:{
            	id:''
            },
            togglePlugin : function(){ // 显示功能区 按钮
                pChatController.emojiShow = false;
                pChatController.pluginShow = !pChatController.pluginShow;
            },
            toggleEmoji : function(){ // 显示emoji 按钮
                pChatController.pluginShow = false;
                pChatController.emojiShow = !pChatController.emojiShow;
            },
            showPopSend:function(){ // 显示发送提交面板
                pChatController.popshow = true;
                setTimeout(function(){
                    pChatController.popSendCtnShow = true;
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
                if(pChatController.popSendCtnShow){
                    pChatController.popSendCtnShow = false;
                    setTimeout(function(){
                        pChatController.popshow = false;
                    },300);
                }
            },
            hidePopSend:function(){
                pChatController.popSendCtnShow = false;
                setTimeout(function(){
                    pChatController.popshow = false;
                },300);
            },
            tabChange:function(type){
                if(type == 1){
                    pChatController.tabShowFlag = false;
                }else{
                    pChatController.tabShowFlag = true;
                }
            },
            showPhotoWall:function(user){
            	if(pChatController.isShowPhoto){
                    $(".pop-photoWall").css('display','none');
                    pChatController.photoLists = [];
                    pChatController.isShowPhoto = false;
                    setTimeout(function(){
                    	pChatController.popshow = false;
                    },300)
                }else{
                	pChatController.showUser = user.$model; // 记录点击用户信息
                	pChatController.popshow = true;
                    $(".pop-photoWall").css('display','block');
                    setTimeout(function(){
                        showPhoto(user.$model.headImg,user.$model.id);
                    },100)
                }
            },
            msgText:'', //文字内容
            imgUrl:'', //发送图片 base64
            sendMsg:function(){
            	
            	if(pChatController.msgText == '' && pChatController.imgUrl == ''){
                    alert('请输入文字或者添加图片')
                    return;
                }
            	
            	var content = {
                        text:pChatController.msgText,
                        pic:pChatController.imgUrl,
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

                    var blocks = sliceContent(content)
                    var msgId = ++avalon.vmodels.rootController._msgIndex;
                    for(var i = 0;i<blocks.length;i++){
                        var msg = undefined;
                        var type = 'msgBlock';
                        if (i==0) {
                            type = 'msg';
                            msg = {
                                msgId:msgId,
                                to:pChatController.toWho.id,
                                blockSize:blocks.length,
                                productId:'',
                                block:blocks[0],
                            };
                        }else{
                            msg = {
                                msgId:msgId,
                                block:blocks[i],
                            }
                        }
                        var msgContent = type+"\n" + JSON.stringify(msg);
                        privacySocket.send(msgContent);
                    }

                pChatController.hidePopSend();
                if(pChatController.emojiShow){
                    pChatController.pluginShow = false;
                    pChatController.emojiShow = false;
                }

                pChatController.msgType = '0';
                pChatController.msgText = '';
                pChatController.pluginShow = false;
                pChatController.imgUrl = '';
                pChatController.imgViewSrc = '/resources/static/img/photo.png';
                $("#pPhotoInput").val('');
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
                    var orien = 6;
                    EXIF.getData(file, function() {
                      orien = EXIF.getTag(this, "Orientation"); 
                    });
                    var reader = new FileReader();
                    var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
                    reader.onload = function() {
                        var result = this.result;
                        var img = new Image();
                        img.src = result;
                        img.onload = callback;
                        function callback() {
                            var data = compress(img, orien);
                            
                            var img2 = new Image();
                            img2.src = data;
                            img2.onload = callback2;
                            function callback2() {
                              var result = rotate(img2, orien);
                              pChatController.imgViewSrc = result;
                              pChatController.imgUrl = result;
                            }
                            img = null;
                        }
                    };
                    reader.readAsDataURL(file);
                });
            },
            pClearImg:function(){
            	pChatController.imgUrl = '';
            	pChatController.imgViewSrc = '/resources/static/img/photo.png';
                $("#pPhotoInput").val('');
            },
            queryHistoryInfo:{
           	    withChatterId:'',
                minId:'',
                maxId:-1,
                pageSize:20,
           },
           queryHistoryIng:false,
           showPics:function(msg,url){
	           	wx.previewImage({
	           	    current: msg, // 当前显示图片的http链接
	           	    urls: ['http://www.luoke30.com' + url], // 需要预览的图片http链接列表
	           	});
           },
        });

        avalon.scan();

        function queryHistoryMsg(){
            
            $.ajax({
                url: "/m/chat/listMsg",
                dataType: "JSON",
                data: pChatController.queryHistoryInfo,
                async: true,
                type: "post",
                success: function(data) {
                    var msg = data;
                    for(var i = 0;i < msg.length ; i++){
                        msg[i].text = replace_em(msg[i].text);
                        if(msg[i].privacy){           
                        	pChatController.pMsgList.unshift(msg[i]);
                        }else{
//                        	alert('这里不应该查询到公共消息');  
                        	return;
                        }
                    } 

               	    $(".msg-block-contain").animate({scrollTop:$(".msg-block-container").height() -  $(".msg-block-contain").height() + 100},300,'swing');
                
                },
                complete: function() {
                	
                },
                error: function() {

                }
            });
        }
        
        
        function init(){
        	pChatController.pMsgList = [];//清空私聊消息列表
        	
        	privacySocket = socket.ws;
        	
        	pChatController.toWho = avalon.vmodels.rootController.toWho;// 保存toId
        	console.log(pChatController.toWho.$model);
        	pChatController.queryHistoryInfo.withChatterId = pChatController.toWho.$model.id;
            /* qqface */
            setTimeout(function(){
                $('.emoji-btn').qqFace({
                    id : 'facebox', //表情容器的ID
                    assign:'sendIpt', //文本框
                    path:'/resources/js/qqface/face/',	//表情存放的路径
                    container:'faceCtn'
                });
               
            },300);
            queryHistoryMsg();
        }
        
        init();

       


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




        $(".msg-block-contain").scroll(function(e){
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
        });




        // init Swiper
        function showPhoto(pic,id){
        	pChatController.photoLists = [];
        	$.ajax({
                url: "/m/chat/chatPicList/" + id + "/1/50",
                dataType: "JSON",
                async: true,
                type: "get",
                success: function(data) {
                	data.unshift({picUrl:pic})
                	pChatController.photoLists = data;

                	pChatController.isShowPhoto = true;
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
        
        
        
        
        
        /*  图片压缩 上传 */
        //    用于压缩图片的canvas
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext('2d');

        //    瓦片canvas
        var tCanvas = document.createElement("canvas");
        var tctx = tCanvas.getContext("2d");

        //    使用canvas对大图片进行压缩
        function compress(img, orien) {
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

//            铺底色
            ctx.fillStyle = "#fff";
            ctx.fillRect(0, 0, canvas.width, canvas.height);

            //如果图片像素大于100万则使用瓦片绘制
            var count;
            if ((count = width * height / 1000000) > 1) {
                count = ~~(Math.sqrt(count) + 1); //计算要分成多少块瓦片

//                计算每块瓦片的宽和高
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
            ndata = canvas.toDataURL('image/jpeg', 0.1);
            //压缩之后的src
            tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;
            
            return ndata;
        }
        
        function rotate(img, orien) {
          var width = img.width;
          var height = img.height;
          canvas.width = width;
          canvas.height = height;
          var ag = 0;
          if (orien == 6) {
            ag = 90;
            canvas.width = height;
            canvas.height = width;
          } else if (orien == 3) {
            ag = 180;
            canvas.width = width;
            canvas.height = height;
          } else if (orien == 8) {
            ag = 270;
            canvas.width = height;
            canvas.height = width;
          } 
//          ag = 90;
//          alert('angle: '+ag);
//          alert('width: '+ canvas.width+', height: '+canvas.height)
          //进行最小压缩
          var ndata = null;
          var xpos = canvas.width/2;
          var ypos = canvas.height/2;

          if (ag!=0) {
            ctx.translate(xpos, ypos);
            ctx.rotate(ag*Math.PI/180);
            ctx.translate(-xpos, -ypos);
//            alert("sx:"+(xpos - width / 2)+" sy:"+ (ypos - height / 2));8585
            ctx.drawImage(img, xpos - width / 2, ypos - height / 2);
          } else {
            ctx.drawImage(img, 0, 0, width, height);
          }
          ndata = canvas.toDataURL('image/jpeg', 0.1);
          tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;
          return ndata;
        }

        /*  图片压缩 上传  */
        

        $(".msg-block-contain").scroll(function(e){
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
        });

        return privacyChat = {
            'init':function(){
                init();
            },
        }
});