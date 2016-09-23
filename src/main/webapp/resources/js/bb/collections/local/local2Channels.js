define(['bb/models/local/local2Channel', 'bb/collections/pagination_bean',
	'backbone'], function (LocalToChannel, PaginationBean) {
	var LocalToChannels = Backbone.Collection.extend({
		model : LocalToChannel,
	});
	
	return LocalToChannels;
});