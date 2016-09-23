define(['bb/models/ali/product', 'bb/collections/pagination_bean',
	'backbone'], function (AliProduct, PaginationBean) {
	var AliProducts = PaginationBean.extend({
		url   : "aliProduct/search",
		model : AliProduct,
	});
	
	return AliProducts;
});