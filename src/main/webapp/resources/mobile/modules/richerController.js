/**
 * Created by lzd on 2016/10/7 0007.
 */

define(['jquery','avalon', 'text!./richer.html',"domReady!",'socket'], function ($,avalon,_richer,domReady,socket) {
    avalon.templateCache._richer = _richer;
    
    socket.init();

    var richerController=avalon.define({
        $id:"richerController" ,
        richerList:[],
        richerType:'DAY',
        richerTab:function (type) { //点击显示日排行
        	richerController.richerType = type;
        	queryData();
        },
        shsa:function (data) {
			 avalon.vmodels.rootController.toWho = data;
			 window.location.hash='#!/privacyChat'
        }
    });


    avalon.scan();

    function queryData(data){
   	 $.ajax({
           url: "/m/chat/richerList",   
           dataType: "JSON",  
           async: true, 
           data:{
        	   richerType:richerController.richerType,
        	   pageNumber:1,
        	   pageSize:100
           },
           type: "POST",
           success: function(res) {
        	   richerController.richerList = [];
        	   var data = res.results;
        	   richerController.richerList = data;
           },
           complete: function() {
           },
           error: function() {
               
           }
       });
   }
   
   queryData();

    function init(){
        console.log('土豪初始化')
        queryData();
    }

    return richer = {
        'init':function(){
            init();
        },
    }
});