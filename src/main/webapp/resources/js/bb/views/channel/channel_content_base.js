define([ 'text!bb/templates/channel/channel_content_base.html',
         'bb/views/base/base_component' ],
    function (ChannelContentBaseTemplate, 
    		BaseComponent) {
        var ChannelContentBaseView = BaseComponent.extend({
            template : _.template(ChannelContentBaseTemplate),
            formValidator : null,
            
            initialize : function() {
            },
            
            render : function() {
                this.$el.html(this.template(this.model.toJSON()));
                return this;
            },

            getModel : function() {
                this.changeAttribute("name", this.$('#channel_name').val());
                this.changeAttribute("type", this.$('#channel_type').val());
                this.changeAttribute("description", this.$('#channel_desc').val());
                this.changeAttribute("channelServiceName", this.$('#channel_svname').val());
                var config = this.model.get('config');
                config.serviceUrl = this.$('#channel_cfg_url').val();
                config.connTimeout = this.$('#channel_cfg_connto').val();
                config.reqTimeout = this.$('#channel_cfg_reqto').val();
                config.socketTimeout = this.$("#channel_cfg_sockto").val();
                config.manually = this.$("#channel_cfg_manually").is(':checked');
                return this.model;
            },

            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },
        });

        return ChannelContentBaseView;
    }
);
