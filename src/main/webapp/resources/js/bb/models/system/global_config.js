define([ 'backbone'], function(Backbone) {
    var GlobalConfig = Backbone.Model.extend({
        idAttribute: "id",
        sync: mySyncFunction,
        url   : "system/globalConfig",
        defaults : function() {
            return {
            	id:-1,
            	aliConnectionTimeout: null,
            	aliSocketTimeout: null,
            	aliConnectionRequestTimeout: null,
            	channelTimeout: null,
            	serviceAvailable: true,
            };
        }
    });

    function mySyncFunction(method, model, options) {
    	if (!_s.isUn(options.url)) {
            return Backbone.sync(method, model, options);
    	}
        if (method == 'read' || method == 'delete') {
            options.url = this.url;
        } else if (method == 'update' || method == 'create') {
            options.url = this.url;
        } else if (method == 'disable') {
			options.url = this.url + '/disable/' + this.id;
		} else if (method == 'enable') {
			options.url = this.url + '/enable/' + this.id;
		} else if (method == 'enableService') {
			options.url = this.url + '/enableService';
		} else if (method == 'disableService') {
			options.url = this.url + '/disableService';
		}
        return Backbone.sync(method, model, options);
    };

    return GlobalConfig;
});
