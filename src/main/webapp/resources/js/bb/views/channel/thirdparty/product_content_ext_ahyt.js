define([ 'text!bb/templates/channel/thirdparty/product_content_ext_ahyt.html',
         'bb/views/base/base_component' ],
    function (ProductContentExtTemplate, 
    		BaseComponent) {
        var ProductContentExtView = BaseComponent.extend({
            template : _.template(ProductContentExtTemplate),
            formValidator : null,
            type: _c.channel_type_shdhst,
            
            initialize : function() {
            },
            
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
                return this;
            },

            getExtInfo : function() {
            	return {
            		proCode: this.$('#product_proCode').val(),
            	};
            },

        });

        return ProductContentExtView;
    }
);
