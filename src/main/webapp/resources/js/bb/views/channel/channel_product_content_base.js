define([ 'text!bb/templates/channel/channel_product_content_base.html',
         'bb/views/base/base_component' ],
    function (ChannelProductContentBaseTemplate, 
    		BaseComponent) {
        var ChannelProductContentBaseView = BaseComponent.extend({
            template : _.template(ChannelProductContentBaseTemplate),
            formValidator : null,
    		misps : null,
    		provinces : null, 
            
            initialize : function() {
            	var options = arguments[0];
            	this.misps = options.misps;
            	this.provinces = options.provinces;
            },
            
            render : function() {
                this.$el.html(this.template({
                	model: this.model.toJSON(),
                	misps: this.misps.toJSON(),
                	provinces: this.provinces.toJSON(),
                	})
                );
                return this;
            },

            getModel : function() {
                this.changeAttribute("name", this.$('#channelProduct_name').val());
                this.changeAttribute("channelId", this.$('#channelProduct_channel').val());
                this.changeAttribute("mispId", this.$('#channelProduct_misp').val());
                this.changeAttribute("provinceId", this.$('#channelProduct_province').val());
                this.changeAttribute("region", this.$('#channelProduct_region').val());
                this.changeAttribute("size", this.$('#channelProduct_size').val());
                this.changeAttribute("sizeUnit", this.$('#channelProduct_sizeUnit').val());
                return this.model;
            },

            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },
        });

        return ChannelProductContentBaseView;
    }
);
