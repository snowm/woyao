(function(){
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);
    var butTom=avalon.define({
        $id:"butTom" ,
        everydayRanKs:true,//今日土豪榜
        WeekRank:false,//周排行榜
        MonthRank:false,//月排行榜
        showBottom:true,//显示下边框
        showBottoms:false,
        showBottomss:false,
            dayRank:function () { //点击显示日排行
                butTom.everydayRanKs=true;
                butTom.WeekRank=false;
                butTom.MonthRank=false;
                butTom.showBottom=true;
                butTom.showBottoms=false;
                butTom.showBottomss=false;
            },
            weekRank:function () { //点击显示周排行
                butTom.WeekRank=true;
                butTom.everydayRanKs=false;
                butTom.MonthRank=false;
                butTom.showBottom=false;
                butTom.showBottoms=true;
                butTom.showBottomss=false;
            },
            monthRank:function () { //点击显示月排行
                butTom.MonthRank=true;
                butTom.everydayRanKs=false;
                butTom.WeekRank=false;
                butTom.showBottom=false;
                butTom.showBottoms=false;
                butTom.showBottomss=true;
            },
            shsa:function () {
                window.location="./chat.html"
            }
    });

    // [].forEach.call($("*"),function(a){
    //     a.style.outline="1px solid #"+(~~(Math.random()*(1<<24))).toString(16)
    // });

    avalon.scan();
})();

