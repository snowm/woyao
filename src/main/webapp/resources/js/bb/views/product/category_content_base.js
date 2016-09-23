define([ 'text!bb/templates/product/category_content_base.html', 'bb/views/base/base_component' ],
    function(CategoryContentBaseTemplate, BaseComponent) {
      var CategoryContentBaseView = BaseComponent.extend({
        template : _.template(CategoryContentBaseTemplate),
        formValidator : null,

        init_ext : function() {
          var options = arguments[0];
        },

        renderEl : function() {
          this.$el.html(this.template(this.model.toJSON()));
          return this;
        },

        initValidate : function() {
          this.$('form#category_content_base_form').validate({
            rules : {
              product_name : "required",
              product_code : "required",
            },
          });
        },

        validate : function() {
          return this.$('form#category_content_base_form').valid();
        },

        changeValues : function() {
          this.changeAttribute(this.model, "name", this.$('#product_name').val());
          this.changeAttribute(this.model, "code", this.$('#product_code').val());
        },

        save : function() {
          if (!this.isChanged() || !this.validate()) {
            return;
          }
          this.changeValues();
          var that = this;
          var spinner = new SpinnerExt.Spinner({
            scale : 0.25,
            blockUI : true
          });
          spinner.start(this.parentView.$('div.modal-dialog:first'));
          var isNew = _s.isUn(this.model.get('id'));
          this.model.save(this.model.attributes, {
            success : function(model, response) {
              console.log('successfully saving this record.');
              spinner.stop();
              BootboxExt.info("保存成功！", {
                callback : function() {
                  var col = that.parentView.parentView.model;
                  if (isNew) {
                    col = that.parentView.parentView.gridView.model;
                  }
                  var length = col.length;
                  col.add(model, {
                    at : 0,
                    merge : true,
                  });
                  col.trigger("refresh");
                }
              });
            },

            error : function(model, response) {
              spinner.stop();
              that.defaultErrorHandler(model, response);
            },
          });
        },
      });

      return CategoryContentBaseView;
    });
