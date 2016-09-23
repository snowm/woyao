define([ 'text!bb/templates/system/timelimit_config.html',
         'bb/views/base/base_component' ],
    function (TimeLimitConfigTemplate, 
    		BaseComponent) {
        var TimeLimitConfigView = BaseComponent.extend({
            template : _.template(TimeLimitConfigTemplate),
            formValidator : null,
    		misps : null,
    		provinces : null, 
            
            initialize : function() {
            	var options = arguments[0];
            	this.misps = options.misps;
            	this.provinces = options.provinces;
            },
            
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
                return this;
            },

            changeValues : function() {
                this.changeAttribute("saveLeft", this.$('#saveLeft').val());
                this.changeAttribute("map2LocalLeft", this.$('#map2LocalLeft').val());
                this.changeAttribute("map2ChannelLeft", this.$('#map2ChannelLeft').val());
                this.changeAttribute("chooseProductLeft", this.$('#chooseProductLeft').val());
                this.changeAttribute("sendToChannelLeft", this.$('#sendToChannelLeft').val());
                this.changeAttribute("completeLeft", this.$('#completeLeft').val());
            },
            
            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },
            
            save : function() {
            	this.changeValues();
                var that = this;
    			var spinner = new MySpinner({scale:0.25, blockUI: true});
    			spinner.start(this.parentView.$('div.modal-dialog:first'));
                this.model.save(this.model.attributes, {
                    success: function(model, response) {
                        console.log('successfully saving this record.');
                        spinner.stop();
    					that.parentView.showTimelimitConfig();
                        that.bootboxWrapper.alert("保存成功！");
                    },
                    
                    error: function(model, response) {
                    	spinner.stop();
    					that.defaultErrorHandler(model, response);
                    },
                });
            },
        });

        return TimeLimitConfigView;
    }
);
