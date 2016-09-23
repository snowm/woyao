define([ 'bb/models/product/category', 'bb/collections/pagination_bean' ], 
    function(Category, PaginationBean) {
  var Categories = PaginationBean.extend({
    url : "category/search",
    model : Category,
  });

  return Categories;
});