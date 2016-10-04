/**
 * Created by Administrator on 2016/9/9 0009.
 */
(function(){
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);
    var butTom=avalon.define({
        $id:"butTom",
        all:true,// 全部界面
        girls:false,// 美女界面
        boys:false,//帅哥界面
        changHide:false,//点击切换消息界面
        showBottom:true,//显示下边框
        showBottoms:false,
        showBottomss:false,
        showBottomls:false,
       showChang:function(){
           butTom.showBottomls=true;
           butTom.showBottom=false;
           butTom.showBottoms=false;
           butTom.showBottomss=false;
           butTom.changHide =!butTom.changHide;
           butTom.boys=false;
           butTom.girls=false;
           butTom.all=false;
       },
        girlsChang:function () {
            butTom.showBottoms=true;
            butTom.showBottom=false;
            butTom.showBottomss=false;
            butTom.showBottomls=false;
            butTom.all=false;
            butTom.girls=true;
            butTom.boys=false;
            butTom.changHide=false
        },
        boysChang:function () {
            butTom.showBottomss=true;
            butTom.showBottom=false;
            butTom.showBottoms=false;
            butTom.showBottomls=false;
            butTom.boys=true;
            butTom.girls=false;
            butTom.all=false;
            butTom.changHide=false;
        },
        allChang:function () {
            butTom.showBottom=true;
            butTom.showBottomss=false;
            butTom.showBottoms=false;
            butTom.showBottomls=false;
            butTom.all=true;
            butTom.boys=false;
            butTom.girls=false;
            butTom.changHide=false
        },
        chat:function () {
            window.location="./chat.html"
        }
    });
    avalon.scan();
})();