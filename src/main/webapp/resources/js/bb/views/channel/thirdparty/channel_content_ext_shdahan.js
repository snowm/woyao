define([ 'text!bb/templates/channel/thirdparty/channel_content_ext_shdahan.html',
         'bb/views/base/base_component' ],
    function (ChannelContentExtTemplate, 
    		BaseComponent) {
        var ChannelContentExtView = BaseComponent.extend({
            template : _.template(ChannelContentExtTemplate),
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
            		account: this.$('#channel_cfg_account').val(),
            		password: this.$('#channel_cfg_password').val(),
            	};
            },

        });

        return ChannelContentExtView;
    }
);
