define([ 'text!bb/templates/user/user_main.html', 
         'bb/models/user/user', 
         'bb/views/user/user_grid', 'bb/views/user/user_content_dialog', 'bb/views/base/base_container_content',
         'bb/views/base/bootbox_wrapper'], 
	function(UserMainTemplate, 
			User, 
			UsersGridView, UserContentDialogView, BaseContainerContentView) {
	
		var UserMainView = BaseContainerContentView.extend({
			gridView : null,
			createDialogView : null, 
			events : {
				"click div#user-p li > a#ref-users-a" : "query",
				"click div#user-p #filter-p button#query-bt" : "query",
				"click div#user-p #filter-p button#create-bt" : "showCreateDialog",
			},

			init_ext : function() {
				this.gridView = new UsersGridView({
					ext_opt:{
						overlayTarget:'#user-p > .panel-body',
						methodType:'POST'
					}
				});
				this.gridView.name = 'usersGrid';
				this.gridView.parentView = this;
			},

			render : function() {
				this.$el.append(_.template(UserMainTemplate));
				this.$('#user-p > .panel-body > .container-fluid').append(this.gridView.$el);
				this.gridView.render();
				return this;
			},

			query : function() {
				this.gridView.query();
			},
			
			showed : function() {
				this.gridView.query();
			},
			
			showCreateDialog : function() {
				var newUser = new User();
				this.createDialogView = new UserContentDialogView({model:newUser});
				this.createDialogView.parentView = this;
				this.createDialogView.render();
	            $('#modals-container').append(this.createDialogView.$el);
			},
		});

		return UserMainView;
});
