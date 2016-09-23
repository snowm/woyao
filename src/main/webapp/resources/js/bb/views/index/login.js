define(['jquery', 'underscore', 'backbone', 'bootbox', 'text!bb/templates/offer_type/css.html'], 
		function ($, _, Backbone, localStore, bootbox, LoginTemplate) {
	
	var SearchView = Backbone.View.extend({
		el: $("#offers-content"),
		template: _.template(LoginTemplate),
		events: {
		    "click #login-a" : "initialSearch",
		 },

		 initialize: function() {
			 console.log('Search form initialized');
			 $("#add_btn").removeClass("disabled");
			 $("#search_btn").removeClass("disabled");
			 
//			 this.listenTo("#copy_btn", 'click', this.render);
			 this.render();
		 },

		 render: function() {
			 return this;
		 }
	});
	
	return SearchView;
	
});
