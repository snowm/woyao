define([ 'bb/views/base/base_paginator', 'bb/views/base/base_component', 'backbone', 'snowm',
    'bootstrap-paginator', 'dataTables-select', 'dataTables-rowReorder',
    'bootstrap-dataTables-responsive', 'dataTables-fixedHeader' ], function(BasePaginator,
    BaseComponent) {

  var BaseGridView = BaseComponent.extend({
    tagName : 'div',
    default_opt : {
      overlayTarget : undefined,
      numberOfPages : 10,
      methodType : 'POST',
      paginator_opt : {},
    },
    opt : undefined,
    table : undefined,
    paginator : undefined,

    initialize : function() {
      if (arguments.length > 0) {
        var options = arguments[0];
        if (!_s.isUn(options.ext_opt)) {
          this.opt = _.defaults(options.ext_opt, this.default_opt);
        } else {
          this.opt = _.clone(this.default_opt);
        }
      } else {
        this.opt = _.clone(this.default_opt);
      }
      if (_s.isFunction(this.init_ext)) {
        this.init_ext(arguments);
      }
    },

    render : function() {
      this.model = this.newPaginationBean();
      this.listenTo(this.model, 'refresh', this.refreshTableData);
      this.renderTables();
      this.renderPaginator();
      this._constructTable();
      return this;
    },

    refreshQuery : function() {
      this.search(this.model);
    },

    query : function() {
      this.model.pageNumber = 1;
      this.search(this.model);
    },

    search : function(paginationBean) {
      this._search(paginationBean);
    },

    _search : function(paginationBean) {
      console.log("BaseGrid---searching grid:" + this.name);
      var that = this;
      var spinner = new SpinnerExt.Spinner({
        scale : 0.25,
        blockUI : true
      });
      spinner.start(this.getOverlayEl());
      var searchData = this.createSearchData();
      searchData.pageNumber = paginationBean.pageNumber;
      searchData.pageSize = paginationBean.pageSize;
      paginationBean.fetch({
        type : that.opt.methodType,
        url : that.opt.url,
        data : searchData,
        success : (function(model, response) {
          spinner.stop();
          model.trigger('refresh');
          that.paginator.refresh(that.model);
          if (that.searchCallBack && that.searchCallBack.success) {
            that.searchCallBack.success.apply(that, arguments);
          }
        }),
        error : (function(model, response) {
          spinner.stop();
          console.log("error:" + arguments);
          that.defaultErrorHandler(model, response, function() {
            if (that.searchCallBack && that.searchCallBack.error) {
              that.searchCallBack.error.apply(that, arguments);
            }
          });
        })
      });
    },

    createSearchData : function() {
      return {};
    },

    newPaginationBean : function() {},

    renderTables : function() {},
    renderPaginator : function() {
      this.paginator = new BasePaginator({
        ext_opt : this.opt.paginator_opt
      });
      var that = this;
      this.paginator.clickPage = function(m) {
        that.search(m);
      };
      this.$el.append(this.paginator.$el);
      this.paginator.render();
    },

    constructTable : function() {
      return null;
    },

    _constructTable : function() {
      console.log("BaseGrid---constructing table:" + this.name);
      this.table = this.constructTable();
      this.table.select();
    },

    getSelected : function() {
      var data = this.table.rows({
        selected : true
      });
      var count = data.count();
      if (count > 0) {
        var obj = data.data()[0];
        var selected = this.model.get(obj.id);
        return selected;
      }
      return undefined;
    },

    refreshTableData : function() {
      console.log("BaseGrid---refreshTableData grid:" + this.name);
      this.table.clear();
      this.table.rows.add(this.model.toJSON()).draw(false);
    },

    refreshCurrentPage : function() {
      this.search(this.model);
    },

    getModelByOpElement : function(e) {
      var id = $(e.target).attr('data-id');
      var t = this.model.get(id);
      return t;
    },

    disable : function(e) {
      var that = this;
      BootboxExt.confirm({
        message : "确定要停用此条记录？",
        confirmCallback : function() {
          that._disable.apply(that, [ e ]);
        },
      });
    },

    _doSyncOp : function(e, opName, callback, options) {
      var t = this.getModelByOpElement(e);
      var that = this;
      var spinner = new SpinnerExt.Spinner({
        scale : 0.25,
        blockUI : true
      });
      spinner.start(this.getOverlayEl());
      t.sync(opName, t, _s.extend({
        success : function(model, response) {
          spinner.stop();
          var serverAttrs = t.parse(response);
          if (!t.set(serverAttrs, options)) {
            return false;
          }
          if (callback) {
            callback.apply(that, [ t, model, response ]);
          }
          BootboxExt.info('操作成功！', {
            callback : function() {
              that.model.trigger('refresh');
            }
          });
        },
        error : function(model, response) {
          spinner.stop();
          that.defaultErrorHandler(model, response);
        }
      }), options);
    },

    getOverlayEl : function() {
      var target = this.parentView.$(this.opt.overlayTarget)
      return target;
    },

  });

  return BaseGridView;
});
