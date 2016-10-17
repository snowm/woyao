/**
 * Created by Luozhongdao on 2016/9/20 0020.
 */

define(['jquery','flatUI','jquery.cookies'],function($){


    jQuery(document).ready(function() {
        "use strict";

        // Page Preloader
        
    });


    $(function(){

        var _config = {    // 通用配置设置变量
            debug : true,  // console.info 开关
        };


        console.info = function(msg,set){
            if(_config.debug){
                if (!set){
                    set = "color:#1c6dc6";
                }
                console.log("%c " + msg,set);
            }else{
                return;
            }
        };

        console.info("倮克后台管理系统。","color:#1c6dc6;font-size:16px");
        console.info("Debug 开启。","color:#1c6dc6;font-size:16px");
        console.info("发布前记得关闭。","color:red;font-size:8px");

    });

    //$(function(){
    //    // 根据配置加载所需模块
    //    var scripts = document.getElementsByTagName("script");
    //    for (var i = 0; i < scripts.length; i++) {
    //    // 获取页面所需加载模块入口名称
    //        var module = scripts[i].getAttribute("require-module");
    //        if (module != undefined && module != "") {
    //            require([ module ]);
    //            break;
    //        }
    //    }
    //});
});