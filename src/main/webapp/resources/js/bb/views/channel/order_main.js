define([ 'text!bb/templates/channel/order_main.html', 
         'bb/models/channel/order', 'bb/collections/channel/channels', 
         'bb/views/channel/order_grid', 'bb/views/base/base_container_content',  
         'bootstrap-datetimepicker'
         ], 
	function(ChannelOrderMainTemplate, 
			ChannelOrder, Channels, 
			ChannelOrdersGridView, BaseContainerContentView) {
		var ChannelOrderMainView = BaseContainerContentView.extend({
			className : 'container-fluid index',
			gridView : null,
			channels : new Channels(),
			events : {
				"click div#channel-order-p #filter-p button#query-bt" : "query",
				"click div#channel-order-p #filter-p span#filter_creationFromDate_remove" : "clearDate",
				"click div#channel-order-p #filter-p span#filter_creationToDate_remove" : "clearDate",
				"click div#channel-order-p #filter-p span#filter_lastModificationFromDate_remove" : "clearDate",
				"click div#channel-order-p #filter-p span#filter_lastModificationToDate_remove" : "clearDate",
			},

			init_ext : function() {
				this.gridView = new ChannelOrdersGridView({
					ext_opt:{
						overlayTarget:'#channel-order-p > .panel-body',
						methodType:'POST',
						misps: this.misps,
						provinces: this.provinces,
					}
				});
				this.gridView.name = 'channelOrdersGrid';
				this.gridView.parentView = this;
			},
			render : function() {
				this.$el.append(_.template(ChannelOrderMainTemplate));
				this.renderFilter();
				this.$('#channel-order-p > .panel-body > .container-fluid').append(this.gridView.$el);
				this.gridView.render();
				return this;
			},
			renderFilter : function() {
				this.$("div#channel-order-p #filter-p input.datepicker").datetimepicker(_c.default_option);
				this.refreshFilter();
			},
			clearDate : function(e) {
				console.log(this.$(e.currentTarget).attr('data-target'));
				var picker = this.$(e.currentTarget).parent().siblings('input');
				picker.val("");
				this.$(picker).datetimepicker('update');
			},

			query : function() {
				this.gridView.query();
			},
			
			refreshFilter : function() {
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
							that.$("div#channel-order-p #filter-p #filter_channel").append("<option value='" + m.get('id') + "'>" + m.get('name') + "</option>");
						});
					}),
					error: (function (model, response) {
						that.defaultErrorHandler(model, response);
					})
				});
				
			},
			showed : function() {
				this.gridView.query();
			},
		});

		return ChannelOrderMainView;
});
