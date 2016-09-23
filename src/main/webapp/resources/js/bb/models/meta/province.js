define([ 'backbone'], function() {
    var Province = Backbone.Model.extend({
        idAttribute: "id",
        defaults : function() {
            return {
                id : null,
                name: '',
            };
        },
        
    });

    return Province;
});
