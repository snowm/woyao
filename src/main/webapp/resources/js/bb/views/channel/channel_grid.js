define([ 'text!bb/templates/channel/channel_table.html', 'text!bb/templates/channel/channel_grid_op.html',   
	'bb/collections/channel/channels', 'bb/views/base/base_grid', 'bb/views/channel/channel_content_dialog' ], 
		function(ChannelTableTemplate, ChannelGridOpTemplate, 
			Channels, BaseGridView, ChannelContentDialogView ) {

	var ChannelsGridView = BaseGridView.extend({
		name : 'channel',
		
		events: {
			"click table div#channel-ops > a[name='edit-a']" : "showEditDialog",
			"click table div#channel-ops > a[name='enable-a']" : "enable",
			"click table div#channel-ops > a[name='disable-a']" : "disable",
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
			this.model = new Channels();
			this.model.pageSize = 20;
			return this.model;
		},

		renderTables : function() {
			var template = _.template(ChannelTableTemplate);
			var html = template();
			this.$el.append(html);
		},

		constructTable : function(){
        	var op_template = _.template(ChannelGridOpTemplate);
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
		            { data: "name" },
		            { data: "type" },
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
		        			var rendered = op_template(row);
		                    return rendered;
		                },
	                	targets: 4
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
				name: this.parentView.$('div#channels-p #filter-p #name').val(),
				};
		},

		enable: function(e) {
			this._doSyncOp(e, 'enable', function(model, json){
				model.set('enabled', json.enabled);
			});
		},

		disable : function(e) {
			var that = this;
			this.bootboxWrapper.confirm({
				message:"确定要停用此渠道？这会让所有该渠道下的产品停用！",
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
		
		showEditDialog: function(e) {
			var t = this.getModelByOpElement(e);
			var that = this;
			var spinner = new MySpinner({scale:0.25, blockUI: true});
			spinner.start(this.getOverlayEl());
			t.fetch({
				success: (function (model, response) {
					spinner.stop();
					var dialog = new ChannelContentDialogView({model:model});
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

	return ChannelsGridView;
});
