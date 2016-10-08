/**
 * Created by lzd on 2016/10/7 0007.
 */

define(['jquery','avalon', 'text!./chatter.html',"domReady!"], function ($,avalon,_chatter,domReady) {
    avalon.templateCache._chatter = _chatter;


    var chatterController=avalon.define({
            $id:"chatterController",
            all:true,// 全部界面
            girls:false,// 美女界面
            boys:false,//帅哥界面
            changHide:false,//点击切换消息界面
            showBottom:true,//显示下边框
            showBottoms:false,
            showBottomss:false,
            showBottomls:false,
            showChang:function(){
                chatterController.showBottomls=true;
                chatterController.showBottom=false;
                chatterController.showBottoms=false;
                chatterController.showBottomss=false;
                chatterController.changHide =!chatterController.changHide;
                chatterController.boys=false;
                chatterController.girls=false;
                chatterController.all=false;
            },
            girlsChang:function () {
                chatterController.showBottoms=true;
                chatterController.showBottom=false;
                chatterController.showBottomss=false;
                chatterController.showBottomls=false;
                chatterController.all=false;
                chatterController.girls=true;
                chatterController.boys=false;
                chatterController.changHide=false
            },
            boysChang:function () {
                chatterController.showBottomss=true;
                chatterController.showBottom=false;
                chatterController.showBottoms=false;
                chatterController.showBottomls=false;
                chatterController.boys=true;
                chatterController.girls=false;
                chatterController.all=false;
                chatterController.changHide=false;
            },
            allChang:function () {
                chatterController.showBottom=true;
                chatterController.showBottomss=false;
                chatterController.showBottoms=false;
                chatterController.showBottomls=false;
                chatterController.all=true;
                chatterController.boys=false;
                chatterController.girls=false;
                chatterController.changHide=false
            },
            chat:function () {
                window.location.hash='#!/privacyChat'
            }
        });
    avalon.scan();


    function init(){
        console.log('聊天列表初始化')
    }

    return chatter = {
        'init':function(){
            init();
        },
    }
});