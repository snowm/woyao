/**
 * Created by luozhongdao on 2016/9/29 0029.
 */
    // init fastClick
window.onload = function(){
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);
    
    
    var wxadt = {
    		appId: document.getElementById('appId').value, 
    		timestamp: document.getElementById('timestamp').value,
    		nonceStr: document.getElementById('nonceStr').value, 
    		signature: document.getElementById('signature').value,
        }
            
        // 配置微信js-sdk许可
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: wxadt.appId, // 必填，企业号的唯一标识，此处填写企业号corpid
            timestamp: wxadt.timestamp, // 必填，生成签名的时间戳
            nonceStr: wxadt.nonceStr, // 必填，生成签名的随机串
            signature: wxadt.signature,// 必填，签名，见附录1
            jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });


    var barListController = avalon.define({
        $id: "barListController",
        listShow:'map',
        barList:[],
        tabChange:function(tab){
            if(tab == 'list'){
                barListController.listShow = 'list';
            }else{
                barListController.listShow = 'map';
            }
        },
        toChatRooms:function(id){
            window.location.href = '/m/chatRoom/' + id + '#!/'
        },
        location:{}
    });

    
    wx.ready(function(){
    	wx.getLocation({
    	    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
    	    success: function (res) {
    	        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
    	        var longitude = res.longitude ; // 经度，浮点数，范围为180 ~ -180。
    	        var speed = res.speed; // 速度，以米/每秒计
    	        var accuracy = res.accuracy; // 位置精度
    	        var urls = "http://api.map.baidu.com/geoconv/v1/?coords=" +　longitude + "," + latitude + "&from=1&to=5&ak=mZm3GQOvw7AFyZIKrkeomWMbhMbpP2Cc";
    	        $.ajax({
    	            url: urls,   
    	            dataType: "JSONP",  
    	            async: true, 
    	            type: "get",
    	            success: function(data) {
    	                queryData(data.result.x,data.result.y);
    	                map.centerAndZoom(new BMap.Point( data.result[0].x , data.result[0].y ), 15);
    	                barListController.location = data.result;
    	            },
    	            complete: function() {
    	            },
    	            error: function() {
    	            	alert("GPS坐标转换百度坐标失败");
    	            }
    	        });
    	    },
    	    error : function(){
    	    },
            fail : function(){
            },
            failure : function(){
            }
    	});
    });
    wx.error(function(d){
      console.log("wx jsapi error:"+d);
    });
    
    
    function queryData(lnt,lat){
    	 $.ajax({
            url: "/m/shopList",   
            dataType: "JSON",  
            async: true, 
            data: {
//            	 latitude: lat || 30.663780,
//            	 longitude:lnt || 104.072227, 
            	 latitude: lat,
             	 longitude:lnt, 
            	 pageNumber:1,
            	 pageSize:50
            },
            type: "POST",
            success: function(data) {
            	if(data.results != null){
            		barListController.barList = data.results;
                    addShop(data.results);
            	}
            },
            complete: function() {
            },
            error: function() {
                
            }
        });
    }
    

    var mlocation = {};
    var map = new BMap.Map("map");
//    map.centerAndZoom(new BMap.Point(104.072227,30.663456), 18);

    //map.setMapStyle({style:'googlelite'});
    map.addControl(new BMap.NavigationControl({offset: new BMap.Size(10, 50)}));



// 定义一个控件类，即function
    function ZoomControl(){
        // 设置默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_BOTTOM_LEFT;
        this.defaultOffset = new BMap.Size(10, 50);
    }
// 通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();
    ZoomControl.prototype.initialize = function(map){
        // 创建一个DOM元素
        var div = document.createElement("div");

        // 设置样式
        div.style.cursor = "pointer";
        div.style.width = "40px";
        div.style.height = "40px";
        div.style.borderRadius = "5px";
        div.style.border = "1px solid rgba(0, 0, 0, 0.5)";
        div.style.background = "url('/resources/static/img/lct.png') center no-repeat";
        div.style.backgroundSize = "80% 80%";
        div.style.backgroundColor = "rgba(255, 255, 255, 0.85)";
        // 绑定事件，点击回到定位位置
        div.onclick = function(e){
        	wx.getLocation({
        	    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
        	    success: function (res) {
        	        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
        	        var longitude = res.longitude ; // 经度，浮点数，范围为180 ~ -180。
        	        var speed = res.speed; // 速度，以米/每秒计
        	        var accuracy = res.accuracy; // 位置精度
        	        var urls = "http://api.map.baidu.com/geoconv/v1/?coords=" +　longitude + "," + latitude + "&from=1&to=5&ak=mZm3GQOvw7AFyZIKrkeomWMbhMbpP2Cc";
        	        $.ajax({
        	            url: urls,   
        	            dataType: "JSONP",  
        	            async: true, 
        	            type: "get",
        	            success: function(data) {
        	                map.centerAndZoom(new BMap.Point(data.result[0].x,data.result[0].y), 15);
        	                queryData(data.result.x,data.result.y);
        	                barListController.location = data.result;
        	            },
        	            complete: function() {
        	            },
        	            error: function() {
        	            	alert("GPS坐标转换百度坐标失败");
        	            }
        	        });
        	    },
        	    error : function(){
        	      console.log(arguments);
        	    },
                fail : function(){
                  console.log(arguments);
                },
                failure : function(){
                  console.log(arguments);
                }
        	});
            
        };
        // 添加DOM元素到地图中
        map.getContainer().appendChild(div);
        // 将DOM元素返回
        return div;
    };
// 创建控件实例
    var myZoomCtrl = new ZoomControl();
    map.addControl(myZoomCtrl);

    /*自定义覆盖物*/
// 定义自定义覆盖物的构造函数
    function SquareOverlay(center, length,text,img){
        this._center = center;
        this._length = length;
        this._text = text;
        this._img = img;
    }
// 继承API的BMap.Overlay
    SquareOverlay.prototype = new BMap.Overlay();
// 实现初始化方法
    SquareOverlay.prototype.initialize = function(map){
        // 保存map对象实例
        this._map = map;
        // 创建div元素，作为自定义覆盖物的容器
        var div = document.createElement("div");
        var p = document.createElement("p");
        var p2 = document.createElement("p");;
        var pic = document.createElement("img");
        p.innerHTML = "距离:50km";
        p.style.color = 'rgba(255, 255, 255, 0.8)';
        p.style.fontSize = '8px';
        
        pic.src = this._img;
        pic.style.width = 30 + "px";
        pic.style.height = 30 + "px";
        pic.style.display = "block";
        pic.style.margin = "0 auto";
        pic.style.borderRadius = '5px';

        p2.innerHTML = "点击进入聊天室";
        p2.style.color = '#00DAFF';
        p2.style.fontSize = '6px';

        div.innerHTML = this._text;

        div.appendChild(pic);
        div.appendChild(p);
        div.appendChild(p2);
        div.style.position = "absolute";
        // 可以根据参数设置元素外观
        div.style.width = this._length + "px";
        div.style.minHeight = '20px';
        div.style.borderRadius = '4px';
        div.style.background = "rgba(0, 0, 0, 0.7)";

        div.style.border = "1px solid #000000";
        div.style.color = "white";
        div.style.textAlign = "center";
        div.style.fontSize = "12px";
        // 将div添加到覆盖物容器中
        map.getPanes().markerPane.appendChild(div);
        // 保存div实例
        this._div = div;
        // 需要将div元素作为方法的返回值，当调用该覆盖物的show、
        // hide方法，或者对覆盖物进行移除时，API都将操作此元素。
        return div;
    };
    SquareOverlay.prototype.addEventListener = function(event,fun){
        this._div['on'+event] = fun;
    };
// 实现显示方法
    SquareOverlay.prototype.show = function(){
        if (this._div){
            this._div.style.display = "";
        }
    };
// 实现隐藏方法
    SquareOverlay.prototype.hide = function(){
        if (this._div){
            this._div.style.display = "none";
        }
    };
// 实现绘制方法
    SquareOverlay.prototype.draw = function(){
        // 根据地理坐标转换为像素坐标，并设置给容器
        var position = this._map.pointToOverlayPixel(this._center);
        this._div.style.left = position.x - this._length / 2 + "px";
        this._div.style.top = position.y - this._length / 2 + "px";
    };
    
    map.addEventListener("dragend", function(){    
    	 var center = map.getCenter();    
    	 queryData(center.lng,center.lat);
    });
    
    
// 添加自定义覆盖物
    function addShop(data){
    	for (var i = 0; i < data.length; i++) { 
            (function (x) {
            	var flag = x;
            	var x = new SquareOverlay(new BMap.Point( data[flag].longitude , data[flag].latitude ), 80, data[flag].name, data[flag].picURL);
            	map.addOverlay(x);
            	x.addEventListener('touchstart',function(){
                  window.location.href = '/m/chatRoom/' + data[flag].id + '#!/'
              });
            })(i);  
         };
//    	setTimeout(function(){
//      		map.centerAndZoom(new BMap.Point(104.072227,30.66378), 12)
//    	},500)
    }
    avalon.scan()
}
