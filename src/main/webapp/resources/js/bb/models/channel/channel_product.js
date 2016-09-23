define([ 'backbone'], function() {
    var ChannelProduct = Backbone.Model.extend({
        idAttribute: "id",
        sync: mySyncFunction,
        url   : "channelProduct",
        defaults : function() {
            return {
                id : null,
                type : null,
                name: '',
                mispId: null,
                channelId: null,
                size: null,
                sizeUnit: 'MB',
                region: 'BELONG',
                provinceId: null, 
                enabled: true,
                deleted: false,
                supportBuyNum: false,
                //ahyt
                proCode: null, 
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

    return ChannelProduct;
});
