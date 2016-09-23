(function() {

  var root = this;

  var _c = function(obj) {
    if (obj instanceof _)
      return obj;
    if (!(this instanceof _c))
      return new _c(obj);
    this._wrapped = obj;
  };

  if (typeof exports !== 'undefined') {
    if (typeof module !== 'undefined' && module.exports) {
      exports = module.exports = _c;
    }
    exports._c = _c;
  } else {
    root._c = _c;
  }

  _c.datetime_fmt = 'yyyy-mm-dd hh:ii';
  _c.default_grid_option = function() {
    return {
      paging : false,
      searching : false,
      info : false,
      ordering : false,
      select : {
        style : 'single'
      },
    // scrollY : '100%',
    // scrollCollapse : true,
    }
  };
  _c.default_option = {
    language : 'zh-CN',
    format : _c.datetime_fmt,
    autoclose : true,
    todayBtn : true,
    minuteStep : 10,
    startView : 1,
    todayHighlight : true,
  };
  _c.aliOrderStatusMap = {
    UNDERWAY : '正在处理',
    REQUEST_FAILED : '请求失败',
    FAILED : '失败',
    SUCCESS : '成功',
    TIMEOUT : '超时',
  };
  _c.purchaseOrderStatusMap = {
    UNDERWAY : '正在处理',
    REQUEST_FAILED : '请求失败',
    FAILED : '失败',
    SUCCESS : '成功',
    TIMEOUT : '超时',
  };

}.call(this));
