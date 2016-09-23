define(['bb/models/ali/order', 'bb/collections/pagination_bean',
	'backbone'], function (AliOrder, PaginationBean) {
	var AliOrders = PaginationBean.extend({
		url   : "aliOrder/search",
		model : AliOrder,
	});
	
	return AliOrders;
});