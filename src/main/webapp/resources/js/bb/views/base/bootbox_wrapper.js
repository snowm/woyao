define(['jquery', 'backbone', 'bootbox'],
    function ($, Backbone, bootbox) {

        var BootboxWrapperView = Backbone.View.extend({

            render : function() {
                return this;
            },

            confirm : function(options) {
            	if (_s.isUn(options)) {
            		options = {};
            	}
            	var modal_dg = bootbox.confirm({
            		className : 'warning',
                	title : '请确认', 
                    message : options.message || '确定?',
                    buttons: {
                        'confirm': {
                            label: options.confirmLabel || '确认',
                            className: 'btn-danger'
                        },
                        'cancel': {
                            label: options.cancelLabel || '取消',
                            className: 'btn-success'
                        },
                    },
                    callback : function(result) {
                        if (result === true) {
                            if ( options.confirmCallback != undefined ) {
                                options.confirmCallback();
                            } else {
                                return;
                            }
                        } else {
                            if ( options.cancelCallback != undefined ) {
                                options.cancelCallback();
                            }
                        }
                    }
                });
                _s.checkBootstrapEmbeddedModal(modal_dg);
            },

            error : function(message, options) {
                if (!options) {
                	options = {};
                }
            	_.defaults(options, {
            		className : 'error',
            		backdrop : 'static',
            		title : '出错了', 
            	});
                options.message = message;
                var modal_dg = bootbox.alert(options);
                _s.checkBootstrapEmbeddedModal(modal_dg);
            },
            alert : function(message, options) {
                if (!options) {
                	options = {};
                }
            	_.defaults(options, {
            		className : 'warning',
            		backdrop : 'static',
            		title : '警告', 
            	});
                options.message = message;
                var modal_dg = bootbox.alert(options);
                _s.checkBootstrapEmbeddedModal(modal_dg);
            },
            info : function(message, options) {
                if (!options) {
                	options = {};
                }
            	_.defaults(options, {
            		className : 'info',
            		backdrop : 'static',
            		title : '信息', 
            	});
                options.message = message;
                var modal_dg = bootbox.alert(options);
                _s.checkBootstrapEmbeddedModal(modal_dg);
            },
        });

        return BootboxWrapperView;
    }
);
