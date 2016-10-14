/**
 * Created by lzd on 2016.
 */

require.config({//第一块，配置
    baseUrl: '/resources/',
    paths: {
        avalon: ["js/avalon/avalon.mobile"],
        mmHistory: 'js/avalon/mmHistory',
        mmRouter: 'js/avalon/mmRouter',
        text: ['js/require/text'],
        domReady: 'js/require/domReady',
        jquery: 'js/jquery-1.9.1/jquery',
        qqface:'js/qqface/jquery.qqFace',
        swiper:'js/swiper-master/swiper.min',
        'async': 'js/require/async',
        socket:'modules/socket',
        wxsdk:'js/wxsdk',
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
        _msgIndex:0,
        lock:false,
        _loading:false, // loading
        content: "",
        _userInfo:{}, //当前用户信息
        _privacyMsg:[],//私聊消息
        privacyMsglength:0,//私聊消息总数
        _publicMsg:[],//公共消息
        toWho:{},//当前私聊对象
    });
    

    avalon.scan();
    
//   监控数据长度 超过 x 删掉第一个
//    rootController._publicMsg.$watch('length', function(a, b) {
//    	if(a >80){
//    		rootController._publicMsg.splice(0,1)
//    	}
//    })
//    
//    rootController._privacyMsg.$watch('length', function(a, b) {
//    	if(a > 80){
//    		rootController._privacyMsg.splice(0,1)
//    	}
//    })
    
    // 微信js-sdk
    wx.config({
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wxf55a7c00ffaca994', // 必填，企业号的唯一标识，此处填写企业号corpid
        timestamp: '', // 必填，生成签名的时间戳
        nonceStr: '', // 必填，生成签名的随机串
        signature: '',// 必填，签名，见附录1
        jsApiList: ['previewImage','getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    
//    wx.ready(function(){
//    	alert("jssdk验证成功");
//        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
//    	
//    	
//    });
//    
//    
//    wx.error(function(res){
//    	alert("jssdk验证失败");
//        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
//
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

