define([ 'text!bb/templates/base/dialog.html', 'bb/views/base/base_component' ], function(
    DialogTemplate, BaseComponent) {
  var BaseContentDialogView = BaseComponent.extend({
    template : _.template(DialogTemplate),
    modal_dg : null,
    confirmClose : false,
    formValidator : null,
    readonly : false,
    name : null,
    css : {},

    events : {
      "click #save-btn" : "save",
    },

    initialize : function() {
      var options = arguments[0];
      if (!_s.isUn(options.readonly)) {
        this.readonly = options.readonly;
      }
      if (!_s.isUn(options.name)) {
        this.name = options.name;
      }
      if (!_s.isUn(options.css)) {
        _s.extend(this.css, options.css);
      }
      this.initExt(options);
    },

    initExt : function(options) {},

    render : function() {
      this.$el.html(this.template({name:this.name}));
      this.$('.modal-dialog:first').css("max-width", this.css.maxWidth);
      this.renderEl();
      this.checkReadonly();
      this.renderModalDialog();
      return this;
    },

    checkReadonly : function() {
      if (this.readonly) {
        this.$('input').attr('readonly', 'readonly');
        this.$('select').attr('disabled', 'disabled');
        this.$('textarea').attr('readonly', 'readonly');
        this.$('#save-btn').css('display', 'none');
        this.readonlyRender();
      } else {
        this.writableRender();
      }

    },

    readonlyRender : function() {},
    writableRender : function() {},

    renderModalDialog : function() {
      this.modal_dg = this.$('div.modal.fade');
      this.modal_dg.modal({
        backdrop : 'static',
      });
      var that = this;
      this.modal_dg.on('hide.bs.modal', function(event) {
        that.onHide(event);
      });
      this.modal_dg.on('hidden.bs.modal', function(event) {
        that.onHidden(event);
      });

      _s.checkBootstrapEmbeddedModal(this.modal_dg);
    },

    onHide : function(event) {},
    
    onHidden : function(event) {
      this.clearDialog();
    },

    clearDialog : function() {
      this.unbind();
      this.remove();
    },

    save : function() {
      this.changeValues();
      this.saveData();
    },

    changeValues : function() {
      console.log('changeValues() not implemented!');
    },

    changeAttribute : function(attribute, val) {
      var oldVal = this.model.get(attribute);
      if (oldVal != val) {
        this.model.set(attribute, val, {
          silent : true
        });
      }
    },

    saveData : function() {
      var that = this;
      var spinner = new MySpinner({
        scale : 0.25,
        blockUI : true
      });
      spinner.start();
      this.model.save(this.model.attributes, {
        success : function(model, response) {
          console.log('successfully saving this record.');
          spinner.stop();
          that.bootboxWrapper.info("保存成功！");
        },

        error : function(model, response) {
          spinner.stop();
          that.defaultErrorHandler(model, response);
        },
      });
    },

  });

  return BaseContentDialogView;
});
