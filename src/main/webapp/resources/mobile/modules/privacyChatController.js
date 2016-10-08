/**
 * Created by tony on 2016-01-18.
 */

/**
 * Created by lzd on 2016.
 */

define(['jquery','avalon', 'text!./privacyChat.html','swiper',"domReady!",'qqface'], function ($,avalon,_privacyChat,swiper,domReady) {
    avalon.templateCache._privacyChat = _privacyChat;

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
            sreenShowSeconds:0, // 倒计时
            sreenImg:'/resources/static/img/delate/photo1.jpg', // 霸屏图片
            sreenMsg:'', // 霸屏文字
            sreenTime:'', // 霸屏时间
            msgList:[ //消息列表
                {name:'老万',msg:'我哈哈哈',sexy:'man',time:'上午 10:50'},
                {name:'jimmy',msg:'我哈哈哈',sexy:'weman',time:'上午 10:50'},
            ],

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
            showPhotoWall:function(){
                if(pChatController.isShowPhoto){
                    $(".pop-photoWall").css('display','none');
                    pChatController.isShowPhoto = false;
                    setTimeout(function(){
                        pChatController.popshow = false;
                    },300)
                }else{
                    pChatController.popshow = true;
                    $(".pop-photoWall").css('display','block');
                    setTimeout(function(){
                        pChatController.isShowPhoto = true;
                        showPhoto();
                    },10)
                }
            },
            privateChat:function(){
                window.location.href='./privacyChat.html';
            },
            forHer:function(id){
                if(pChatController.isShowPhoto){
                    pChatController.showPhotoWall();
                }
                pChatController.forHerShow = true;
            },
            hideForOther:function(){
                pChatController.forHerShow = false;
            },
            msgType: '0',//霸屏消息类型
            msgText:'', //文字内容
            imgUrl:'', //发送图片 base64
            sendMsg:function(){
                var msg = {
                    text : pChatController.msgText,
                    pic : pChatController.imgUrl,
                };
                console.log(JSON.stringify(msg));

                // 发送消息
                socket.send(JSON.stringify(msg));


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
            }
            ,
            imgViewSrc:'/resources/static/img/photo.png',
            fileChange:function(e){
                var f = e.files[0];//一次只上传1个文件，其实可以上传多个的
                var FR = new FileReader();
                FR.onload = function(f){
                    var img = this.result;
                    compressImg(img,600,function(data){//压缩完成后执行的callback
                        pChatController.imgViewSrc = img;
                        pChatController.imgUrl = data;
                    });
                };
                FR.readAsDataURL(f);//先注册onload，再读取文件内容，否则读取内容是空的
            }
        });

        function init(){
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
        }

        init();

        avalon.scan();

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


        // 获取初始化数据 滑动到底部
        function initData(){
            console.log(textcontainer.height() - textContain.height());
            textContain.animate({scrollTop:textcontainer.height() - textContain.height() + 100},500,'swing');
        }
        initData();

        // init Swiper
        function showPhoto(){
            var swiper = new Swiper('.swiper-container',{
                preventClicks : false,
                preventLinksPropagation : false,
                touchRatio : 1,
                lazyLoading : true,
            });
        }



        /* update file */
        function compressImg(imgData,maxHeight,onCompress){
            if(!imgData){
                alert("请传入图片");
                return;
            }
            onCompress = onCompress || function(){};
            maxHeight = maxHeight || 600;//默认最大高度600px
            var canvas = document.createElement('canvas');
            var img = new Image();
            img.onload = function(){
                if(img.height > maxHeight) {//按最大高度等比缩放
                    img.width *= maxHeight / img.height;
                    img.height = maxHeight;
                    console.log(img.width +"h:"+img.height);
                    canvas.width = img.width;
                    canvas.height = img.height;
                }else{
                    canvas.width = img.width;
                    canvas.height = img.height;
                }
                var ctx = canvas.getContext("2d");
                ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏
                //重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
                ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
                onCompress(canvas.toDataURL("image/jpeg"));//必须等压缩完才读取canvas值，否则canvas内容是黑帆布
            };
            // 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
            img.src = imgData;
        }
        /* update file */


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