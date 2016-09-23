define([ 'backbone'], function(Backbone) {
    var ChannelConfig = Backbone.Model.extend({
        idAttribute: "id",
        defaults : function() {
            return {
                id : null,
                serviceUrl: '',
                connTimeout: 2000,
                reqTimeout: 5000,
                socketTimeout: 2000,
            };
        }
    });

    return ChannelConfig;
});
