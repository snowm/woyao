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
        'jquery': ['./js/plugin/vendor/jquery.min'],
        'flatUI': ['./js/plugin/flat-ui.min'],
        'jquery.cookies': ['./js/plugin/jquery.cookies'],
//        'jquery-ui': ['./js/plugin/jquery-ui-1.10.3.min'],
//        'jquery-migrate': ['./js/plugin/jquery-migrate-1.2.1.min'],
//        'bootstrap': ['./js/plugin/bootstrap.min'],
//        'application':['./js/plugin/application']
//        'custom': ['./js/plugin/custom'],
//        'toggles': ['./js/plugin/toggles.min'],
//        'modernizr': ['./js/plugin/modernizr.min'],
    },shim: {
        "jquery":{
            "expotrs":'$'
        },
        "jquery.cookies":["jquery"],
//        "jquery-ui":["jquery"],
//        "jquery-migrate":["jquery"],
//        "custom":["jquery"],
//        "toggles":["jquery"],
//        "bootstrap":["jquery"],
        "flatUI":["jquery"],
//        "application":["jquery"],
    },
    priority: ['text']
});


require(['/shopAdmin/resources/js/common.js','mmRouter',"domReady!"],function(mmRouter,domReady){
    var model = avalon.define({
        $id: "root",
        content: "/shopAdmin/resources/html/home.html",
    });

    //导航回调
    function callback() {
        var controllerPath = "/shopAdmin/resources/js";
        var viewPath = "/shopAdmin/resources/html";

        var paths = this.path.split("/");
        for (var i = 0; i < paths.length; i++) {
            if (paths[i] != "") {
                controllerPath += "/" + paths[i] + '.js';
                viewPath += "/" + paths[i];
            }
        }
        require([controllerPath], function (page) {
            avalon.vmodels.root.content = viewPath + ".html";
        });
    }
 
    avalon.router.get("/order", callback);
    avalon.router.get("/chat", callback);
    avalon.router.get("/demo", callback);

    avalon.history.start({
        basepath: "/avalon"
    });
    avalon.scan()
});