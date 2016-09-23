define([ 'bb/models/channel/channel_config', 'backbone'], function(ChannelConfig) {
    var Channel = Backbone.Model.extend({
        idAttribute: "id",
        sync: mySyncFunction,
        url   : "channel",
        defaults : function() {
            return {
                id : null,
                name: '',
                type: '',
                description: '',
                channelServiceName: '',
                config: {
                    id : null,
                    serviceUrl: '',
                    connTimeout: 2000,
                    reqTimeout: 5000,
                    socketTimeout: 5000,
                    manually: false,
                },
                enabled: true,
                deleted: false,
                supportBuyNum: false,
            };
        },
        
    });

    function mySyncFunction(method, model, options) {
        if (method == 'read' || method == 'delete') {
            options.url = this.url + '/' + this.id;
        } else if (method == 'update' || method == 'create') {
            options.url = this.url;
        } else if (method == 'disable') {
			options.url = this.url + '/disable/' + this.id;
		} else if (method == 'enable') {
			options.url = this.url + '/enable/' + this.id;
		}
        return Backbone.sync(method, model, options);
    };

    return Channel;
});
