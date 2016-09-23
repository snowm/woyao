define([ 'text!bb/templates/user/user_table.html', 'text!bb/templates/user/user_grid_op.html',   
	'bb/collections/users', 'bb/views/base/base_grid', 'bb/views/user/user_content_dialog' ], 
		function(UserTableTemplate, UserGridOpTemplate, 
			Users, BaseGridView, UserContentDialogView) {

	var UsersGridView = BaseGridView.extend({
		name : 'user_grid',
		
		events: {
			"click table #order-ops a[name='edit-a']" : "showEditDialog",
			"click table #order-ops a[name='enable-a']" : "enable",
			"click table #order-ops a[name='disable-a']" : "disable",
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
			this.model = new Users();
			this.model.pageSize = 20;
			return this.model;
		},

		renderTables : function() {
			var template = _.template(UserTableTemplate);
			var html = template();
			this.$el.append(html);
		},

		constructTable : function(){
        	var op_template = _.template(UserGridOpTemplate);
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
		            { data: "username" },
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
	                	targets: 3
	                },
					{
		                orderable: false,
	                	targets: '_all'
	                }
	            ],
			});
			table.select();
		    return table;
		},

		createSearchData: function() {
			return {
				name: this.parentView.$('div#user-p #filter-p #name').val(),
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
					var dialog = new UserContentDialogView({model:model});
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

	return UsersGridView;
});
