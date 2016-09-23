define([ 'text!bb/templates/channel/order_table.html', 'text!bb/templates/channel/order_grid_op.html',   
	'bb/collections/channel/orders', 'bb/views/base/base_grid', 'bb/views/channel/order_content_dialog' ], 
		function(ChannelOrderTableTemplate, ChannelOrderGridOpTemplate, 
			ChannelOrders, BaseGridView, ChannelOrderContentDialogView ) {

	var ChannelOrdersGridView = BaseGridView.extend({
		name : 'channel_orders',
		
		events: {
			"click table #channel-order-ops a[name='look-a']" : "showLookDialog",
			"click table #channel-order-ops a[name='enable-a']" : "enable",
			"click table #channel-order-ops a[name='disable-a']" : "disable",
			"click table #channel-order-ops a[name='success-a']" : "markSuccess",
			"click table #channel-order-ops a[name='fail-a']" : "markFail",
		},
		
		init_ext : function() {
			_.extend(this.opt.paginator_opt, 
				{
					size: 'small',
					numberOfPages : 4,
					page_select : true,
					info_enable : true,
				}
			);
		},

		newPaginationBean: function() {
			this.model = new ChannelOrders();
			this.model.pageSize = 20;
			return this.model;
		},

		renderTables : function() {
			var template = _.template(ChannelOrderTableTemplate);
			var html = template();
			this.$el.append(html);
		},

		searchCallBack : {
			success : function() {
				var l = this.table.rows().data().length;
				for(var i = 0;i<l;i++){
					var row = this.table.row(i);
					if (!row) {return}
					var data = row.data();
					var id = data.id;
					var orderStatus = data.orderStatus;
					if (orderStatus == 'FAILURE' || orderStatus == 'TIMEOUT') {
						this.$(row.node()).addClass('danger');
					} else if (orderStatus == 'SUCCESS'){
						this.$(row.node()).addClass('success');
					} else if (orderStatus == 'SENT'){
						this.$(row.node()).addClass('info');
					}
				}
			}
		},
		constructTable : function(){
			var that = this;
        	var op_template = _.template(ChannelOrderGridOpTemplate);
			var table = this.$('table').DataTable({
				paging: false,
				searching: false,
				info: false,
				select: {
					style: 'single'
				},
				scrollY: '600px',
				columns: [
		            { data: "id" },
		            { data: "tbOrderNo"},
		            { data: "accountVal" },
		            { data: "localProductCode" },
		            { data: "channelName" },
		            { data: "channelProductName" },
		            { data: "orderStatus" },
		            { data: "manually" },
		            { data: "creationDate" },
		            { data: "lastModifiedDate" },
		            { data: "enabled" },
		            { data: "id" }
		        ],
		        order : [[8, 'desc']],
				columnDefs: [
					{ responsivePriority: 2, targets: [4,5] },
					{
						render: function ( data, type, row ) {
					        var rendered = _c.channelOrderStatusMap[data];
							return rendered;
						},
						width: "67px",
						responsivePriority: 1,
						targets: 6
					},
					{ 	width: "160px", 
						responsivePriority: 3,
						orderable : true,
//						orderSequence : ['desc', 'asc'],
						targets: 8 
					},
					{ width: "160px", responsivePriority: 3, targets: 9 },
					{ width: "40px", responsivePriority: 2, targets: 10 },
					{
		                render: function ( data, type, row, context ) {
		                	var rowsCount = that.model.length;
		                	var rowIdx = context.row;
		                	var isUp = (rowIdx > 6) && (rowIdx + 6 > rowsCount);
		                	var markable = false;
		                	if (row.enabled == true && row.orderStatus == 'SENT') {
		                		markable = true;
		                	}
		        			var rendered = op_template({model:row, isUp:isUp, markable:markable});
		                    return rendered;
		                },
						width: "90px",
	                	targets: 11
	                },
					{
		                orderable: false,
	                	targets: '_all'
	                }
	            ],
			});
			this.$('table').on('order.dt', function(e) {
				e.preventDefault();
				var order = table.order();
				var column = order[0][0];
				
				if (that.orderTriggerSearch) {
					that.orderTriggerSearch = false;
					return;
				}
				
				if (column == 8 && !that.orderTriggerSearch) {
					that.orderTriggerSearch = true;
					that.refreshCurrentPage();
				}
			});
			this.$('table').on( 'draw.dt', function () {
				var l = that.table.rows().data().length;
				for(var i = 0;i<l;i++){
					var row = that.table.row(i);
					if (!row) {return}
					var data = row.data();
					var id = data.id;
					var orderStatus = data.orderStatus;
					if (orderStatus == 'FAILURE' || orderStatus == 'TIMEOUT') {
						that.$(row.node()).addClass('danger');
					} else if (orderStatus == 'SUCCESS'){
						that.$(row.node()).addClass('success');
					} else if (orderStatus == 'SENT'){
						that.$(row.node()).addClass('info');
					}
				}
			});
		    return table;
		},
		
		orderTriggerSearch : false,

		createSearchData: function() {
			var order = this.table.order();
			var column = order[0][0];
			var seq = '';
			if (column == 8) {
				seq = order[0] [1];
			}

			return {
				tbOrderNo: this.parentView.$('div#channel-order-p #filter-p #filter_tbOrderNo').val(),
				spuId: this.parentView.$('div#channel-order-p #filter-p #filter_spuId').val(),
				accountVal: this.parentView.$('div#channel-order-p #filter-p #filter_accountVal').val(),
				localProductCode: this.parentView.$('div#channel-order-p #filter-p #filter_localProductCode').val(),
				localOrderStatus: this.parentView.$('div#channel-order-p #filter-p #filter_localOrderStatus').val(),
				channel: this.parentView.$('div#channel-order-p #filter-p #filter_channel').val(),
				creationFromDate: this.parentView.$('div#channel-order-p #filter-p #filter_creationFromDate').val(),
				creationToDate: this.parentView.$('div#channel-order-p #filter-p #filter_creationToDate').val(),
				lastModificationFromDate: this.parentView.$('div#channel-order-p #filter-p #filter_lastModificationFromDate').val(),
				lastModificationToDate: this.parentView.$('div#channel-order-p #filter-p #filter_lastModificationToDate').val(),
				enabled: this.parentView.$('div#channel-order-p #filter-p #filter_enabled').val(),
				manually: this.parentView.$('div#channel-order-p #filter-p #filter_manually').val(),
				idSeq : seq,
			};
		},
		
		showLookDialog: function(e) {
			var t = this.getModelByOpElement(e);
			var that = this;
			var spinner = new MySpinner({scale:0.25, blockUI: true});
			spinner.start(this.getOverlayEl());
			t.fetch({
				success: (function (model, response) {
					spinner.stop();
					var dialog = new ChannelOrderContentDialogView({
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
			var that = this;
			this.bootboxWrapper.confirm({
				message:"确定要启用此订单？对该订单的处理会正常接收！",
				confirmCallback: function() {
					that._enable.apply(that, [e]);
				},
			});
		},

		_enable: function(e) {
			this._doSyncOp(e, 'enable', function(model, json){
				model.set('enabled', json.enabled);
			});
		},

		disable: function(e) {
			var that = this;
			this.bootboxWrapper.confirm({
				message:"确定要停用此订单？对该订单的处理会全部停止！",
				confirmCallback: function() {
					that._disable.apply(that, [e]);
				},
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
				message:"确定要将此订单标记为失败？会重选渠道！",
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
	});

	return ChannelOrdersGridView;
});
