define([ 'text!bb/templates/channel/channel_product_search_dialog.html',
         'bb/collections/meta/misps', 'bb/collections/meta/provinces', 'bb/collections/channel/channels', 
         'bb/views/channel/channel_product_search_grid', 'bb/views/base/base_meta',  
         'bb/views/base/base_component' ],
    function (ChannelProductSearchDialogTemplate, 
    		MISPS, Provinces, Channels, 
    		ChannelProductSearchGridView, BaseMetaInfo, 
    		BaseComponent) {
        var ChannelProductSearchDialogView = BaseComponent.extend({
            modal_dg : null,
            template : _.template(ChannelProductSearchDialogTemplate),
            gridView : null, 
			channels : new Channels(),
			metaInfo : null, 
			misps : null,
			provinces : null, 
            selected : undefined,

            events: {
                "click div.modal-footer button#choose-bt" : "choose",
				"click div#channel-product-p #filter-p button#query-bt" : "query",
            },
            
			initialize : function() {
				this.metaInfo = new BaseMetaInfo();
				this.misps = this.metaInfo.misps;
				this.provinces = this.metaInfo.provinces;
				this.gridView = new ChannelProductSearchGridView({
					ext_opt:{
						overlayTarget: 'div.modal-dialog:first',
						methodType:'POST',
						channels: this.channels,
						misps: this.misps,
						provinces: this.provinces,
					}
				});
				this.gridView.name = 'searchChannelProductsDialogGrid';
				this.gridView.parentView = this;
			},

            render : function() {
                this.$el.html(this.template());
				this.$('div#channel-product-p .panel-body > .container-fluid').append(this.gridView.$el);
                this.renderModalDialog();
                return this;
            },

            renderModalDialog : function() {
                this.modal_dg = this.$('div.modal.fade');
                this.modal_dg.modal({
                	backdrop : 'static',
                });
                _s.checkBootstrapEmbeddedModal(this.modal_dg);
                
                this.refreshFilter();
                var that = this;
                this.modal_dg.on('hidden.bs.modal', function (event) {
                    that.close();
                    if (!_s.isUn(that.selected)) {
                    	that.chooseCallback(that.selected);
                    }
                });

                this.modal_dg.on('shown.bs.modal', function (event) {
					that.gridView.render();
                	that.gridView.query();
                });

            },

			query : function() {
				this.gridView.query();
			},
			
            choose : function() {
            	this.modal_dg.modal('hide');
            	this.selected = this.gridView.getSelected();
            },

            chooseCallback : function(selected) {
            },
            
			refreshFilter : function() {
				this.metaInfo.fetchMISP(this.$("#filter_misp"));
				this.metaInfo.fetchProvinces(this.$("#filter_province"));

				var that = this;
				var searchData = {
						pageNumber : -1,
						pageSize : -1,
					};
				this.channels.fetch({
					type: 'POST',
					data: searchData,
					success: function (model, response) {
						model.each(function (m){
							that.$("#filter_channel").append("<option value='" + m.get('id') + "'>" + m.get('name') + "</option>");
						});
					},
					error: function (model, response) {
						that.defaultErrorHandler(model, response);
					}
				});
				
			},

        });

        return ChannelProductSearchDialogView;
    }
);
