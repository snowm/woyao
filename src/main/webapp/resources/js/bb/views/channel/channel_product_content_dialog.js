define([ 'text!bb/templates/channel/channel_product_content_dialog.html', 'text!bb/templates/channel/thirdparty/product_content_ext_nothing.html',
         'bb/views/channel/channel_product_content_base', 'bb/views/channel/thirdparty/product_content_ext_ahyt', 
         'bb/views/base/base_component' ],
    function (ChannelProductContentDialogTemplate, ThirdpartyProductNothingTemplate, 
    		ChannelProductContentBaseView, ChannelProductContentExtAhytView, 
    		BaseComponent) {
        var ChannelProductContentDialogView = BaseComponent.extend({
            modal_dg : null,
            template : _.template(ChannelProductContentDialogTemplate),
            channelProduct : null,
            baseInfoView : null, 
            thirdPartyInfoView : null,
    		misps : null,
    		provinces : null, 

            events: {
                "click #save-btn" : "save",
            },
            
            initialize : function() {
            	var options = arguments[0];
            	this.misps = options.misps;
            	this.provinces = options.provinces;
            },
            
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
                var that = this;
                this.$('div.modal-body >div > ul > li > a').click( function (e) {
                	e.preventDefault();
                    var selected = $(this);
                    selected.tab('show');
                    that.selectTab(e.target.id);
                });
                this.showBaseInfo();
                this.renderModalDialog();
                return this;
            },

            selectTab : function(targetId) {
                switch (targetId) {
	                case "base-info-a":
	                    this.showBaseInfo();
	                    break;
	                case "thirdparty-info-a":
	                    this.showThirdPartyInfo();
	                    break;
                }
            },
            
            showBaseInfo : function(){
            	if (_s.isUn(this.baseInfoView)) {
            		this.baseInfoView = new ChannelProductContentBaseView({
            			model:this.model,
            			misps: this.misps,
                    	provinces: this.provinces,
                    });
            		this.baseInfoView.parentView = this;
            		this.baseInfoView.render();
            		this.$('div#channel-product-base-info').append(this.baseInfoView.$el);
            	}
            },
            
            showThirdPartyInfo : function() {
            	var type = this.model.get('channelType');
            	if (_s.isUn(this.thirdPartyInfoView) || this.thirdPartyInfoView != type) {
            		if (!_s.isUn(this.thirdPartyInfoView)) {
            			this.thirdPartyInfoView.unbind();
            			this.thirdPartyInfoView.remove();
            			this.thirdPartyInfoView = undefined;
            		}
            		switch (type) {
	            		case _c.channel_type_shdhst:
		            		this.$('div#channel-product-thirdparty-info').append(_.template(ThirdpartyProductNothingTemplate));
		            		break;
		        		case _c.channel_type_ahyt:
		            		this.thirdPartyInfoView = new ChannelProductContentExtAhytView({model:this.model});
		            		break;
	            	}
            		if (!_s.isUn(this.thirdPartyInfoView)) {
	            		this.thirdPartyInfoView.parentView = this;
	            		this.thirdPartyInfoView.render();
	            		this.$('div#channel-product-thirdparty-info').append(this.thirdPartyInfoView.$el);
            		}
            	}
            },
            
            renderModalDialog : function() {
                this.modal_dg = this.$('div.modal.fade');
                this.modal_dg.modal({
                  backdrop : 'static',
                });
                var that = this;
                this.modal_dg.on('hidden.bs.modal', function (event) {
                    that.clearDialog();
                });

                _s.checkBootstrapEmbeddedModal(this.modal_dg);
            },

            clearDialog: function() {
            	this.unbind();
            	this.remove();
            },

            save : function() {
                this.changeValues();
                this.saveData();
            },

            changeValues : function() {
            	this.model = this.baseInfoView.getModel();
            	if (!_s.isUn(this.thirdPartyInfoView)) {
	            	var extInfo = this.thirdPartyInfoView.getExtInfo();
	            	for (var item in extInfo) { 
	            		this.model.set(item, extInfo[item]);
	            	}  
	            }
            },

            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },
            
            saveData:function(){
                var that = this;
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.$('div.modal-content'));
    			var isNew = _s.isUn(this.model.get('id'));
                this.model.save(this.model.attributes, {
                    success: function(model, response) {
                        spinner.stop();
                        that.bootboxWrapper.info("保存成功！", {
                        	callback: function(){
                        		var col = that.parentView.model;
                        		if (isNew) {
                        			col = that.parentView.gridView.model;
                        		}
                    			var length = col.length;
                    			col.add(model, {
	                        		at: 0,
	                        		merge:true,
	                        	});
                    			col.trigger("refresh");
                        	}
                        });
                    },
                    
                    error: function(model, response) {
                    	spinner.stop();
    					that.defaultErrorHandler(model, response);
                    },
                });
            },

        });

        return ChannelProductContentDialogView;
    }
);
