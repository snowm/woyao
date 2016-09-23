define([ 'backbone'], function() {
    var Channel = Backbone.Model.extend({
        idAttribute: "id",
        sync: mySyncFunction,
        url   : "channelOrder",
        defaults : function() {
            return {
                id : null,
                thirdChannelOrderId: null,
                aliOrderId : null,
                tbOrderNo : null,
                spuId : null,
                accountVal : null,
                localOrderId : null,
                buyNum : null,
                channelProductId : null,
                channelProductName : null,
                channelName : null,
                callbackValidationCode : null,
                orderStatus : null,
                sentDate : null,
                completeDate : null,
                failReason : null,
                creationDate : null,
                lastModifiedDate : null,
                enabled: true,
                deleted: false,
            };
        },
        
    });

    function mySyncFunction(method, model, options) {
    	if (!_s.isUn(options.url)) {
            return Backbone.sync(method, model, options);
    	}
        if (method == 'read') {
        	if (!_s.isUn(options) && options.localOrderId){
        		options.url = this.url + '/getByLocalOrderId/' + options.localOrderId;
        	} else if (!_s.isUn(options) && options.aliOrderId){
        		options.url = this.url + '/getByAliOrderId/' + options.aliOrderId;
        	} else {
        		options.url = this.url + '/' + this.id;
        	}
        } else if (method == 'delete') {
            options.url = this.url;
        } else if (method == 'update' || method == 'create') {
            options.url = this.url;
        } else if (method == 'disable') {
			options.url = this.url + '/disable/' + this.id;
		} else if (method == 'enable') {
			options.url = this.url + '/enable/' + this.id;
		} else if (method == 'markSuccess') {
			options.url = this.url + '/markSuccess/' + this.id;
		} else if (method == 'markFail') {
			options.url = this.url + '/markFail/' + this.id;
		}
        return Backbone.sync(method, model, options);
    };

    return Channel;
});
