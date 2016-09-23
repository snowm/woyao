define([ 'backbone'], function() {
    var MISP = Backbone.Model.extend({
        idAttribute: "id",
        defaults : function() {
            return {
                id : null,
                name: '',
            };
        },
        
    });

    return MISP;
});
