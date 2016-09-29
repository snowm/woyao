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
        msgList:[ //消息列表
            {name:'老万',msg:'hahaha',sexy:'man',isMe:'false'},
            {name:'小明',msg:'hahahfdsfasdfsadafdsafsdfasdfadsfasd',sexy:'weman',isMe:'false'},
            {name:'呵呵',msg:'fdsafdsdasdsa撒旦撒多撒多撒sa',sexy:'weman',isMe:'true'},
            {name:'拜拜',msg:'hahjfghjfhaha',sexy:'man',isMe:'false'},
            {name:'拜拜',msg:'hahjfghjfhaha',sexy:'man',isMe:'true'},
        ],

        togglePlugin : function(){ // 显示功能区 按钮
            mainController.emojiShow = false;
            mainController.pluginShow = !mainController.pluginShow;
        },
        toggleEmoji : function(){ // 显示emoji 按钮
            mainController.pluginShow = false;
            mainController.emojiShow = !mainController.emojiShow;
        },
        subMsg:function(){
            var str = mainController.senttext;
            $("#show").html(replace_em(str));
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
        choiseImg:function(){
            wx.ready(function(){
                // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                wx.chooseImage({
                    count: 1, // 默认9
                    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                    success: function (res) {
                        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    }
                });
            });
        },
        richerList:function(){
            window.location.href='http://192.168.0.104:3000/html/screen.html';
        },
        showPhotoWall:function(){
            if(mainController.isShowPhoto){
                mainController.popshow = false;
                setTimeout(function(){
                    mainController.isShowPhoto = false;
                    $(".pop-photoWall").css('display','none');
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
        sendMsg:function(){
            //socket.emit('chat message', mainController.senttext);
        }
    });
    avalon.scan();

    var textContain = $(".msg-block-contain");
    var textcontainer = $(".msg-block-container");

    // 获取初始化数据
    function initData(){
        console.log(textcontainer.height() - textContain.height());
        textContain.animate({scrollTop:textcontainer.height() - textContain.height() + 100},500,'swing');
    }
    initData();


    function showPhoto(){
        var swiperPhoto = new Swiper('.swiper-photowall',{
            preventClicks : false,
            preventLinksPropagation : false,
            touchRatio : 1,
            lazyLoading : true,
        });
    }
    $('.emoji-btn').qqFace({
        id : 'facebox', //表情盒子的ID
        assign:'sendIpt', //给那个控件赋值
        path:'../plugin/qqface/face/',	//表情存放的路径
        container:'faceCtn'
    });
//查看结果
    function replace_em(str){
        str = str.replace(/\</g,'&lt;');
        str = str.replace(/\>/g,'&gt;');
        str = str.replace(/\n/g,'<br/>');
        str = str.replace(/\[em_([0-9]*)\]/g,'<img src="../plugin/qqface/face/$1.gif" border="0" />');
        return str;
    }



})();