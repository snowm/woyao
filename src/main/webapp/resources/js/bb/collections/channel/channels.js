define(['bb/models/channel/channel', 'bb/collections/pagination_bean',
	'backbone'], function (Channel, PaginationBean) {
	var Channels = PaginationBean.extend({
		url   : "channel/search",
		model : Channel,
	});
	
	return Channels;
});