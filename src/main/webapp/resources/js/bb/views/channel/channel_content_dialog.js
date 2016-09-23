define([ 'text!bb/templates/channel/channel_content_dialog.html',
         'bb/views/channel/channel_content_base', 'bb/views/channel/thirdparty/channel_content_ext_shdahan', 'bb/views/channel/thirdparty/channel_content_ext_ahyt',
         'bb/views/base/base_component' ],
    function (ChannelContentDialogTemplate, 
    		ChannelContentBaseView, ChannelContentExtShdahanView, ChannelContentExtAhytView, 
    		BaseComponent) {

        var ChannelContentDialogView = BaseComponent.extend({
            modal_dg : null,
            template : _.template(ChannelContentDialogTemplate),
            channel : null,
            baseInfoView : null, 
            thirdPartyInfoView : null,

            events: {
                "click #save-btn" : "save",
            },
            initialize : function() {
            },
            
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
                var that = this;
                this.$('li > a').click( function (e) {
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
            		this.baseInfoView = new ChannelContentBaseView({model:this.model});
            		this.baseInfoView.parentView = this;
            		this.baseInfoView.render();
            		this.$('div#channel-base-info > .container-fluid').append(this.baseInfoView.$el);
            	}
            },
            
            showThirdPartyInfo : function() {
            	var type = this.baseInfoView.$('#channel_type').val();
            	if(_s.isUn(this.thirdPartyInfoView) || this.thirdPartyInfoView.type != type) {
            		if (!_s.isUn(this.thirdPartyInfoView)) {
            			this.thirdPartyInfoView.unbind();
            			this.thirdPartyInfoView.remove();
            			this.thirdPartyInfoView = undefined;
            		}
            		switch (type) {
	            		case _c.channel_type_shdhst:
		            		this.thirdPartyInfoView = new ChannelContentExtShdahanView({model:this.model});
		            		break;
		        		case _c.channel_type_ahyt:
		            		this.thirdPartyInfoView = new ChannelContentExtAhytView({model:this.model});
		            		break;
	            	}
            		if (!_s.isUn(this.thirdPartyInfoView)) {
	            		this.thirdPartyInfoView.parentView = this;
	            		this.thirdPartyInfoView.render();
	            		this.$('div#channel-thirdparty-info  > .container-fluid').append(this.thirdPartyInfoView.$el);
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
	            	var configExt = this.thirdPartyInfoView.getExtInfo();
	            	_.extend(this.model.get('config'), configExt);
	            }
            },

            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },
            
            saveData : function() {
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

        return ChannelContentDialogView;
    }
);
