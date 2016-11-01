/**
 * Created by lzd on 2016.
 */

require.config({//第一块，配置
    baseUrl: '/resources/',
    paths: {
        avalon: ["js/avalon/avalon.mobile"],
        mmHistory: ['js/avalon/mmHistory'],
        mmRouter: ['js/avalon/mmRouter'],
        text: ['http://cdn.bootcss.com/require-text/2.0.12/text.min','js/require/text'],
        domReady: ['js/require/domReady'],
        jquery: ['http://apps.bdimg.com/libs/jquery/1.9.1/jquery.min','js/jquery-1.9.1/jquery'],
        qqface:'js/qqface/jquery.qqFace',
        swiper:['http://cdn.bootcss.com/Swiper/3.3.1/js/swiper.min','js/swiper-master/swiper.min'],
        async: ['js/require/async'],
        socket:['modules/socket'],
        wxsdk:['http://res.wx.qq.com/open/js/jweixin-1.1.0','js/wxsdk'],
    },
    shim: {
        'qqface': {deps: ['jquery']},
        'swiper': {deps: ['jquery']},
    },
    priority: ['text'],
});


require(['mmRouter',"domReady!",'socket','wxsdk'],function(mmRouter,domReady,socket,wx){
    var rootController = avalon.define({
        $id: "rootController",
        _msgIndex:0, //客户端递增消息id
        lock:false, //断开socket标记
        _loading:false, // loading遮盖层
        content: "",
        _userInfo:{}, //当前用户信息
        _roomInfo:{}, //房间消息
        _privacyMsg:[],//私聊消息
        privacyMsglength:0,//私聊消息总数
        _publicMsg:[],//公共消息
        toWho:{},//当前私聊对象
        hiddenLoading:function(){
        	alert("close loading");
        	rootController._loading = false;
        }
    });
    

    avalon.scan();
    
    var wxadt = {
    		appId: document.getElementById('appId').value, 
    		timestamp: document.getElementById('timestamp').value,
    		nonceStr: document.getElementById('nonceStr').value, 
    		signature: document.getElementById('signature').value,
        }
    //========================  配置微信js-sdk许可  ========================
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: wxadt.appId, // 必填，企业号的唯一标识，此处填写企业号corpid
        timestamp: wxadt.timestamp, // 必填，生成签名的时间戳
        nonceStr: wxadt.nonceStr, // 必填，生成签名的随机串
        signature: wxadt.signature,// 必填，签名，见附录1
        jsApiList: ['previewImage','getLocation','chooseWXPay','onMenuShareAppMessage','chooseImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    //========================  配置微信js-sdk许可  ========================
    
//    
//    wx.ready(function(){
//    	
//    });
//    
//    wx.error(function(res){
//    });
    
    var _brageData = {};
    

    //导航回调
    var _pagepathAry = [];
    
    function callback() {
        var loadedFlag = false;
        var jspath = "modules"; //这里改成您自己的网站地址 ,这个是js路径的变量
        var pagepath = "";      //这个是网页的变量

        var paths = this.path.split("/");
        
        //判断hash是不是空
        if(paths[1] == ''){
            paths = ['','chatRoom']
        }
        
        
        for (var i = 0; i < paths.length; i++) {
            if (paths[i] != "") {
                jspath += "/" + paths[i] + "Controller";
                pagepath += "_" + paths[i];
            }
        }
        // 加载路由
        require([jspath], function (page) {
            avalon.vmodels.rootController.content = pagepath;
        });

        if(_pagepathAry){
            for(var i = 0 ; i < _pagepathAry.length ;i++ ){
                if(_pagepathAry[i] == pagepath){
                    loadedFlag = true;
                }
            }
        }

        // 如果是第一次访问 记录下hush值
        if(!loadedFlag){
            _pagepathAry.push(pagepath);
        }

        // 如果是第二次进入页面 执行初始化方法（等封装 必须写判断调用）
        if(pagepath == '_privacyChat' && loadedFlag){
            privacyChat.init();
        }else if(pagepath == '_chatRoom' && loadedFlag){
            chatRoom.init();
        }else if(pagepath == '_chatter' && loadedFlag){
            chatter.init();
        }else if(pagepath == '_richer' && loadedFlag){
            richer.init();
        }

        
    }
    

    
    
    avalon.router.get("/", callback);
    avalon.router.get("/chatter", callback);
    avalon.router.get("/chatRoom", callback);
    avalon.router.get("/privacyChat", callback);
    avalon.router.get("/richer", callback);
    avalon.history.start({
        basepath: "/"
    });
});

