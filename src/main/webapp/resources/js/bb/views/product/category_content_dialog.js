define([ 'text!bb/templates/product/category_content_dialog_body.html',
    'bb/models/product/category', 'bb/views/product/category_content_base',
    'bb/views/base/base_content_dialog' ], function(CategoryContentDialogBodyTemplate, Category,
    CategoryContentBaseView, BaseContentDialogView) {
  var CategoryContentDialogView = BaseContentDialogView.extend({
    contentTemplate : _.template(CategoryContentDialogBodyTemplate),
    baseInfoView : null,
    currentTab : null,
    css : {
      maxWidth : '500px'
    },

    initExt : function(options) {},

    renderEl : function() {
      this.$('div.modal-body:first').html(this.contentTemplate());
      var that = this;
      this.$('div.modal-body >div > ul > li > a').click(function(e) {
        e.preventDefault();
        var selected = $(this);
        selected.tab('show');
        that.selectTab(e.target.id);
      });
      this.showBaseInfo();
    },

    selectTab : function(targetId) {
      switch (targetId) {
      case "base-info-a":
        this.showBaseInfo();
        break;
      }
    },

    showBaseInfo : function() {
      if (_s.isUn(this.baseInfoView)) {
        this.baseInfoView = new CategoryContentBaseView({
          model : this.model,
        });
        this.baseInfoView.parentView = this;
        this.baseInfoView.render();
        if (this.readonly) {
          this.baseInfoView.readonlyRender();
        }
        this.$('div#base-info').append(this.baseInfoView.$el);
        this.currentTab = this.baseInfoView;
      }
      this.currentTab = this.baseInfoView;
    },

    readonlyRender : function() {
      if (!_s.isUn(this.baseInfoView)) {
        this.baseInfoView.readonlyRender();
      }
    },

    save : function() {
      if (!_s.isUn(this.currentTab)) {
        this.currentTab.save();
      }
    },

  });

  return CategoryContentDialogView;
});
