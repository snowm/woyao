/**
 * Created by luozhongdao on 2016/9/29 0029.
 */
    // init fastClick
window.onload = function(){
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);

    var barListController = avalon.define({
        $id: "barListController",
        listShow:'list',
        tabChange:function(tab){
            if(tab == 'list'){
                barListController.listShow = 'list';
            }else{
                barListController.listShow = 'map';
            }
        },
        toChatRooms:function(){
            window.location.href = '/m/chatRoom.html'
        },
    });




    var mlocation = {};
    var map = new BMap.Map("map");

    //$.ajax({
    //    url: "http://api.map.baidu.com/location/ip",    //请求的url地址
    //    dataType: "JSONP",   //返回格式为json
    //    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    //    data: {
    //        'ak':'mZm3GQOvw7AFyZIKrkeomWMbhMbpP2Cc',
    //        'coor':'bd09ll',
    //        //'ip':"171.214.225.0"
    //    },
    //    type: "GET",   //请求方式
    //    beforeSend: function() {
    //    },
    //    success: function(req) {
    //        console.log(req);
    //        mlocation = req;
    //
    //        map.centerAndZoom(new BMap.Point(req.content.point.x, req.content.point.y), 13);
    //        //map.centerAndZoom(new BMap.Point(104.072227, 30.663456), 13);
    //    },
    //    complete: function() {
    //    },
    //    error: function() {
    //        alert("获取定位失败!")
    //    }
    //});
    map.centerAndZoom(new BMap.Point(104.072227,30.663456), 13);

    //map.setMapStyle({style:'googlelite'});

    //map.addControl(new BMap.PanoramaControl({offset: new BMap.Size(10, 60)}));
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
            map.centerAndZoom(new BMap.Point(mlocation.content.point.x, mlocation.content.point.y), 18);
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
    function SquareOverlay(center, length,text){
        this._center = center;
        this._length = length;
        this._text = text;
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
        var p2 = document.createElement("p");
        p.innerHTML = "距离:50km";
        p.style.color = 'rgba(255, 255, 255, 0.8)';
        p.style.fontSize = '8px';


        p2.innerHTML = "点击进入聊天室";
        p2.style.color = 'rgba(255, 255, 255, 0.8)';
        p2.style.fontSize = '6px';

        div.innerHTML = this._text;

        div.appendChild(p);
        div.appendChild(p2);
        div.style.position = "absolute";
        // 可以根据参数设置元素外观
        div.style.width = this._length + "px";
        div.style.minHeight = '20px';
        div.style.borderRadius = '5px';
        div.style.background = "rgba(128, 128, 128, 0.8)";
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
// 添加自定义覆盖物
    var mySquare = new SquareOverlay(new BMap.Point(104.064995, 30.664766), 80,'兰桂坊酒吧');
    var mySquare2 = new SquareOverlay(new BMap.Point(104.075994, 30.684756), 80,'九眼桥');


    map.addOverlay(mySquare);
    mySquare.addEventListener('touchstart',function(){
        window.location.href = '/html/index.html'
    });

    map.addOverlay(mySquare2);
    mySquare2.addEventListener('touchstart',function(){
        window.location.href = '/html/index.html'
    });
    avalon.scan()
}
