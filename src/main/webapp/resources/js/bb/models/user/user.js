define([ 'backbone'], function(Backbone) {
    var User = Backbone.Model.extend({
        idAttribute: "id",
        sync: mySyncFunction,
        url   : "user",
        defaults : function() {
            return {
                id : null,
                username: '',
                password: '',
                enabled: true,
                type: 'CUSTOMER',
                gender: 'MALE',
            };
        }
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

    return User;
});
