require.config({
  baseUrl : "resources/js",
  paths : {
    'text' : 'lib/requirejs/text',
    'i18n' : 'lib/requirejs/i18n',
    'underscore' : 'lib/underscore/underscore-min',
    'jquery' : 'lib/jquery/jquery-3.1.0.min',
    'jquery.blockUI' : 'lib/jquery/jquery.blockUI',
    'jquery.validate' : 'lib/jquery/validation/jquery.validate.min',
    'jquery-ui' : 'lib/jquery-ui/jquery-ui.min',
    'backbone' : 'lib/backbone/backbone-min',
    'backbone-stickit' : 'lib/backbone/backbone.stickit.min',
    'backbone.localStorage' : 'lib/backbone/backbone.localStorage-min',
    'bootstrap' : 'lib/bootstrap/bootstrap.min',
    'bootstrap-select' : 'lib/bootstrap/select/bootstrap-select.min',
    'bootstrap-select-zh' : 'lib/bootstrap/select/defaults-zh_CN.min',
    'bootstrap-multiselect' : 'lib/bootstrap/bootstrap-multiselect',
    'bootstrap-paginator' : 'lib/bootstrap/bootstrap-paginator.min',
    'bootstrap-datetimepicker' : 'lib/bootstrap/datetimepicker4/bootstrap-datetimepicker.min',
    'moment' : 'lib/moment/moment.min',
    'moment-zh' : 'lib/moment/locale/zh-cn',
    'datatables.net' : 'lib/bootstrap/dataTables/jquery.dataTables.min',
    'datatables.net-bs' : 'lib/bootstrap/dataTables/dataTables.bootstrap.min',
    'datatables.net-responsive' : 'lib/bootstrap/dataTables/ext/dataTables.responsive.min',
    'bootstrap-dataTables-responsive' : 'lib/bootstrap/dataTables/ext/responsive.bootstrap.min',
    'dataTables-select' : 'lib/bootstrap/dataTables/ext/dataTables.select.min',
    'dataTables-scroller' : 'lib/bootstrap/dataTables/ext/dataTables.scroller.min',
    'dataTables-rowReorder' : 'lib/bootstrap/dataTables/ext/dataTables.rowReorder.min',
    'dataTables-colReorder' : 'lib/bootstrap/dataTables/ext/dataTables.colReorder.min',
    'dataTables-fixedHeader' : 'lib/bootstrap/dataTables/ext/dataTables.fixedHeader.min',
    'dataTables-fixedColumns' : 'lib/bootstrap/dataTables/ext/dataTables.fixedColumns.min',
    'bootbox' : 'lib/bootstrap/bootbox/bootbox.min',
    'bootboxExt' : 'lib/bootbox_ext',
    'spin' : 'lib/spin/spin.min',
    'spinExt' : 'lib/spin/spin_ext',
    'jquery.fileupload' : 'lib/jquery/fileupload/jquery.fileupload',
    'jquery.iframe-transport' : 'lib/jquery/fileupload/jquery.iframe-transport',
    'jquery.ui.widget' : 'lib/jquery/fileupload/vendor/jquery.ui.widget',
    'snowm' : 'lib/snowm',
    'json' : 'lib/json/json2',
    'globalConfig' : 'base/globalConfig',
  },
  shim : {
    'underscore' : {
      exports : '_'
    },
    'json' : {
      exports : 'JSON2'
    },
    'jquery' : {
      deps : [ 'json' ],
      exports : '$'
    },
    'backbone' : {
      deps : [ 'underscore', 'jquery' ],
    },
    'backbone.stickit' : {
      deps : [ 'backbone' ],
      exports : 'Backbone'
    },
    'backbone.localStorage' : {
      deps : [ 'backbone' ],
      exports : 'Backbone.LocalStorage'
    },
    'bootstrap' : {
      deps : [ 'jquery' ]
    },
    'bootstrap-select' : {
      deps : [ 'bootstrap' ]
    },
    'bootstrap-select-zh' : {
      deps : [ 'bootstrap-select' ]
    },
    'bootstrap-multiselect' : {
      deps : [ 'bootstrap' ]
    },
    'bootstrap-paginator' : {
      deps : [ 'bootstrap' ]
    },
    'bootstrap-datetimepicker' : {
      deps : [ 'bootstrap', 'moment', 'moment-zh' ]
    },
    'datatables.net' : {
      deps : [ 'bootstrap' ]
    },
    'datatables.net-bs' : {
      deps : [ 'datatables.net' ]
    },
    'datatables.net-responsive' : {
      deps : [ 'datatables.net-bs' ]
    },
    'bootstrap-dataTables-responsive' : {
      deps : [ 'datatables.net-responsive' ]
    },
    'dataTables-select' : {
      deps : [ 'datatables.net-bs' ]
    },
    'dataTables-scroller' : {
      deps : [ 'datatables.net-bs' ]
    },
    'dataTables-rowReorder' : {
      deps : [ 'datatables.net-bs' ]
    },
    'dataTables-colReorder' : {
      deps : [ 'datatables.net-bs' ]
    },
    'dataTables-fixedHeader' : {
      deps : [ 'datatables.net-bs' ]
    },
    'dataTables-fixedColumns' : {
      deps : [ 'datatables.net-bs' ]
    },
    'bootbox' : {
      deps : [ 'bootstrap' ],
    },
    'bootboxExt' : {
      deps : [ 'bootbox', 'snowm' ],
    },
    'spin' : {
      deps : [ 'jquery' ],
      exports : 'Spinner'
    },
    'spinExt' : {
      deps : [ 'spin', 'snowm', 'jquery.blockUI' ],
      exports : 'SpinnerExt'
    },
    'jquery-ui' : {
      deps : [ 'jquery', 'bootstrap' ]
    },
    'jquery.blockUI' : {
      deps : [ 'jquery' ],
      exports : 'jQuery.blockUI'
    },
    'jquery.validate' : {
      deps : [ 'jquery' ],
      exports : 'jQuery.validate'
    },
    'snowm' : {
      deps : [ 'bootstrap', 'underscore' ],
      exports : '_s'
    },
    'globalConfig' : {
      exports : '_c'
    },
  }
});

require([ 'bb/routers/main', 'backbone', 'base/base' ], function(MainRouter, Backbone) {
  var mainRouter = new MainRouter();
  Backbone.history.start();
});
