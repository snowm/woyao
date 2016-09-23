define([ 'text!bb/templates/channel/channel_product_main.html', 'bb/views/base/base_meta', 
         'bb/models/channel/channel_product', 'bb/collections/meta/misps', 'bb/collections/meta/provinces', 'bb/collections/channel/channels', 
         'bb/views/channel/channel_product_grid', 'bb/views/base/base_container_content', 'bb/views/channel/channel_product_content_dialog', 'bb/views/channel/select_channel_dialog' 
         ], 
	function(ChannelProductMainTemplate, BaseMetaInfo, 
			ChannelProduct, MISPS, Provinces, Channels, 
			ChannelProductsGridView, BaseContainerContentView, ChannelProductContentDialogView, SelectChannelDialogView) {
	
		var ChannelProductMainView = BaseContainerContentView.extend({
			gridView : null,
			selectChannelDialogView : null, 
			createDialogView : null, 
			channels : new Channels(),
			metaInfo : null, 
			misps : null,
			provinces : null, 
			attributes:{},
			events : {
				"click div#channel-product-p #filter-p button#query-bt" : "query",
				"click div#channel-product-p #filter-p button#create-bt" : "showCreateDialog",
			},

			init_ext : function() {
				this.metaInfo = new BaseMetaInfo();
				this.misps = this.metaInfo.misps;
				this.provinces = this.metaInfo.provinces;
				this.gridView = new ChannelProductsGridView({
					ext_opt:{
						overlayTarget:'#channel-product-p > .panel-body',
						methodType:'POST',
						misps: this.misps,
						provinces: this.provinces,
					}
				});
				this.gridView.name = 'channelProductsGrid';
				this.gridView.parentView = this;
			},

			render : function() {
				this.$el.append(_.template(ChannelProductMainTemplate));
				this.refreshFilter();
				this.$('#channel-product-p > .panel-body > .container-fluid').append(this.gridView.$el);
				this.gridView.render();
				return this;
			},

			query : function() {
				this.gridView.query();
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
					success: (function (model, response) {
						model.each(function (m){
							that.$("#filter_channel").append("<option value='" + m.get('id') + "'>" + m.get('name') + "</option>");
						});
					}),
					error: (function (model, response) {
						that.defaultErrorHandler(model, response);
					})
				});
				
			},
			
			showCreateDialog : function() {
				this.selectChannelDialogView = new SelectChannelDialogView();
				this.selectChannelDialogView.parentView = this;
				this.selectChannelDialogView.render();
	            $('#modals-container').append(this.selectChannelDialogView.$el);
	            var that = this;
	            this.selectChannelDialogView.chooseCallback = function(selected) {
					var newChannelProduct = new ChannelProduct();
					newChannelProduct.set('channelId', selected.get('id'));
					newChannelProduct.set('channelName', selected.get('name'));
					newChannelProduct.set('channelType', selected.get('type'));
					that.createDialogView = new ChannelProductContentDialogView({
						model:newChannelProduct, 
						misps:that.misps, 
						provinces:that.provinces,
					});
					that.createDialogView.parentView = that;
					that.createDialogView.render();
		            $('#modals-container').append(that.createDialogView.$el);
	            }
			},
			showed : function() {
				this.gridView.query();
			},
		});

		return ChannelProductMainView;
});
