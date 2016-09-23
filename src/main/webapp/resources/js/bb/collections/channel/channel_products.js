define(['bb/models/channel/channel_product', 'bb/collections/pagination_bean',
	'backbone'], function (ChannelProduct, PaginationBean) {
	var ChannelProducts = PaginationBean.extend({
		url   : "channelProduct/search",
		model : ChannelProduct,
	});
	
	return ChannelProducts;
});