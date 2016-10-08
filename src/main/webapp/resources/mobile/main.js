/**
 * Created by lzd on 2016.
 */

require.config({//第一块，配置
    baseUrl: '/resources/',
    paths: {
        avalon: ["js/avalon/avalon.mobile.shim"],
        mmHistory: 'js/avalon/mmHistory',
        mmRouter: 'js/avalon/mmRouter',
        text: ['js/require/text'],
        domReady: 'js/require/domReady',
        jquery: 'js/jquery-1.9.1/jquery',
        qqface:'js/qqface/jquery.qqFace',
        swiper:'js/swiper-master/swiper.min',
        'async': 'js/require/async',
    },
    shim: {
        'qqface': {deps: ['jquery']},
        'swiper': {deps: ['jquery']},
    },
    priority: ['text'],
});


require(['mmRouter',"domReady!"],function(mmRouter,domReady){
    avalon.log("引入avalon");

    var rootController = avalon.define({
        $id: "rootController",
        content: "",
    });

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

    avalon.log("加载路由");
    avalon.router.get("/", callback);
    avalon.router.get("/chatter", callback);
    avalon.router.get("/chatRoom", callback);
    avalon.router.get("/privacyChat", callback);
    avalon.router.get("/richer", callback);
    avalon.history.start({
        basepath: "/"
    });
    avalon.scan();
});

