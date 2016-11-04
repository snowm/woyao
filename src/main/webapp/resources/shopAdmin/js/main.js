/**
 * Created by luozhongdao on 2016/9/15 0015.
 */

require.config({
    baseUrl: '/shopAdmin/resources/',
    paths: {
        'avalon': ["./js/plugin/avalon"],
        'mmHistory': './js/plugin/mmHistory',
        'mmRouter': './js/plugin/mmRouter',
        'text': ['./js/plugin/require/text'],
        'domReady': './js/plugin/domReady',
        'jquery': ['http://libs.baidu.com/jquery/1.7.1/jquery.min'],
        'flatUI': ['./js/plugin/flat-ui.min'],
        'jquery.cookies': ['./js/plugin/jquery.cookies'],
        'datapicker':['./js/plugin/datapicker'],
        'datapicker.cn':['./js/plugin/datapicker.cn'],
        'bootstrap': ['./js/plugin/bootstrap.min'],
        'uploadfile': ['./js/plugin/uploadfile'],
        'highcharts':['./js/plugin/highcharts'],
        'exporting':['./js/plugin/exporting'],
        'dark-unica':['./js/plugin/dark-unica']
//        'jquery-ui': ['./js/plugin/jquery-ui-1.10.3.min'],
//        'jquery-migrate': ['./js/plugin/jquery-migrate-1.2.1.min'],
        
//        'application':['./js/plugin/application']
//        'custom': ['./js/plugin/custom'],
//        'toggles': ['./js/plugin/toggles.min'],
//        'modernizr': ['./js/plugin/modernizr.min'],
    },shim: {
        "jquery":{
            "expotrs":'$'
        },
        "jquery.cookies":["jquery"],
        "datapicker":["jquery"],
        "datapicker.cn":["jquery"],
        "uploadfile":["jquery"],
//        "jquery-ui":["jquery"],
//        "jquery-migrate":["jquery"],
//        "custom":["jquery"],
//        "toggles":["jquery"],
        "bootstrap":["jquery"],
        "flatUI":["jquery"],
        "highcharts":["jquery"],
        "exporting":["jquery","highcharts"],
        "dark-unica":["jquery","highcharts"],
//        "application":["jquery"],
    },
    priority: ['text']
});


require(['/shopAdmin/resources/js/common.js','mmRouter',"domReady!"],function(mmRouter,domReady){
	ShopDetail();
    var root = avalon.define({
        $id: "root",
        nav:"home",
        shopDetail:{},
        content: "/shopAdmin/resources/html/home.html",
        navtab:function(tab){
        	if(tab == 'home'){
        		root.nav = 'home';
        	}else if(tab == 'order'){
        		root.nav = 'order';
        	}else if(tab == 'chat'){
        		root.nav = 'chat';
        	}else if(tab == 'goods'){
        		root.nav = 'goods';
        	}else if(tab == 'shop'){
        		root.nav = 'shop';
        	}else if(tab == 'falseGoods'){
        		root.nav = 'falseGoods';
        	}else if(tab == 'help'){
        		root.nav = 'help';
        	}
        }
    });
    
    //导航回调
    var _pagepathAry = [];
    function callback() {
    	var loadedFlag = false;
        var controllerPath = "/shopAdmin/resources/js";
        var viewPath = "/shopAdmin/resources/html";
        var pagepath = "";      //这个是网页的变量

        var paths = this.path.split("/");
        if(paths[1] == ''){
        	paths = ['','home']
        }
        for (var i = 0; i < paths.length; i++) {
            if (paths[i] != "") {
                controllerPath += "/" + paths[i] + '.js';
                viewPath += "/" + paths[i];
                pagepath = "_" + paths[i]
            }
        }
        require([controllerPath], function (page) {
            avalon.vmodels.root.content = viewPath + ".html";
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
        if(pagepath == '_order' && loadedFlag){
            order.init();
        }else if(pagepath == '_chat' && loadedFlag){
        	chat.init();
        }else if(pagepath == '_shop' && loadedFlag){
        	shop.init();
        }else if(pagepath == '_goods' && loadedFlag){
        	goods.init();
        }
        else if(pagepath == '_home' && loadedFlag){ 
        	home.init();
        }
    }
 
    avalon.router.get("/order", callback);
    avalon.router.get("/chat", callback);
    avalon.router.get("/demo", callback);
    avalon.router.get("/shop", callback);
    avalon.router.get("/goods", callback);
    avalon.router.get("/home", callback);
    avalon.router.get("/falseGoods", callback);
    avalon.router.get("/help", callback);
    avalon.router.get("/", callback);

    avalon.history.start({
        basepath: "/avalon"
    });
    
    avalon.scan();
    
    function ShopDetail(){
		 
    	$.ajax({
    		  type: "get",
    		  url: '/shop/admin/detail/search',
    		  success: function(data){
    			  console.log(data);
    			  root.shopDetail=data;
    		  },
    		  dataType: 'json'
    		});
    }
    
});
