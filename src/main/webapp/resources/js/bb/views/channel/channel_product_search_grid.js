define([ 'text!bb/templates/channel/channel_product_search_table.html', 
	'bb/collections/channel/channel_products', 'bb/views/base/base_grid', 'bb/views/channel/channel_product_content_dialog'], 
		function(ChannelProductSearchTableTemplate, 
			ChannelProducts, BaseGridView, ChannelProductContentDialogView) {

	var ChannelProductSearchGridView = BaseGridView.extend({
		channels : null,
		misps : null,
		provinces : null, 
		name : 'channel_products',
		
		events: {
			"click table a[name='look-a']" : "look",
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
			this.channels = this.opt.channels;
			this.misps = this.opt.misps;
			this.provinces = this.opt.provinces;
		},

		newPaginationBean: function() {
			this.model = new ChannelProducts();
			this.model.pageSize = 10;
			return this.model;
		},

		renderTables : function() {
			var template = _.template(ChannelProductSearchTableTemplate);
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
					{
						visible: true,
	                	targets: 0
	                },
					{
		                render: function ( data, type, row ) {
		        			var rendered = data + ' ' + row.sizeUnit;
		                    return rendered;
		                },
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
	                	targets: 6
	                },
					{
		                render: function ( data, type, row ) {
		                	var jsonStr = JSON.stringify(row);
		        			var rendered = "<a href='javascript:;' class='btn btn-info btn-sm btn-op-col' name='look-a' data-id='" + row.id + "'>查看</a>";
		                    return rendered;
		                },
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
				name: this.parentView.$('#channel-product-p #filter-p #filter_name').val(),
				channelId: this.parentView.$('#channel-product-p #filter-p #filter_channel').val(),
				mispId: this.parentView.$('#channel-product-p #filter-p #filter_misp').val(),
				provinceId: this.parentView.$('#channel-product-p #filter-p #filter_province').val(),
				region: this.parentView.$('#channel-product-p #filter-p #filter_region').val(),
				enabled: this.parentView.$('#channel-product-p #filter-p #filter_enabled').val(),
				};
		},
		
		look: function(e) {
			var t = this.getModelByOpElement(e);
			var that = this;
			var spinner = new MySpinner({scale:0.25, blockUI: true});
			spinner.start(this.getOverlayEl());
			t.fetch({
				success: function (model, response) {
					spinner.stop();
					var dialog = new ChannelProductContentDialogView({
						model:model,
						misps:that.misps, 
						provinces:that.provinces,
					});
					dialog.parentView = that;
					dialog.render();
		            $('#modals-container').append(dialog.$el);
				},
				error: function(model, response) {
					spinner.stop();
					that.defaultErrorHandler(model, response);
				},
			});
		},
		
	});

	return ChannelProductSearchGridView;
});
