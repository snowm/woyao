define([ 'text!bb/templates/channel/order_list_panel.html', 'text!bb/templates/channel/order_grid_op.html',   
	'bb/collections/channel/orders', 'bb/views/channel/order_content_dialog', 
	'bb/views/base/base_component', 
	'bootstrap-paginator'], 
		function(ChannelOrderListMainTemplate, ChannelOrderGridOpTemplate, 
			ChannelOrders, ChannelOrderContentDialogView, 
			BaseComponent) {

	var ChannelOrdersGridView = BaseComponent.extend({
		className:"container-fluid",
		template : _.template(ChannelOrderListMainTemplate),
		name : 'channel_orders',
		
		events: {
			"click table #channel-order-ops a[name='look-a']" : "showLookDialog",
			"click table #channel-order-ops a[name='enable-a']" : "enable",
			"click table #channel-order-ops a[name='disable-a']" : "disable",
			"click table #channel-order-ops a[name='success-a']" : "markSuccess",
			"click table #channel-order-ops a[name='fail-a']" : "markFail",
		},
		
		initialize : function() {
			var that = this;
			this.listenTo(this.model, 'refresh', function(){
				that.refreshTableData(that.table);
			});
		},

        render : function() {
			var html = this.template();
			this.$el.append(html);
			
			this.renderTable();
		},
		
		renderTable : function() {
			this.table = this.constructTable();
			this.refreshTableData(this.table);
			this.renderRows(this.table);
		},

		renderRows : function(table) {
			var l = table.rows().data().length;
			for(var i = 0;i<l;i++){
				var row = table.row(i);
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
		},
		constructTable : function(){
			var that = this;
        	var op_template = _.template(ChannelOrderGridOpTemplate);
			var table = this.$('table').DataTable({
				paging: false,
				searching: false,
				info: false,
				ordering: false,
				scrollY: '250px',
				select: {
					style: 'single'
				},
				columns: [
		            { data: "id" },
		            { data: "localProductCode" },
		            { data: "channelName" },
		            { data: "channelProductName" },
		            { data: "orderStatus" },
		            { data: "manually" },
		            { data: "lastModifiedDate" },
		            { data: "enabled" },
		            { data: "id" }
		        ],
				columnDefs: [
					{ responsivePriority: 1, targets: [1,2] },
					{ responsivePriority: 4, targets: 3 },
					{
						render: function ( data, type, row ) {
					        var rendered = _c.channelOrderStatusMap[data];
							return rendered;
						},
						width: "67px",
						responsivePriority: 4,
						targets: 4
					},
					{ width: "160px", responsivePriority: 3, targets: 6 },
					{ width: "40px", responsivePriority: 2, targets: 7 },
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
						width: "110px",
	                	targets: 8
	                },
					{
		                orderable: false,
	                	targets: '_all'
	                }
	            ],
			});
		    return table;
		},

		refreshTableData : function(table) {
			table.clear();
			table.rows.add(this.model.toJSON()).draw(false);
		},
		
		showLookDialog: function(e) {
			var id = $(e.target).data('id');
			var t = this.model.get(id);
			var that = this;
			var spinner = new MySpinner({scale:0.25, blockUI: true});
			spinner.start();
			t.fetch({
				success: (function (model, response) {
					spinner.stop();
					var dialog = new ChannelOrderContentDialogView({
						model:model,
					});
					dialog.parentView = this;
					dialog.render();
		            $('#modals-container').append(dialog.$el);
				}),
				error: function(model, response) {
					spinner.stop();
					that.defaultErrorHandler(model, response);
				},
			});
		},

		getModelByOpElement: function(e) {
			var id = $(e.target).attr('data-id');
			var t = this.model.get(id);
			return t;
		},
		
		_doSyncOp: function(e, opName, callback) {
			var t = this.getModelByOpElement(e);
			var that = this;
			var spinner = new MySpinner({scale:0.25, blockUI: true});
			spinner.start(this.parentView.$el);
            t.sync(opName, t, {
                success: function(model, response) {
                	spinner.stop();
                	callback.apply(that, [t, model, response]);
					that.bootboxWrapper.info('操作成功！', {
						callback: function(){
							that.model.trigger('refresh');
						}
					});
                },
                error: function(model, response) {
                	spinner.stop();
					that.defaultErrorHandler(model, response);
                }
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
