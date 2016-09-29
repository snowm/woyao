/**
 * Created by luozhongdao on 2016/9/7 0007.
 */


(function(){
    // init fastClick
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);


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
        //choiseImg:function(){
        //    wx.ready(function(){
        //        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        //        wx.chooseImage({
        //            count: 1, // 默认9
        //            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        //            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        //            success: function (res) {
        //                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
        //            }
        //        });
        //    });
        //},
        richerList:function(){
            window.location.href='./screen.html';
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
            window.location.href='./msg.html';
        },
        privateChat:function(){
            window.location.href='./chat.html';
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
                var msg = {
                    text : mainController.msgText,
                    pic : mainController.imgUrl,
                };
                //console.log(msg);
//                socket.emit('user message',JSON.stringify(msg));
                console.log(JSON.stringify(msg));
                socket.send(JSON.stringify(msg));
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
            var f = e.files[0];//一次只上传1个文件，其实可以上传多个的
            var FR = new FileReader();
            FR.onload = function(f){
                var img = this.result;
                compressImg(img,600,function(data){//压缩完成后执行的callback
                    mainController.imgViewSrc = img;
                    mainController.imgUrl = data;
                });
            };
            FR.readAsDataURL(f);//先注册onload，再读取文件内容，否则读取内容是空的
        }
    });

    avalon.scan();

    var textContain = $(".msg-block-contain");
    var textcontainer = $(".msg-block-container");

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

    /* qqface */
    // init QQ face
    $('.emoji-btn').qqFace({
        id : 'facebox', //表情容器的ID
        assign:'sendIpt', //文本框
        path:'/resources/plugin/qqface/face/',	//表情存放的路径
        container:'faceCtn'
    });

    // compile QQ faceCode
    function replace_em(str){
        str = str.replace(/\</g,'&lt;');
        str = str.replace(/\>/g,'&gt;');
        str = str.replace(/\n/g,'<br/>');
        str = str.replace(/\[em_([0-9]*)\]/g,"<img src='/resources/plugin/qqface/face/$1.gif'/>");
        return str;
    };
    /* qqface */

    /* socket */
    //var ws = new WebSocket('ws://192.168.0.110:3001');
    //ws.onopen = function() { console.log("open")};
    //ws.onmessage = function(evt)
    //{
    //    console.log(evt.data)
    //};
    //var socket = io();
    //socket.on('chat message', function(msg){
    //    var text = msg;
    //    text = JSON.parse(text);
    //    text.msg = replace_em(text.msg);
    //    console.log(text)
    //    mainController.msgList.push(text);
    //    textContain.animate({scrollTop:textcontainer.height() - textContain.height() + 100},500,'swing');
    //
    //    var seconds = text.msgType*1000;
    //    if(seconds != 0){
    //        mainController.sreenShow = true;
    //        mainController.sreenImg = text.imgUrl;
    //        mainController.sreenMsg = text.msg;
    //        mainController.sreenTime = text.msgType;
    //        mainController.sreenShowSeconds = seconds/1000;
    //        var fl = '';
    //        fl = setInterval(function(){
    //            mainController.sreenShowSeconds--;
    //            if(mainController.sreenShowSeconds == 0){
    //                clearTimeout(fl);
    //                mainController.sreenImg = '';
    //                mainController.sreenMsg = '';
    //                mainController.sreenTime = '';
    //                mainController.sreenShow = false;
    //                return
    //            }
    //        },1000)
    //    }
    //});
    /* socket */

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



    /* socket */
    //建立websocket连接
    socket = new WebSocket("ws://"+window.location.host + '/mobile/chat/socket');
    //打开websocket时触发
    socket.onopen = function(){
        $("#showMsg").append("连接成功..<br/>");
//        socket.send("new chatter presented!");
        console.log("websocket open");
    }
    //服务端有消息推送到客户端时触发
    socket.onmessage = function(message){
        console.log('received msg:'+message.data);
        var d = JSON.parse(message.data);
        var msg = {name:d.from, msg:d.text,sexy:'man',time:'上午 10:50'};
        mainController.msgList.push(msg);
    }
    //websocket关闭时触发
    socket.onclose = function(event){
        console.log("websocket close");
    }
    //websocket出错时触发
    socket.onerror = function(event){
        socket.close();
        console.log("websocket error");
        var msg = {name:'sys', msg:event,sexy:'man',time:'上午 10:50'};
        mainController.msgList.push(msg);
    }
    $("#sendButton").click(function(){
        //通过websocket对象的send方法发送数据到服务端,该方法不能直接传送json对象，//可以先将json对象转换成json格式字符串发送给服务端
        var obj = {
            to:'',
            text: '',
            pic: ''
        };
        //或者使用JSON.stringify(obj);如果js报错找不到该方法，可以自定义一个简单的//jquery插件，功能就是将简单json对象转换成json格式字符串
        socket.send(obj.toJSONString());
        //socket.send($.simpleJsonToString(obj));
    });
    /* socket */



})();