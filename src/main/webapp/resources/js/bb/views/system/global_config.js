define([ 'text!bb/templates/system/global_config.html',
         'bb/views/base/base_component' ],
    function (TimeLimitConfigTemplate, 
    		BaseComponent) {
        var TimeLimitConfigView = BaseComponent.extend({
            template : _.template(TimeLimitConfigTemplate),

    		events: {
    			"click #global-config-form a[name='enable-a']" : "enable",
    			"click #global-config-form a[name='disable-a']" : "disable",
    		},
    		
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
                return this;
            },

    		enable: function(e) {
    			var that = this;
    			this.bootboxWrapper.confirm({
    				message:"确定要启用服务？服务器将正常接收淘宝订单！",
    				confirmCallback: function() {
    					that._enable.apply(that, ["服务已经恢复！"]);
    				},
    			});
    		},

    		_enable: function(successMsg) {
    			this._doSyncOp('enableService', successMsg);
    		},

    		disable: function(e) {
    			var that = this;
    			this.bootboxWrapper.confirm({
    				message:"确定要停用服务？服务器将不再接收淘宝的订单！",
    				confirmCallback: function() {
    					that._disable.apply(that, ["服务已经停止！"]);
    				},
    			});
    		},

    		_disable: function(successMsg) {
    			this._doSyncOp('disableService', successMsg);
    		},

    		_doSyncOp: function(opName, successMsg) {
    			var that = this;
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.parentView.$('div.modal-dialog:first'));
                this.model.sync(opName, this.model, {
                    success: function(model, response) {
                    	spinner.stop();
    					that.bootboxWrapper.info(successMsg, that.parentView.showGlobalConfig.apply(this, []));
                    },
                    error: function(model, response) {
                    	spinner.stop();
    					that.defaultErrorHandler(model, response);
                    }
                });
    		},
        });

        return TimeLimitConfigView;
    }
);
