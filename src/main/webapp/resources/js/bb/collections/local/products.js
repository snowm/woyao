define(['bb/models/local/product', 'bb/collections/pagination_bean',
	'backbone'], function (LocalProduct, PaginationBean) {
	var LocalProducts = PaginationBean.extend({
		url   : "localProduct/search",
		model : LocalProduct,
	});
	
	return LocalProducts;
});