(function(factory) {

  // Establish the root object, `window` (`self`) in the browser, or `global`
  // on the server.
  // We use `self` instead of `window` for `WebWorker` support.
  var root = (typeof self == 'object' && self.self === self && self)
      || (typeof global == 'object' && global.global === global && global);

  if (typeof define === 'function' && define.amd) {
    define([ 'jquery', 'underscore', 'exports' ], function($, _, exports) {
      // Export global even in AMD case in case this script is loaded with
      // others that may still expect a global Backbone.
      root._s = factory(root, exports, $, _);
    });
  } else if (typeof exports !== 'undefined') {
    var _ = require('underscore'), $;
    $ = require('jquery');

    factory(root, exports, $, _);
    // Finally, as a browser global.
  } else {
    root._s = factory(root, {}, (root.jQuery || root.Zepto || root.ender || root.$), root._);
  }
}(function init(root, _s, $, _) {

  _s.bindEvent = function(jqueryEle, eventName, method, context) {
    var m = method;
    if (context) {
      m = _.bind(method, context);
    }
    jqueryEle.on(eventName, m);
  };

  _s.isUn = function(obj) {
    return _.isUndefined(obj) || _.isNull(obj);
  };

  _s.isBlank = function(str) {
    if (_s.isUn(str)) {
      return true;
    }
    if (_.isString(str) && str.trim() == '') {
      return true;
    }
    return false;
  };

  _s.checkBootstrapEmbeddedModal = function(modalObj) {
    var maxBD_zi = 1040;
    var bd_zi = maxBD_zi;
    var i = 0;
    $('div.modal-backdrop.in').each(function() {
      var bd = $(this);
      var zi = bd.css('z-index');
      if (zi) {
        maxBD_zi = maxBD_zi < zi ? zi : maxBD_zi;
      }
      i++;
    });
    if (i > 1) {
      bd_zi += (100 * (i - 1));
      bd_zi = bd_zi < maxBD_zi ? maxBD_zi : bd_zi;
      $('.modal-backdrop.in:last').css('z-index', bd_zi);
    }

    var maxMD_zi = 1050;
    var md_zi = maxMD_zi;
    var i2 = 0;
    $('div.modal.in').each(function() {
      var md = $(this);
      var zi = md.css('z-index');
      if (zi) {
        maxMD_zi = maxMD_zi < zi ? zi : maxMD_zi;
      }
      i2++;
    });
    if (i > i2) {
      i2 = i;
    }
    if (i2 > 1) {
      md_zi += (100 * (i2 - 1));
      md_zi = md_zi < maxMD_zi ? maxMD_zi : md_zi;
      modalObj.css('z-index', md_zi);
    }
  };

  _s.renderEasyui = function() {
    if ($.parser && $.fn.slider) {
      $.parser.parse();
    }
  };

  _s.makeReadonly = function(view) {
    view.$('select').attr('disabled', 'disabled');
    view.$('input[type="checkbox"]').attr('disabled', 'disabled');
    view.$('input[type="radio"]').attr('disabled', 'disabled');
    view.$('input').attr('readonly', 'readonly');
    view.$('textarea').attr('readonly', 'readonly');
  };

  _.extend(_s, _);
  return _s;
}));
