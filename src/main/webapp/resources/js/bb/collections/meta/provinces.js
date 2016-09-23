define(['bb/models/meta/province', 'bb/collections/pagination_bean',
	'backbone'], function (Province, PaginationBean) {
	var Provinces = PaginationBean.extend({
		url   : "meta/provinces",
		model : Province,
		parse: function(response) {
		    return response;
		}
	});
	
	return Provinces;
});