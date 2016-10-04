/**
 * Created by luozhongdao on 2016/9/15 0015.
 */

require.config({
    baseUrl: '/admin/resources/',
    paths: {
        'avalon': ["./js/plugin/avalon"],
        'mmHistory': './js/plugin/mmHistory',
        'mmRouter': './js/plugin/mmRouter',
        'text': ['./js/plugin/require/text'],
        'domReady': './js/plugin/domReady',
        'jquery': ['./js/plugin/jquery-1.11.1.min'],
        'jquery.cookies': ['./js/plugin/jquery.cookies'],
        'jquery-ui': ['./js/plugin/jquery-ui-1.10.3.min'],
        'jquery-migrate': ['./js/plugin/jquery-migrate-1.2.1.min'],
        'bootstrap': ['./js/plugin/bootstrap.min'],
        'custom': ['./js/plugin/custom'],
        'toggles': ['./js/plugin/toggles.min'],
        'modernizr': ['./js/plugin/modernizr.min'],
    },shim: {
        "jquery":{
            "expotrs":'$'
        },
        "jquery.cookies":["jquery"],
        "jquery-ui":["jquery"],
        "jquery-migrate":["jquery"],
        "custom":["jquery"],
        "toggles":["jquery"],
        "bootstrap":["jquery"],
    },
    priority: ['text']
});


require(["/admin/resources/js/common.js",'mmRouter',"domReady!"],function(mmRouter,domReady){
    var model = avalon.define({
        $id: "root",
        content: "/admin/resources/html/home.html",
    });

    //导航回调
    function callback() {
        var controllerPath = "/admin/resources/js";
        var viewPath = "/admin/resources/html";

        var paths = this.path.split("/");
        for (var i = 0; i < paths.length; i++) {
            if (paths[i] != "") {
                controllerPath += "/" + paths[i] + '.js';
                viewPath += "/" + paths[i];
            }
        }

        console.log(controllerPath);
        console.log(viewPath);
        require([controllerPath], function (page) {
            avalon.vmodels.root.content = viewPath + ".html";
        });
    }

//    avalon.log("加载avalon路由");
    avalon.router.get("/business-manage", callback);
    avalon.router.get("/home", callback);

    avalon.history.start({
        basepath: "/avalon"
    });
    avalon.scan()
});