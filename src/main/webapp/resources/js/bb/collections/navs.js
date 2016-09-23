define(['bb/models/nav'], function (Nav) {
	var Navs = Backbone.Collection.extend({
		model : Nav,
	});
	return Navs;
});