define([ 'text!bb/templates/system/system_dialog.html', 
         'bb/models/system/timelimit_config','bb/models/system/global_config', 
         'bb/views/system/timelimit_config','bb/views/system/global_config',  
         'bb/views/base/base_content_dialog' ],
    function (SystemDialogTemplate,
    		TimeLimitConfig, GlobalConfig, 
    		TimeLimitConfigView, GlobalConfigView,  
    		BaseContentDialogView) {
        var SystemConfigDialogView = BaseContentDialogView.extend({
            template : _.template(SystemDialogTemplate),
            currentTab : null, 
            timeLimitConfigView : null,
            globalConfigView : null, 

            renderEl : function() {
                this.$el.html(this.template());
                var that = this;
                this.$('div.modal-body >div > ul > li > a').click( function (e) {
                	e.preventDefault();
                    var selected = $(this);
                    selected.tab('show');
                    that.selectTab(e.target.id);
                });
                this.showTimelimitConfig();
            },

            selectTab : function(targetId) {
                switch (targetId) {
	                case "timelimit-info-a":
	                    this.showTimelimitConfig();
	                    break;
	                case "global-config-info-a":
	                    this.showGlobalConfig();
	                    break;
                }
            },

            showTimelimitConfig : function() {
            	if (!_s.isUn(this.timeLimitConfigView)) {
            		this.timeLimitConfigView.unbind();
            		this.timeLimitConfigView.remove();
            		this.timeLimitConfigView = undefined;
            	}
            	var that = this;
        		var c = new TimeLimitConfig();
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.$('div.modal-dialog:first'));
        		c.fetch({
        			success: function(model, response) {
                        spinner.stop();
                        var view = new TimeLimitConfigView({model:model});
                        view.parentView = that;
                        that.$('div#timelimit-info').append(view.$el);
                        view.render();
                        that.timeLimitConfigView = view;
                        that.currentTab = that.timeLimitConfigView; 
                    },
                    
                    error: function(model, response) {
                    	spinner.stop();
    					that.defaultErrorHandler(model, response);
                    },	
        		});
            },

            showGlobalConfig : function(){
            	if (!_s.isUn(this.globalConfigView)) {
            		this.globalConfigView.unbind();
            		this.globalConfigView.remove();
            		this.globalConfigView = undefined;
            	}
            	var that = this;
        		var c = new GlobalConfig();
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.$('div.modal-dialog:first'));
        		c.fetch({
        			success: function(model, response) {
                        spinner.stop();
                        var view = new GlobalConfigView({model:model});
                        view.parentView = that;
                        that.$('div#global-config-info').append(view.$el);
                        view.render();
                        that.globalConfigView = view;
                        that.currentTab = that.globalConfigView; 
                    },
                    
                    error: function(model, response) {
                    	spinner.stop();
    					that.defaultErrorHandler(model, response);
                    },	
        		});
            },
            
            readonlyRender : function() {
            },
            
            save : function() {
            	if (!_s.isUn(this.currentTab)) {
            		this.currentTab.save();
            	}
            },

        });

        return SystemConfigDialogView;
    }
);
