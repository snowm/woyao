define([ 'text!bb/templates/index/error_ali_order_main.html', 
         'bb/models/ali/order', 
         'bb/views/index/error_ali_order_grid', 'bb/views/base/base_container_content'], 
	function(OrderMainTemplate, 
			AliOrder, 
			OrdersGridView, BaseContainerContentView) {
	
		var OrderMainView = BaseContainerContentView.extend({
			className : '',
			gridView : null,
			events : {
				"click button#refresh-bt" : "queryOrders",
			},

			init_ext : function() {
				this.gridView = new OrdersGridView({
					ext_opt:{
						overlayTarget:'div.panel-body',
						methodType:'POST',
					}
				});
				this.gridView.name = 'index_errorAliOrderGrid';
				this.gridView.parentView = this;
			},

			render : function() {
				this.$el.append(_.template(OrderMainTemplate));
				this.$('div.panel-body > .container-fluid').append(this.gridView.$el);
				this.gridView.render();
				return this;
			},
			
			refreshExceptionOrder : function() {
				this.gridView.query();
			},
			showed : function() {
				this.gridView.query();
			},
			
			hidden : function() {
				this.gridView.stopAutoRefresh();
			},

			queryOrders : function() {
				this.gridView.query();
			},
			
		});

		return OrderMainView;
});
