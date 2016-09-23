define(['bb/models/user/user', 'bb/collections/pagination_bean',
	'backbone'], function (User, PaginationBean) {
	var Users = PaginationBean.extend({
		url   : "user/search",
		model : User,
	});
	
	return Users;
});