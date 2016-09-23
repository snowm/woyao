define([ 'text!bb/templates/index/exception_order_main.html', 
         'bb/models/local/order', 
         'bb/views/index/exception_order_grid', 'bb/views/base/base_container_content',
         'bb/views/base/bootbox_wrapper'], 
	function(OrderMainTemplate, 
			LocalOrder, 
			OrdersGridView, BaseContainerContentView, 
			BootboxWrapperView) {
	
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
				this.gridView.name = 'index_exceptionOrderGrid';
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
