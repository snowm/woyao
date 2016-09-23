define([ 'backbone'], function(Backbone) {
    var TimeLimit = Backbone.Model.extend({
        idAttribute: "id",
        sync: mySyncFunction,
        url   : "system/timeLimit",
        defaults : function() {
            return {
                id : null,
                saveLeft: null,
                map2LocalLeft: null,
                map2ChannelLeft: null,
                chooseProductLeft: null,
                sendToChannelLeft: null,
                completeLeft: null,
            };
        }
    });

    function mySyncFunction(method, model, options) {
        options.url = this.url;
        return Backbone.sync(method, model, options);
    };

    return TimeLimit;
});
