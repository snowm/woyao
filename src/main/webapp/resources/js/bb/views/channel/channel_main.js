define([ 'text!bb/templates/channel/channel_main.html', 
         'bb/models/channel/channel', 
         'bb/views/channel/channel_grid', 'bb/views/channel/channel_content_dialog', 
         'bb/views/base/base_container_content'], 
	function(ChannelMainTemplate, 
			Channel, 
			GridView, ChannelContentDialogView, 
			BaseContainerContentView) {
	
		var ChannelMainView = BaseContainerContentView.extend({
			gridView : null,
			createDialogView : null, 
			events : {
				"click div#channels-p #filter-p button#query-bt" : "query",
				"click div#channels-p #filter-p button#create-bt" : "showCreateDialog",
			},

			init_ext : function() {
				this.gridView = new GridView({
					ext_opt:{
						overlayTarget:'#channels-p > .panel-body',
						methodType:'POST'
					}
				});
				this.gridView.name = 'channelsGrid';
				this.gridView.parentView = this;
			},

			render : function() {
				this.$el.append(_.template(ChannelMainTemplate));
				this.$('#channels-p > .panel-body > .container-fluid').append(this.gridView.$el);
				this.gridView.render();
				return this;
			},

			query : function() {
				this.gridView.query();
			},
			
			showCreateDialog : function() {
				var newChannel = new Channel();
				this.createDialogView = new ChannelContentDialogView({model:newChannel});
				this.createDialogView.parentView = this;
				this.createDialogView.render();
	            $('#modals-container').append(this.createDialogView.$el);
			},
			showed : function() {
				this.gridView.query();
			},
		});

		return ChannelMainView;
});
