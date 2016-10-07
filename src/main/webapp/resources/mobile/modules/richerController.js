/**
 * Created by lzd on 2016/10/7 0007.
 */

define(['jquery','avalon', 'text!./richer.html',"domReady!"], function ($,avalon,_richer,domReady) {
    avalon.templateCache._richer = _richer;


    var richerController=avalon.define({
        $id:"richerController" ,
        everydayRanKs:true,//今日土豪榜
        WeekRank:false,//周排行榜
        MonthRank:false,//月排行榜
        showBottom:true,//显示下边框
        showBottoms:false,
        showBottomss:false,
        dayRank:function () { //点击显示日排行
            richerController.everydayRanKs=true;
            richerController.WeekRank=false;
            richerController.MonthRank=false;
            richerController.showBottom=true;
            richerController.showBottoms=false;
            richerController.showBottomss=false;
        },
        weekRank:function () { //点击显示周排行
            richerController.WeekRank=true;
            richerController.everydayRanKs=false;
            richerController.MonthRank=false;
            richerController.showBottom=false;
            richerController.showBottoms=true;
            richerController.showBottomss=false;
        },
        monthRank:function () { //点击显示月排行
            richerController.MonthRank=true;
            richerController.everydayRanKs=false;
            richerController.WeekRank=false;
            richerController.showBottom=false;
            richerController.showBottoms=false;
            richerController.showBottomss=true;
        },
        shsa:function () {
            window.location.hash='#!/privacyChat'
        }
    });

    // [].forEach.call($("*"),function(a){
    //     a.style.outline="1px solid #"+(~~(Math.random()*(1<<24))).toString(16)
    // });

    avalon.scan();


    function init(){
        console.log('土豪初始化')
    }

    return richer = {
        'init':function(){
            init();
        },
    }
});