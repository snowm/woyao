define([ 'text!bb/templates/product/product_main.html', 'bb/models/product/product',
    'bb/views/product/product_grid', 
    'bb/views/base/base_container_content' ], function(
    ProductMainTemplate, Product, ProductGridView, ProductContentDialogView,
    BaseContainerContentView) {
  var ProductMainView = BaseContainerContentView.extend({
    gridView : null,
    events : {
      "click div#product-p #filter-p button#query-bt" : "query",
      "click div#product-p #filter-p button#create-bt" : "showCreateDialog",
    },
    init_ext : function() {
      this.gridView = new ProductGridView({
        ext_opt : {
          overlayTarget : '#category-p > .panel-body',
          methodType : 'POST',
        }
      });
      this.gridView.name = 'categoriesGrid';
      this.gridView.parentView = this;
    },

    render : function() {
      this.$el.append(_.template(ProductMainTemplate));
      this.$('.panel-body > .container-fluid').append(this.gridView.$el);
      this.gridView.render();
      return this;
    },

    query : function() {
      this.gridView.query();
    },

    showCreateDialog : function() {
      var newProduct = new Product();
      this.createDialogView = new ProductContentDialogView({
        model : newProduct,
      });
      this.createDialogView.parentView = this;
      this.createDialogView.render();
      $('#modals-container').append(this.createDialogView.$el);
    },

    showed : function() {
      this.gridView.query();
    },
  });

  return ProductMainView;
});
