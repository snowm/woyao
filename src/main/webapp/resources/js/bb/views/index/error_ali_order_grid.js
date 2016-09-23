define([ 'text!bb/templates/index/error_ali_order_table.html', 'text!bb/templates/ali/order_grid_op.html',   
	'bb/collections/ali/orders', 'bb/views/base/base_grid', 'bb/views/ali/order_content_dialog'], 
		function(AliOrderTableTemplate, AliOrderGridOpTemplate, 
			AliOrders, BaseGridView, AliOrderContentDialogView) {

	var ExceptionOrderGridView = BaseGridView.extend({
        intervalObj : undefined,
		name : 'local_orders',
		
		events: {
			"click table #ali-order-ops a[name='look-a']" : "showLookDialog",
			"click table #ali-order-ops a[name='process-a']" : "markErrorProcessed",
		},
		
		init_ext : function() {
			_.extend(this.opt.paginator_opt, 
				{
					size: 'small',
					numberOfPages : 4,
					page_select : true,
					info_enable : false,
				}
			);
			this.opt.url = 'aliOrder/searchErrorOrders';
			this.bindRefresh =  _.bind(this.refreshCurrentPage, this);
		},

		newPaginationBean: function() {
			this.model = new AliOrders();
			this.model.pageSize = 10;
			return this.model;
		},
		searchCallBack : {
			error: function() {
				this.stopAutoRefresh();
			}
		},

		renderTables : function() {
			var template = _.template(AliOrderTableTemplate);
			var html = template();
			this.$el.append(html);
		},

		search : function(paginationBean) {
			this.stopInterval(this.intervalObj);
			this._search(paginationBean);
			this.intervalObj = this.startInterval(this.bindRefresh, 60000);
		},
		
		stopAutoRefresh : function() {
			this.stopInterval(this.intervalObj);
		},

	    stopInterval : function(id) {
			if (!_s.isUn(id)) {
				window.clearInterval(id);
			}
		},
		startInterval : function(func, interval) {
			var newId = window.setInterval(func, interval);
			return newId;
		},
		
		constructTable : function(){
			var that = this;
        	var op_template = _.template(AliOrderGridOpTemplate);
			var table = this.$('table').DataTable({
				paging: false,
				searching: false,
				info: false,
				ordering: false,
				intervalObj : null, 
				select: {
					style: 'single'
				},
				columns: [
				            { data: "id" },
				            { data: "tbOrderNo"},
				            { data: "spuId"},
				            { data: "accountVal" },
				            { data: "unitPrice" },
				            { data: "inCorrectReason" },
				            { data: "errorProcessed" },
				            { data: "timeStart" },
				            { data: "creationDate" },
				            { data: "lastModifiedDate" },
				            { data: "enabled" },
				            { data: "id" }
				        ],
				columnDefs: [
					{
						render: function ( data, type, row ) {
							var rendered = data;
							if(_s.isUn(data)) {
								rendered = '';
							}
							return rendered;
						},
						targets: 5
					},
					{
						render: function ( data, type, row, context ) {
		                	var rowsCount = that.model.length;
		                	var rowIdx = context.row;
		                	var isUp = (rowIdx > 6) && (rowIdx + 6 > rowsCount);
							var isError = row.inCorrectOrder && !row.errorProcessed;
							var rendered = op_template({model:row, isError:isError, isUp: isUp});
							return rendered;
						},
						targets: 11
					},
					{
						orderable: false,
						targets: '_all'
					}
				],
			});
		    return table;
		},
		
		showLookDialog: function(e) {
			var t = this.getModelByOpElement(e);
			var that = this;
			var spinner = new MySpinner({scale:0.25, blockUI: true});
			spinner.start(this.getOverlayEl());
			t.fetch({
				success: (function (model, response) {
					spinner.stop();
					var dialog = new AliOrderContentDialogView({
						model:model,
					});
					dialog.parentView = that;
					dialog.render();
		            $('#modals-container').append(dialog.$el);
				}),
				error: function(model, response) {
					spinner.stop();
					that.defaultErrorHandler(model, response);
				},
			});
		},

		markErrorProcessed : function(e) {
			this._doSyncOp(e, 'markErrorProcessed', function(model, json){
				model.set('errorProcessed', json.errorProcessed);
			});
		},
		
	});

	return ExceptionOrderGridView;
});
