define([ 'text!bb/templates/channel/channel_product_table.html', 'text!bb/templates/channel/channel_product_grid_op.html',   
	'bb/collections/channel/channel_products', 'bb/views/base/base_grid', 'bb/views/channel/channel_product_content_dialog' ], 
		function(ChannelProductTableTemplate, ChannelProductGridOpTemplate, 
			ChannelProducts, BaseGridView, ChannelProductContentDialogView ) {

	var ChannelProductsGridView = BaseGridView.extend({
		misps : null,
		provinces : null, 
		name : 'channel_product',
		
		events: {
			"click table #channel-product-ops a[name='edit-a']" : "showEditDialog",
			"click table #channel-product-ops a[name='enable-a']" : "enable",
			"click table #channel-product-ops a[name='disable-a']" : "disable",
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
			this.misps = this.opt.misps;
			this.provinces = this.opt.provinces;
		},

		newPaginationBean: function() {
			this.model = new ChannelProducts();
			this.model.pageSize = 20;
			return this.model;
		},

		renderTables : function() {
			var template = _.template(ChannelProductTableTemplate);
			var html = template();
			this.$el.append(html);
		},

		constructTable : function(){
			var table = this.$('table').DataTable({
				paging: false,
				searching: false,
				info: false,
				ordering: false,
				select: {
					style: 'single'
				},
				scrollY: '600px',
				scrollCollapse: true,
				columns: [
		            { data: "id" },
		            { data: "name"},
		            { data: "channelName"},
		            { data: "size" },
		            { data: "mispName" },
		            { data: "provinceName" },
		            { data: "region" },
		            { data: "enabled" },
		            { data: "id" }
		        ],
				columnDefs: [
				    { width: '94px', targets : 2},
				    {
		                render: function ( data, type, row ) {
		        			var rendered = data + ' ' + row.sizeUnit;
		                    return rendered;
		                },
		                width : '80px',
	                	targets: 3
	                },
					{
		                render: function ( data, type, row ) {
							if (_s.isUn(data)) {
								return '';
							}
		                	var rendered = _c.regionsMap[data];
		                	return rendered;
		                },
		                width : '110px',
	                	targets: 6
	                },
					{
		                render: function ( data, type, row ) {
		                	var template = _.template(ChannelProductGridOpTemplate);
		        			var rendered = template(row);
		                    return rendered;
		                },
		                width : '85px',
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

		createSearchData: function() {
			return {
				name: this.parentView.$('div#channel-product-p #filter-p #filter_name').val(),
				channelId: this.parentView.$('div#channel-product-p #filter-p #filter_channel').val(),
				mispId: this.parentView.$('div#channel-product-p #filter-p #filter_misp').val(),
				provinceId: this.parentView.$('div#channel-product-p #filter-p #filter_province').val(),
				region: this.parentView.$('div#channel-product-p #filter-p #filter_region').val(),
				enabled: this.parentView.$('div#channel-product-p #filter-p #filter_enabled').val(),
			};
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
		
		showEditDialog: function(e) {
			var t = this.getModelByOpElement(e);
			var that = this;
			var spinner = new MySpinner({scale:0.25, blockUI: true});
			spinner.start(this.getOverlayEl());
			t.fetch({
				success: (function (model, response) {
					spinner.stop();
					var dialog = new ChannelProductContentDialogView({
						model:model,
						misps:that.misps, 
						provinces:that.provinces,
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
		
	});

	return ChannelProductsGridView;
});
