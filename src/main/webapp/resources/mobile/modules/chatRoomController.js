/**
 * Created by lzd on 2016.
 */

define(['jquery','avalon', 'text!./chatRoom.html','swiper',"domReady!",'qqface'], function ($,avalon,_chatRoom,swiper,domReady) {
    avalon.templateCache._chatRoom = _chatRoom;


        var mainController = avalon.define({
            $id : "mainController",
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
            msgList:[ //消息列表
                {name:'老万',msg:'我哈哈哈',sexy:'man',time:'上午 10:50'},
                {name:'jimmy',msg:'我哈哈哈',sexy:'weman',time:'上午 10:50'},
                {name:'jimmy',msg:'我哈哈哈',sexy:'weman',time:'上午 10:50'},
            ],

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
            showPhotoWall:function(){
                if(mainController.isShowPhoto){
                    $(".pop-photoWall").css('display','none');
                    mainController.isShowPhoto = false;
                    setTimeout(function(){
                        mainController.popshow = false;
                    },300)
                }else{
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
//                        console.log(msgContent);
                		var msgContent = type+"\n" + JSON.stringify(msg);
                		console.log("block:"+i+"---"+msgContent.length);
                        socket.send(msgContent);
                	}
                    
                    mainController.hidePopSend();
                    if(mainController.emojiShow){
                        mainController.pluginShow = false;
                        mainController.emojiShow = false;
                    }

                    mainController.msgType = '0';
                    mainController.msgText = '';
                    mainController.pluginShow = false;
                    mainController.imgUrl = '';
                    mainController.imgViewSrc = '/resources/static/img/photo.png';
                }
            }
            ,
            imgViewSrc:'/resources/static/img/photo.png',
            fileChange:function(e){
//                var f = e.files[0];//一次只上传1个文件，其实可以上传多个的
//                var FR = new FileReader();
//                FR.onload = function(f){
//                    var img = this.result;
//                    compressImg(img,600,function(data){//压缩完成后执行的callback
//                        mainController.imgViewSrc = img;
//                        mainController.imgUrl = data;
//                    });
//                };
//                FR.readAsDataURL(f);//先注册onload，再读取文件内容，否则读取内容是空的
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
                    console.log("图片原始大小：" + size);
                    reader.onload = function() {
                        var result = this.result;
                        var img = new Image();
                        img.src = result;
                        mainController.imgViewSrc = result;
                        //如果图片大小小于200kb，则直接上传
                        if (result.length <= 200 * 1024) {
                            img = null;
                            alert("图片小于200KB 可以直接上传。图片原始大小：" + size);
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
                            alert("图片大于200KB 进行压缩。图片原始大小：" + size);
                            var data = compress(img);
                            mainController.imgUrl = imgdata;
                            img = null;
                        }
                    };
                    reader.readAsDataURL(file);
                });
            }
        });
        avalon.scan();
        
        
        /* update file */
//        function compressImg(imgData,maxHeight,onCompress){
//            if(!imgData){
//                alert("请传入图片");
//                return;
//            }
//            onCompress = onCompress || function(){};
//            maxHeight = maxHeight || 300;//默认最大高度300px
//            var canvas = document.createElement('canvas');
//            var img = new Image();
//            img.onload = function(){
//                if(img.height > maxHeight) {//按最大高度等比缩放
//                    img.width *= maxHeight / img.height;
//                    img.height = maxHeight;
//                    console.log(img.width +"h:"+img.height);
//                    canvas.width = img.width;
//                    canvas.height = img.height;
//                }else{
//                    canvas.width = img.width;
//                    canvas.height = img.height;
//                }
//                var ctx = canvas.getContext("2d");
//                ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏
//                //重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
//                ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
//                onCompress(canvas.toDataURL("image/jpeg"));//必须等压缩完才读取canvas值，否则canvas内容是黑帆布
//            };
//            // 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
//            img.src = imgData;
//        }
        /* update file */
        
        
        
        

     // 创建一个Socket实例
      
        var socket = new WebSocket("ws://"+window.location.host + '/mobile/chat/socket');
        	
        // 打开Socket 
        socket.onopen = function(event) { 

        	console.log("连接成功")
          // 发送一个初始化消息
          socket.send('join'); 

          // 监听消息
          socket.onmessage = function(message) { 
        	  var text = message.data;
              text = JSON.parse(text);
//              text.text = replace_em(text.text);
              
              console.log(text)
              return
              var msg = {name:text.from, msg:text.text,imgUrl:text.pic,sexy:'man',time:'上午 10:50'};
              
              mainController.msgList.push(msg);
              initData();
          
//              var seconds = msg.msgType*1000;
//              if(seconds != 0){
//                  mainController.sreenShow = true;
//                  mainController.sreenImg = text.imgUrl;
//                  mainController.sreenMsg = text.msg;
//                  mainController.sreenTime = text.msgType;
//                  mainController.sreenShowSeconds = seconds/1000;
//                  var fl = '';
//                  fl = setInterval(function(){
//                      mainController.sreenShowSeconds--;
//                      if(mainController.sreenShowSeconds == 0){
//                          clearTimeout(fl);
//                          mainController.sreenImg = '';
//                          mainController.sreenMsg = '';
//                          mainController.sreenTime = '';
//                          mainController.sreenShow = false;
//                          return
//                      }
//                  },1000)
//              }
//              
              
            
            
            
            
          }; 

          // 监听Socket的关闭
          socket.onclose = function(event) { 
            console.log('Client notified socket has closed',event); 
          }; 

          // 关闭Socket.... 
          //socket.close() 
        };


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

            alert('压缩前：' + initSize);
            alert('压缩后：' + ndata.length);
            alert('压缩率：' + ~~(100 * (initSize - ndata.length) / initSize) + "%");

            tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;

            return ndata;
        }

        /*  图片压缩 上传  */





    function init(){

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



    // init Swiper
    function showPhoto(){
        var swiper = new Swiper('.swiper-container',{
            preventClicks : false,
            preventLinksPropagation : false,
            touchRatio : 1,
            lazyLoading : true,
        });
    }

    var textContain = $(".msg-block-contain");
    var textcontainer = $(".msg-block-container");

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
        str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/plugin/qqface/face/$1.gif'/>");
        return str;
    };


    /* qqface */


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

    initData();

    // 获取初始化数据 滑动到底部
    function initData(){
        console.log(textcontainer.height() - textContain.height());
        textContain.animate({scrollTop:textcontainer.height() - textContain.height() + 100},500,'swing');
    }




    return chatRoom = {
        init:function(){
            init();
        }
    }


});
