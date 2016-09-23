define([ 'bb/views/base/base_component'], 
	function(BaseComponent) {
		var BaseContainerContentView = BaseComponent.extend({
			className : 'container-fluid index',
			name : '',
			tabEl : null,
			is_first_show: true,
			force_refresh_data : false,

			initialize : function(options) {
				if (options) {
					this.name = options.name;
					if (!_s.isUn(options.force_refresh_data)){
						this.force_refresh_data = options.force_refresh_data;
					}
				}
				if (_s.isFunction(this.init_ext)) {
					this.init_ext(arguments);
				}
				console.log('Container:' + this.name + ' initialized');
			},
			
			initializeEvents : function() {
				this.on('show', this.onShow, this);
				this.on('shown', this.onShown, this);
				this.on('hide', this.onHide, this);
				this.on('hidden', this.onHidden, this);
			},

			show : function() {
				var that = this;
				this.$el.show("normal", function() {
					if (that.is_first_show) {
						that.is_first_show = false;
						that.showed();
					} else if (that.force_refresh_data) {
						that.showed();
					}
				});
			},
			showed : function(){},

			hide : function(hidden) {
				var that = this;
				this.$el.hide("normal", function(){
					that.hidden(arguments);
					hidden(arguments);
				});
			},
			hidden : function() {},
			refresh : function() {},
		
		});
		return BaseContainerContentView;
	}
);
