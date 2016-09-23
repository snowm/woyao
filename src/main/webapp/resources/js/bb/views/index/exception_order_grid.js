define([ 'text!bb/templates/index/exception_order_table.html', 'text!bb/templates/local/order_grid_op.html',   
	'bb/collections/local/orders', 'bb/views/base/base_grid', 'bb/views/local/order_content_dialog', 
	'bb/views/base/bootbox_wrapper', 
	'bootstrap-paginator'], 
		function(LocalOrderTableTemplate, LocalOrderGridOpTemplate, 
			LocalOrders, BaseGridView, LocalOrderContentDialogView, 
			BootboxWrapperView) {

	var ExceptionOrderGridView = BaseGridView.extend({
        intervalObj : undefined,
		name : 'local_orders',
		
		events: {
			// "click table #local-order-ops button[name='refresh-bt']" : "query",
			"click table #local-order-ops a[name='look-a']" : "showLookDialog",
			"click table #local-order-ops a[name='enable-a']" : "enable",
			"click table #local-order-ops a[name='disable-a']" : "disable",
			"click table #local-order-ops a[name='success-a']" : "markSuccess",
			"click table #local-order-ops a[name='fail-a']" : "markFail",
			"click table #local-order-ops a[name='sync-a']" : "markSync",
			"click table #local-order-ops a[name='unsync-a']" : "markUnSync",
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
			this.opt.url = 'localOrder/searchExceptionOrders';
			this.bindRefresh =  _.bind(this.refreshCurrentPage, this);
		},

		newPaginationBean: function() {
			this.model = new LocalOrders();
			this.model.pageSize = 10;
			return this.model;
		},
		searchCallBack : {
			error: function() {
				this.stopAutoRefresh();
			}
		},

		renderTables : function() {
			var template = _.template(LocalOrderTableTemplate);
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
        	var template = _.template(LocalOrderGridOpTemplate);
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
		            { data: "aliOrder.tbOrderNo"},
		            { data: "localProductCode" },
		            { data: "step" },
		            { data: "orderStatus" },
		            { data: "aliOrder.timeStart" },
		            { data: "aliOrder.timeLimit" },
		            { data: "sentCallbackToAli" },
		            { data: "lastModifiedDate" },
		            { data: "enabled" },
		            { data: "errorCode" },
		            { data: "failReason" },
		            { data: "id" }
		        ],
				columnDefs: [
					{
						render: function ( data, type, row ) {
					        var rendered = _c.processStepMap[data];
					        return rendered;
						},
				        targets: 3
					},
					{
						render: function ( data, type, row ) {
					        var rendered = _c.localOrderStatusMap[data];
							return rendered;
						},
						targets: 4
					},
					{
		                render: function ( data, type, row, context ) {
		                	var rowsCount = that.model.length;
		                	var rowIdx = context.row;
		                	var isUp = (rowIdx > 6) && (rowIdx + 6 > rowsCount);
		                	var markSuccess, markFail, markSync, markUnsync = false;
		                	if (row.orderStatus == 'FAILED' || row.orderStatus == 'SUCCESS' || row.orderStatus == 'TIMEOUT') {
		                		markUnsync = row.sentCallbackToAli;
		                		markSync = !row.sentCallbackToAli;
		                	}
		        			var rendered = template({model:row, isUp:isUp, markSuccess:markSuccess, markFail:markSuccess, markSync:markSync, markUnsync:markUnsync});
		                    return rendered;
		                },
	                	targets: 12
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
					var dialog = new LocalOrderContentDialogView({
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

		enable: function(e) {
			this._doSyncOp(e, 'enable', function(model, json){
				model.set('enabled', json.enabled);
			});
		},

		_disable: function(e) {
			this._doSyncOp(e, 'disable', function(model, json){
				model.set('enabled', json.enabled);
			});
		},

		markSuccess : function(e) {
			var that = this;
			this.bootboxWrapper.confirm({
				message:"确定要将此订单标记为成功？这将会向淘宝反馈成功消息！",
				confirmCallback: function() {
					that._markSuccess.apply(that, [e]);
				},
			});
		},
		
		_markSuccess: function(e) {
			this._doSyncOp(e, 'markSuccess', function(model, json){
				model.set('orderStatus', json.orderStatus);
			});
		},
		markFail : function(e) {
			var that = this;
			this.bootboxWrapper.confirm({
				message:"确定要将此订单标记为失败？这将会向淘宝反馈失败消息！",
				confirmCallback: function() {
					that._markFail.apply(that, [e]);
				},
			});
		},
		_markFail: function(e) {
			this._doSyncOp(e, 'markFail', function(model, json){
				model.set('orderStatus', json.orderStatus);
			});
		},

		markSync: function(e) {
			this._doSyncOp(e, 'markSync', function(model, json){
				model.set('sentCallbackToAli', json.sentCallbackToAli);
			});
		},

		markUnSync: function(e) {
			this._doSyncOp(e, 'markUnSync', function(model, json){
				model.set('sentCallbackToAli', json.sentCallbackToAli);
			});
		},
	});

	return ExceptionOrderGridView;
});
