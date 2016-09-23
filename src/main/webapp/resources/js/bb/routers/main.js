define([ 'bb/views/index/main', 'bb/views/user/user_main', 'bb/views/channel/channel_main',
    'bb/views/channel/channel_product_main', 'bb/views/system/system_dialog',
    'bb/views/channel/order_main', 'bb/views/product/category_main', 'backbone', 'snowm',
    'globalConfig' ], function(IndexMainView, UserMainView, ChannelMainView,
    ChannelProductMainView, SystemConfigView, ChannelOrderMainView, CategoryMainView) {
  var MainRouter = Backbone.Router.extend({
    $el : null,
    contentViews : {},
    currentView : undefined,
    viewName_indexPage : 'indexPage',
    viewName_channelOrderPage : 'channelOrderPage',
    viewName_channelManagePage : 'channelManagePage',
    viewName_userManagePage : 'userManagePage',
    viewName_systemConfigPage : 'systemConfigPage',
    viewName_channelProductManagePage : 'channelProductManagePage',

    viewName_categoryManagePage : 'categoryManagePage',

    routes : {
      '' : 'defaultPage',
      'index' : 'indexPage',
      'channel-order-manage' : 'channelOrderPage',
      'user-manage' : 'userManagePage',
      'channel-manage' : 'channelManagePage',
      'channel-product-manage' : 'channelProductManagePage',
      'category-manage' : 'categoryManagePage',
    },

    initialize : function() {
      this.$el = $('body > #main_container');
      _s.bindEvent($('#main_navbar a#system-config-a'), 'click', this.systemConfigPage, this);
    },

    clearActiveNavs : function() {
      $('#main_navbar li').removeClass('active');
    },

    indexPage : function() {
      this.clearActiveNavs();
      $('#main_navbar a#index-a').parent('li').addClass('active');
      this.selectView({
        name : this.viewName_indexPage
      });
    },

    channelManagePage : function() {
      this.clearActiveNavs();
      $('#main_navbar a#channel-manage-a').parent('li').addClass('active');
      this.selectView({
        name : this.viewName_channelManagePage
      });
    },

    channelOrderPage : function() {
      this.clearActiveNavs();
      $('#main_navbar a#channel-order-manage-a').parent('li').addClass('active');
      $('#main_navbar a#channel-order-manage-a').parent('li').parent('ul').parent('li').addClass(
          'active');
      this.selectView({
        name : this.viewName_channelOrderPage
      });
    },

    userManagePage : function() {
      this.clearActiveNavs();
      $('#main_navbar a#user-manage-a').parent('li').addClass('active');
      $('#main_navbar a#user-manage-a').parent('li').parent('ul').parent('li').addClass('active');
      this.selectView({
        name : this.viewName_userManagePage
      });
    },

    systemConfigPage : function() {
      var view = new SystemConfigView({});
      $('div#modals-container').append(view.$el);
      view.render();
    },

    channelProductManagePage : function() {
      this.clearActiveNavs();
      $('#main_navbar a#channel-product-manage-a').parent('li').addClass('active');
      $('#main_navbar a#channel-product-manage-a').parent('li').parent('ul').parent('li').addClass(
          'active');
      this.selectView({
        name : this.viewName_channelProductManagePage
      });
    },
    categoryManagePage : function() {
      this.clearActiveNavs();
      $('#main_navbar a#category-manage-a').parent('li').addClass('active');
      $('#main_navbar a#category-manage-a').parent('li').parent('ul').parent('li').addClass(
          'active');
      this.selectView({
        name : this.viewName_categoryManagePage
      });
    },

    defaultPage : function(actions) {
      console.log('default action for router');
      this.indexPage();
    },

    selectView : function(options) {
      var name = options.name;
      if (_s.isUn(forceRemoveExisted)) {
        options.forceRemoveExisted = false;
      }
      var forceRemoveExisted = options.forceRemoveExisted;
      var tabEl = options.tabEl;
      // 处理当前view
      if (!_s.isUn(this.currentView)) {
        if (this.currentView.view.name == name) {
          // 如果当前view就是目标view，看forceRemoveExisted选项决定是refresh，还是remove之后完全重新渲染
          if (!_s.isUn(forceRemoveExisted) && forceRemoveExisted == false) {
            // 如果forceRemoveExisted为false，那么只需要refresh currentView
            this.currentView.view.refresh();
            return;
          } else {
            this.removeView(name);
          }
        } else {
          // 如果当前view不是目标view，看currentView上的forceRemove选项决定是hide还是remove当前view
          if (this.currentView.forceRemove) {
            // 如果强制要求remove当前view,remove当前view
            this.removeView();
          } else {
            // 如果不强制要求remove当前view,hide当前view
            this.hideView(this.currentView.name, _.bind(this.processTargetView, this, options));
            return;
          }
        }
      }
      this.processTargetView(options);
    },

    processTargetView : function(options) {
      var name = options.name;
      var forceRemoveExisted = options.forceRemoveExisted;
      var tabEl = options.tabEl;
      // 处理目标view
      var existedView = this.getView(name);
      if (!_s.isUn(existedView)) {
        // 如果目标view已经存在，看forceRemoveExisted选项决定是refresh，还是remove之后完全重新渲染
        if (!_s.isUn(forceRemoveExisted) && forceRemoveExisted == false) {
          this.currentView = existedView;
          this.showView(this.currentView);
          this.currentView.view.refresh();
          return;
        } else {
          this.removeView(name);
        }
      }
      // 重新渲染目标view
      var view = null;
      // 当切出此view时，是否强制remove
      var forceRemove = false;
      var routePath = '';
      var initOptions = {
        name : name
      };
      if (name == this.viewName_indexPage) {
        view = new IndexMainView(initOptions);
      } else if (name == this.viewName_channelOrderPage) {
        view = new ChannelOrderMainView(initOptions);
      } else if (name == this.viewName_userManagePage) {
        view = new UserMainView(initOptions);
      } else if (name == this.viewName_channelManagePage) {
        view = new ChannelMainView(initOptions);
      } else if (name == this.viewName_channelProductManagePage) {
        view = new ChannelProductMainView(initOptions);
      } else if (name == this.viewName_categoryManagePage) {
        view = new CategoryMainView(initOptions);
      } else {
        console.log('Unsupported page name:' + name);
      }

      var viewEle = {
        forceRemove : forceRemove,
        routePath : routePath,
        view : view
      };
      this.contentViews[name] = viewEle;
      this.currentView = viewEle;
      this.$el.append(this.currentView.view.$el);
      this.currentView.view.render();
      this.showView(this.currentView);
    },

    showView : function(viewEle) {
      viewEle.view.show();
      // this.navigate(viewEle.routePath, {trigger:true});
    },
    getView : function(name) {
      if (this.hasView(name)) {
        return this.contentViews[name];
      }
      return undefined;
    },

    hasView : function(name) {
      return _.has(this.contentViews, name) && !_s.isUn(this.contentViews[name]);
    },

    removeView : function(name) {
      if (_s.isUn(name)) {
        if (this.currentView) {
          this.currentView.view.close();
          this.contentViews[this.currentView.name] = undefined;
        }
        return;
      }
      if (this.hasView(name)) {
        this.contentViews[name].view.close();
        this.contentViews[name] = undefined;
      }
    },

    hideView : function(name, hidden) {
      if (_s.isUn(name)) {
        if (this.currentView) {
          this.currentView.view.hide(hidden);
        }
        return;
      }
      if (this.hasView(name)) {
        this.contentViews[name].view.hide(hidden);
      }
    },

  });

  Backbone.View.prototype.close = function() {
    this.off();
    this.remove();
  };

  return MainRouter;
});
