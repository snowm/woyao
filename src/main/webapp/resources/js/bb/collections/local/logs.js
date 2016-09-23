define(['bb/models/local/log', 'bb/collections/pagination_bean',
	'backbone'], function (OrderLog, PaginationBean) {
	var OrderLogs = PaginationBean.extend({
		url   : "orderLog",
		model : OrderLog,
	});
	
	return OrderLogs;
});