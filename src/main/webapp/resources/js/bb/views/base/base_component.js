define([ 'backbone', 'bootstrap', 'spinExt', 'snowm', 'bootboxExt' ], function() {
  var BaseComponentView = Backbone.View.extend({
    name : '',
    cachedModel : undefined,
    is_first_show : true,

    initialize : function(options) {
      if (options) {
        this.name = options.name;
      }
      if (this.model) {
        this.cachedModel = this.model.clone();
      }
      if (_s.isFunction(this.init_ext)) {
        this.init_ext(arguments);
      }
    },

    initializeEvents : function() {
      this.on('show', this._onShow, this);
      this.on('shown', this._onShown, this);
      this.on('hide', this._onHide, this);
      this.on('hidden', this._onHidden, this);
      this.on('dataLoad', this._onDataLoad, this);
      this.on('dataLoaded', this._onDataLoaded, this);
      this.on('firstShow', this._onFirstShow, this);
    },

    render : function() {
      this.renderEl();
      this.initValidate();
      return this;
    },
    renderEl : function() {
      return this
    },
    initValidate : function() {},

    isChanged : function() {
      return false;
    },

    validate : function() {
      return true;
    },

    defaultErrorHandler : function(model, response, alertCallback) {
      var errorMsg = "Service error!";
      if (!_s.isBlank(response.statusText)) {
        console.log(response.statusText);
        errorMsg = response.statusText;
      }
      if (response.status == 401) {
        BootboxExt.error(errorMsg, {
          callback : function() {
            window.location.reload();
          }
        });
      } else {
        BootboxExt.error(errorMsg, {
          callback : alertCallback,
        });
      }
    },

    changeAttribute : function(m, attribute, val) {
      var oldVal = m.get(attribute);
      if (oldVal != val) {
        m.set(attribute, val, {
          silent : true
        });
      }
    },

    _onShow : function() {},
    _onShown : function() {},
    _onHide : function() {},
    _onHidden : function() {},
    _onDataLoad : function() {},
    _onDataLoaded : function() {},
    _onFirstShow : function() {},

  });
  return BaseComponentView;
});
