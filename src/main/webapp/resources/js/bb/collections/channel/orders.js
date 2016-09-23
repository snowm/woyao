define(['bb/models/channel/order', 'bb/collections/pagination_bean',
	'backbone'], function (ChannelOrder, PaginationBean) {
	var ChannelOrders = PaginationBean.extend({
		url   : "channelOrder/search",
		model : ChannelOrder,
	});
	
	return ChannelOrders;
});