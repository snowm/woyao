define([ 'text!bb/templates/user/user_content_dialog.html',
         'bb/views/base/base_component' ],
    function (UserContentDialogTemplate, 
    		BaseComponent) {

        var UserContentDialogView = BaseComponent.extend({
            modal_dg : null,
            template : _.template(UserContentDialogTemplate),
            user : null,
            confirmClose : false,
            formValidator : null,

            events: {
                "click #save-btn" : "save",
            },
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
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
                    that.close();
                });

                _s.checkBootstrapEmbeddedModal(this.modal_dg);
                this.formValidator = this.$('#vendor_content_form').validate({
                    rules: {
                        html_content: {
                            required: true
                        }
                    },
                    messages: {
                        html_content: "Content cannot be empty!"
                    }
                });
            },

            save : function() {
                this.changeValues();
                this.saveData();
            },

            changeValues : function() {
                this.changeAttribute("username", this.$('#user_name').val());
                this.changeAttribute("password", this.$('#user_pwd').val());
                this.changeAttribute("enabled", this.$('#user_enabled').val());
            },

            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },
            
            saveData:function(){
                var that = this;
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.parentView.$('div.modal-dialog:first'));
    			var isNew = _s.isUn(this.model.get('id'));
                this.model.save(this.model.attributes, {
                    success: function(model, response) {
                        console.log('successfully saving this record.');
                        spinner.stop();
                        that.bootboxWrapper.info("保存成功！", {
                        	callback: function(){
                        		var col = that.parentView.model;
                        		if (isNew) {
                        			col = that.parentView.gridView.model;
                        		}
                    			var length = col.length;
                    			col.add(model, {
	                        		at: 0,
	                        		merge:true,
	                        	});
                    			col.trigger("refresh");
                        	}
                        });
                    },
                    
                    error: function(model, response) {
                    	spinner.stop();
    					that.defaultErrorHandler(model, response);
                    },
                });
            },

        });

        return UserContentDialogView;
    }
);
