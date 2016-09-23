(function(factory) {

  // Establish the root object, `window` (`self`) in the browser, or `global`
  // on the server.
  // We use `self` instead of `window` for `WebWorker` support.
  var root = (typeof self == 'object' && self.self === self && self)
      || (typeof global == 'object' && global.global === global && global);

  if (typeof define === 'function' && define.amd) {
    define([ 'jquery', 'underscore', 'snowm', 'spin', 'exports', 'jquery.blockUI' ], function($, _, _s, Spinner,
        exports) {
      // Export global even in AMD case in case this script is loaded with
      // others that may still expect a global Backbone.
      root.SpinnerExt = factory(root, exports, $, _, _s, Spinner);
    });
  } else if (typeof exports !== 'undefined') {
    var _ = require('underscore'), $, _s, Spinner;
    $ = require('jquery');
    _s = require('snowm');
    Spinner = require('spin');
    require('jquery.blockUI');

    factory(root, exports, $, _, _s, Spinner);
    // Finally, as a browser global.
  } else {
    root.SpinnerExt = factory(root, {}, (root.jQuery || root.Zepto || root.ender || root.$),
        root._, root._s, root.Spinner);
  }
}(function init(root, SpinnerExt, $, _, _s, Spinner) {

  var default_options = {
    lines : 11, // The number of lines to draw
    length : 0, // The length of each line
    width : 25, // The line thickness
    radius : 50, // The radius of the inner circle
    scale : 0.5, // Scales overall size of the spinner
    corners : 1, // Roundness (0..1)
    color : '#fff', // #rgb or #rrggbb
    opacity : 0.25, // Opacity of the lines
    rotate : 0, // Rotation offset
    direction : 1, // 1: clockwise, -1: counterclockwise
    speed : 1, // Rounds per second
    trail : 97, // Afterglow percentage
    fps : 20, // Frames per second when using setTimeout()
    zIndex : 2e9, // Use a high z-index by default
    className : 'spinner', // CSS class to assign to the element
    top : '50%', // center vertically
    left : '50%', // center horizontally
    shadow : false, // Whether to render a shadow
    hwaccel : false, // Whether to use hardware acceleration (might be buggy)
    position : 'absolute', // Element positioning

    // ext
    blockEle : true,
    blockUI : false,
  };

  var s = SpinnerExt.Spinner = function(options, target) {
    if (_s.isUn(options)) {
      this.options = _.clone(default_options);
    } else {
      this.options = _.defaults(options, default_options);
    }
    this.target = target;
    this.initialize.apply(this, arguments);
  };

  _.extend(s.prototype, {
    initialize : function() {
    },

    start : function(target) {
      this.stop();
      if (target) {
        this.target = target;
      } else {
        this.target = $(':root');
      }
      this.spinner = new Spinner(this.options).spin(this.target.get(0));
      if (this.target && this.options.blockEle) {
        this.target.block({
          message : null,
          baseZ : 1000,
          overlayCSS : {
            opacity : 0.6,
          },
        });
      } else if (this.options.blockUI) {
        $.blockUI({
          message : null,
          baseZ : 4000,
          overlayCSS : {
            opacity : 0.6,
          },
        });
      }
    },

    stop : function() {
      if (this.spinner) {
        this.spinner.stop();
      }
      if (this.target && this.options.blockEle) {
        this.target.unblock();
      } else if (this.options.blockUI) {
        $.unblockUI();
      }
    }
  });
  
  return SpinnerExt;
}));
