define([ 'text!bb/templates/channel/order_content_base.html',
         'bb/views/base/base_component' ],
    function (ChannelOrderContentBaseTemplate, 
    		BaseComponent) {
        var ChannelOrderContentBaseView = BaseComponent.extend({
            template : _.template(ChannelOrderContentBaseTemplate),
            
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
                return this;
            },

            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },
            
            readonlyRender : function() {
            	_s.makeReadonly(this);
        		this.$('#save-btn').css('display', 'none');
            },
            
        });

        return ChannelOrderContentBaseView;
    }
);
