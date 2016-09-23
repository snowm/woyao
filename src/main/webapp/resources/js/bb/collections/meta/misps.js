define(['bb/models/meta/misp', 'bb/collections/pagination_bean',
	'backbone'], function (MISP, PaginationBean) {
	var MISPS = PaginationBean.extend({
		url   : "meta/misps",
		model : MISP,
		parse: function(response) {
		    return response;
		}
	});

	return MISPS;
});