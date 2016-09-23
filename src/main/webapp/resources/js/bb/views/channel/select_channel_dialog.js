define([ 'text!bb/templates/channel/select_channel_dialog.html',
         'bb/collections/channel/channels', 
         'bb/views/base/base_component' ],
    function (SelectChannelDialogTemplate, 
    		Channels,
    		BaseComponent) {

        var SelectChannelDialogView = BaseComponent.extend({
            modal_dg : null,
            template : _.template(SelectChannelDialogTemplate),
            channels : new Channels(),
            selected : undefined,

            events: {
                "click #choose-btn" : "choose",
            },
            
            render : function() {
                this.$el.html(this.template());
                this.renderModalDialog();
                return this;
            },

            renderModalDialog : function() {
                this.modal_dg = this.$('div.modal.fade');
                this.modal_dg.modal({
                  backdrop : 'static',
                });
                var that = this;
                this.modal_dg.on('hidden.bs.modal', function (event) {
                    that.clearDialog();
                    if (!_s.isUn(that.selected)) {
                    	that.chooseCallback(that.selected);
                    }
                });
                this.refreshDropdown();
                _s.checkBootstrapEmbeddedModal(this.modal_dg);
            },

            clearDialog: function() {
            	this.unbind();
            	this.remove();
            },

            choose : function() {
            	this.modal_dg.modal('hide');
            	var selectedId = this.$("#channelId").val();
            	this.selected = this.channels.get(selectedId);
            },
            
            chooseCallback : function(selected) {
            },

            refreshDropdown : function(){
                var that = this;
				var searchData = {
						pageNumber : -1,
						pageSize : -1,
					};
				var spinner = new MySpinner({scale:0.25, blockUI: true});
				spinner.start(this.$el);
				this.channels.fetch({
					type: 'POST',
					data: searchData,
					success: (function (model, response) {
						spinner.stop();
						model.each(function (m){
							that.$("#channelId").append("<option value='" + m.get('id') + "'>" + m.get('name') + "</option>");
						});
					}),
					error: (function (model, response) {
						that.defaultErrorHandler(model, response);
					})
				});
            },

        });

        return SelectChannelDialogView;
    }
);
