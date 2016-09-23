define([ 'text!bb/templates/product/category_main.html', 'bb/models/product/category',
    'bb/views/product/category_grid', 'bb/views/product/category_content_dialog',
    'bb/views/base/base_container_content' ], function(
    CategoryMainTemplate, Category, CategoryGridView, CategoryContentDialogView,
    BaseContainerContentView) {
  var CategoryMainView = BaseContainerContentView.extend({
    gridView : null,
    events : {
      "click div#category-p #filter-p button#query-bt" : "query",
      "click div#category-p #filter-p button#create-bt" : "showCreateDialog",
    },
    init_ext : function() {
      this.gridView = new CategoryGridView({
        ext_opt : {
          overlayTarget : '#category-p > .panel-body',
          methodType : 'POST',
        }
      });
      this.gridView.name = 'categoriesGrid';
      this.gridView.parentView = this;
    },

    render : function() {
      this.$el.append(_.template(CategoryMainTemplate));
      this.$('.panel-body > .container-fluid').append(this.gridView.$el);
      this.gridView.render();
      return this;
    },

    query : function() {
      this.gridView.query();
    },

    showCreateDialog : function() {
      var newCategory = new Category();
      this.createDialogView = new CategoryContentDialogView({
        model : newCategory,
      });
      this.createDialogView.parentView = this;
      this.createDialogView.render();
      $('#modals-container').append(this.createDialogView.$el);
    },

    showed : function() {
      this.gridView.query();
    },
  });

  return CategoryMainView;
});
