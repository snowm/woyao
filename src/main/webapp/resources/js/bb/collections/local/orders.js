define(['bb/models/local/order', 'bb/collections/pagination_bean',
	'backbone'], function (LocalOrder, PaginationBean) {
	var LocalOrders = PaginationBean.extend({
		url   : "localOrder/search",
		model : LocalOrder,
	});
	
	return LocalOrders;
});