(function(factory) {

  // Establish the root object, `window` (`self`) in the browser, or `global`
  // on the server.
  // We use `self` instead of `window` for `WebWorker` support.
  var root = (typeof self == 'object' && self.self === self && self)
      || (typeof global == 'object' && global.global === global && global);

  if (typeof define === 'function' && define.amd) {
    define([ 'jquery', 'snowm', "bootbox", 'exports' ], function($, _s, bootbox, exports) {
      // Export global even in AMD case in case this script is loaded with
      // others that may still expect a global Backbone.
      root.BootboxExt = factory(root, exports, $, _s, bootbox);
    });
  } else if (typeof exports !== 'undefined') {
    var $, _s, bootbox;
    $ = require('jquery');
    _s = require('snowm');
    bootbox = require('bootbox');
    factory(root, exports, $, _s, bootbox);
    // Finally, as a browser global.
  } else {
    root.BootboxExt = factory(root, {}, (root.jQuery || root.Zepto || root.ender || root.$),
        root._s, root.bootbox);
  }
}(function init(root, BootboxExt, $, _s, bootbox) {

  BootboxExt.confirm = function(options) {
    if (_s.isUn(options)) {
      options = {};
    }
    var modal_dg = bootbox.confirm({
      className : 'warning',
      title : '请确认',
      message : options.message || '确定?',
      buttons : {
        'confirm' : {
          label : options.confirmLabel || '确认',
          className : 'btn-danger'
        },
        'cancel' : {
          label : options.cancelLabel || '取消',
          className : 'btn-success'
        },
      },
      callback : function(result) {
        if (result === true) {
          if (options.confirmCallback != undefined) {
            options.confirmCallback();
          } else {
            return;
          }
        } else {
          if (options.cancelCallback != undefined) {
            options.cancelCallback();
          }
        }
      }
    });
    _s.checkBootstrapEmbeddedModal(modal_dg);
    return modal_dg;
  };

  BootboxExt.error = function(message, options) {
    if (!options) {
      options = {};
    }
    _s.defaults(options, {
      className : 'error',
      backdrop : true,
      title : '出错了',
    });
    options.message = message;
    var modal_dg = bootbox.alert(options);
    _s.checkBootstrapEmbeddedModal(modal_dg);
    return modal_dg;
  };

  BootboxExt.alert = function(message, options) {
    if (!options) {
      options = {};
    }
    _s.defaults(options, {
      className : 'warning',
      backdrop : true,
      title : '警告',
    });
    options.message = message;
    var modal_dg = bootbox.alert(options);
    _s.checkBootstrapEmbeddedModal(modal_dg);
    return modal_dg;
  };

  BootboxExt.info = function(message, options) {
    if (!options) {
      options = {};
    }
    _s.defaults(options, {
      className : 'info',
      backdrop : true,
      title : '信息',
    });
    options.message = message;
    var modal_dg = bootbox.alert(options);
    _s.checkBootstrapEmbeddedModal(modal_dg);
    return modal_dg;
  };
  return BootboxExt;
}));