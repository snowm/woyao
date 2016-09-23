define([ 'text!bb/templates/channel/order_content_dialog.html', 
         'bb/models/channel/order', 
         'bb/views/channel/order_content_base', 
         'bb/views/base/base_content_dialog' ],
    function (ChannelOrderContentDialogTemplate,  
    		ChannelOrder, 
    		ChannelOrderContentBaseView, 
    		BaseContentDialogView) {
        var ChannelOrderContentDialogView = BaseContentDialogView.extend({
            template : _.template(ChannelOrderContentDialogTemplate),
            currentTab : null, 
            baseInfoView : null, 
            aliOrderInfoView : null, 
            localOrderInfoView : null, 

            initExt : function(options) {
            	this.readonly= true;
            },
            
            renderEl : function() {
                this.$el.html(this.template(this.model.toJSON()));
                var that = this;
                this.$('div.modal-body >div > ul > li > a').on('shown.bs.tab', function(e){
                    that.selectTab(e.target.id); 
                });
                this.$('div.modal-body >div > ul > li > a').click( function (e) {
                	e.preventDefault();
                    var selected = $(this);
                    selected.tab('show');
                });
                this.showBaseInfo();
            },

            selectTab : function(targetId) {
                switch (targetId) {
	                case "base-info-a":
	                    this.showBaseInfo();
	                    break;
	                case "ali-order-info-a":
	                	this.showAliOrderInfo();
	                    break;
	                case "local-order-info-a":
	                	this.showLocalOrderInfo();
	                    break;
                }
            },

            showTab : function(m, successFunc, errorFunc, errM){
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.$('div.modal-dialog:first'));
    			this.model.fetch({
    				success: (function (model, response) {
    					spinner.stop();
    					successFunc(model);
    				}),
    				error: function(model, response) {
    					spinner.stop();
    					that.defaultErrorHandler(model, response, errorFunc.apply(that,[errM]));
    				},
    			});
            },
            
            showBaseInfo : function(){
            	if (!_s.isUn(this.baseInfoView)) {
            		this.baseInfoView.unbind();
            		this.baseInfoView.remove();
            		this.baseInfoView = undefined;
            	}
            	var that = this;
            	var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.$('div.modal-dialog:first'));
    			this.model.fetch({
    				success: (function (model, response) {
    					spinner.stop();
    					that._showBaseInfo(model);
    				}),
    				error: function(model, response) {
    					spinner.stop();
    					that.defaultErrorHandler(model, response);
    				},
    			});
            },

            _showBaseInfo : function(m) {
        		var view = new ChannelOrderContentBaseView({model: m});
        		view.parentView = this;
        		this.$('div#channel-order-base-info').append(view.$el);
        		view.render();
        		if (this.readonly) {
        			view.readonlyRender();
        		}
        		this.baseInfoView = view;
                this.currentTab = this.baseInfoView; 
            },
            
            showAliOrderInfo : function() {
            	if (!_s.isUn(this.aliOrderInfoView)) {
            		this.aliOrderInfoView.unbind();
            		this.aliOrderInfoView.remove();
            		this.aliOrderInfoView = undefined;
            	}
        		var aliOrderId = this.model.get('aliOrderId');
        		var m = new AliOrder();
        		if (_s.isUn(aliOrderId)) {
        			this._showAliOrderInfo(m);
        			return;
        		}
        		m.set('id', aliOrderId);
        		var that = this;
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.$('div.modal-dialog:first'));
    			m.fetch({
    				success: (function (model, response) {
    					spinner.stop();
    					that._showAliOrderInfo(model);
    				}),
    				error: function(model, response) {
    					spinner.stop();
    					that.defaultErrorHandler(model, response);
    				},
        		});
            },
            
            _showAliOrderInfo : function(m) {
            	var view = new AliOrderContentBaseView({
        			model: m,
                });
            	view.parentView = this;
        		$('div#channel-ali-order-info').append(view.$el);
            	view.render();
        		if (this.readonly) {
        			view.readonlyRender();
        		}
        		this.aliOrderInfoView = view;
                this.currentTab = view; 
            },

            showLocalOrderInfo : function() {
            	if (!_s.isUn(this.localOrderInfoView)) {
            		this.localOrderInfoView.unbind();
            		this.localOrderInfoView.remove();
            		this.localOrderInfoView = undefined;
            	}
        		var localOrderId = this.model.get('localOrderId');
        		var m = new LocalOrder();
        		if (_s.isUn(localOrderId)) {
        			this._showLocalOrderInfo(m);
        			return;
        		}
        		m.set('id', localOrderId);
        		var that = this;
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.$('div.modal-dialog:first'));
    			m.fetch({
    				success: (function (model, response) {
    					spinner.stop();
    					that._showLocalOrderInfo(model);
    				}),
    				error: function(model, response) {
    					spinner.stop();
    					that.defaultErrorHandler(model, response);
    				},
        		});
            },
            
            _showLocalOrderInfo : function(m) {
            	var view = new LocalOrderContentBaseView({
        			model: m,
                });
            	view.parentView = this;
        		$('div#channel-local-order-info').append(view.$el);
            	view.render();
        		if (this.readonly) {
        			view.readonlyRender();
        		}
        		this.localOrderInfoView = view;
                this.currentTab = view; 
            },
            
            readonlyRender : function() {
            	if (!_s.isUn(this.baseInfoView)) {
            		this.baseInfoView.readonlyRender();
            	}
            	if (!_s.isUn(this.localOrderInfoView)) {
            		this.localOrderInfoView.readonlyRender();
            	}
            	if (!_s.isUn(this.localOrderInfoView)) {
            		this.channelOrderInfoView.readonlyRender();
            	}
            },
            
            save : function() {
            	if (!_s.isUn(this.currentTab)) {
            		this.currentTab.save();
            	}
            },

        });

        return ChannelOrderContentDialogView;
    }
);
