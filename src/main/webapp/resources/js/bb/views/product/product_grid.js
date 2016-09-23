define([ 'text!bb/templates/product/category_table.html',
    'text!bb/templates/product/category_grid_op.html', 'bb/collections/product/categories',
    'bb/views/product/category_content_dialog', 'bb/views/base/base_grid' ], function(
    CategoryTableTemplate, CategoryGridOpTemplate, Categories, CategoryContentDialogView,
    BaseGridView) {

  var CategoryGridView = BaseGridView.extend({
    name : 'categries',
    opTemplate : _s.template(CategoryGridOpTemplate),

    events : {
      "click table #category-ops a[name='edit-a']" : "showEditDialog",
      "click table #category-ops a[name='enable-a']" : "enable",
      "click table #category-ops a[name='disable-a']" : "disable",
    },

    init_ext : function() {
      _.extend(this.opt.paginator_opt, {
        size : 'small',
        numberOfPages : 4,
        page_select : true,
        info_enable : true,
      });
    },

    newPaginationBean : function() {
      this.model = new Categories();
      this.model.pageSize = 20;
      return this.model;
    },

    renderTables : function() {
      var template = _.template(CategoryTableTemplate);
      var html = template();
      this.$el.append(html);
    },

    constructTable : function() {
      var that = this;
      var table = this.$('table').DataTable(_.extend(_c.default_grid_option(), {
        columns : [ {
          data : "id"
        }, {
          data : "name"
        }, {
          data : "code"
        }, {
          data : "id"
        } ],
        columnDefs : [ {
          render : function(data, type, row) {
            var rendered = that.opTemplate(row);
            return rendered;
          },
          targets : 3,
        }, {
          orderable : false,
          targets : '_all'
        } ],
      }));
      return table;
    },

    createSearchData : function() {
      return {
        name : this.parentView.$('#filter-p #filter_name').val(),
        code : this.parentView.$('#filter-p #filter_code').val(),
        enabled : this.parentView.$('#filter-p #filter_enabled').val(),
      };
    },

    showEditDialog : function(e) {
      var t = this.getModelByOpElement(e);
      var that = this;
      var spinner = new SpinnerExt.Spinner({
        scale : 0.25,
        blockUI : true
      });
      spinner.start(this.getOverlayEl());
      t.fetch({
        success : (function(model, response) {
          spinner.stop();
          var dialog = new CategoryContentDialogView({
            model : model,
            name : model.get('name'),
          });
          dialog.parentView = that;
          dialog.render();
          $('#modals-container').append(dialog.$el);
        }),
        error : function(model, response) {
          spinner.stop();
          that.defaultErrorHandler(model, response);
        },
      });
    },

    enable : function(e) {
      this._doSyncOp.apply(this, [ e, 'disable' ]);
    },

    disable : function(e) {
      var that = this;
      BootboxExt.confirm({
        message : "确定要禁用此分类？",
        confirmCallback : function() {
          that._doSyncOp.apply(that, [ e, 'disable' ]);
        },
      });
    },

  });

  return CategoryGridView;
});
