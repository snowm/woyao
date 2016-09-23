define(['jquery', 'backbone', 'cspinner','bootbox', 'views/changed_confirm', 'bb/views/base/base_component'],
    function ($, Backbone, CSpinner, bootbox, ChangedConfirmView, BaseComponent) {
        var BaseEditorView = BaseComponent.extend({
            tagName : "div",
            events: {
                "click #save-btn" : "save",
                "click #exit-btn" : "exit",
                "click #cancel-btn" : "cancel"
            },

            initialize : function() {
            },

            render : function() {
                if (!this.model.isNew()) {
                    this.model.changed = null;
                }
                this.$el.html(this.template(this.model.toJSON()));
                this.afterRender();
                return this;
            },

            afterRender : function() {},

            editMode : function(){
                return this.model.get("id") != "";
            },

            changeValues : function(){},

            changeAttribute: function(attribute, val) {
                var oldVal = this.model.get(attribute);
                if (oldVal != val) {
                    this.model.set(attribute, val, {silent: true});
                }
            },

            validate : function() { return true; },

            isChanged : function() { return false; },

            cancel : function() { },

            save : function() {
                if (!this.validate()) {
                    return;
                }
                this.changeValues();
                this.saveData();
            },

            saveData:function(){
                var isNew = true;
                if (this.model.get("id") != "") {
                    isNew = false;
                }
                var that = this;
                CSpinner.start();
                this.model.save(this.model.attributes, {
                    success: function(model, response) {
                        console.log('successfully saving this record.');
                        var results = that.parentView.paginationBean;
                        var idx = 0;//default
                        if (results) {
                            if (isNew) {
                                results.remove(results.at(that.parentView.paginationBean.numberPerPage-1));
                            } else {
                                idx = results.indexOf(that.model);
                                results.remove(results.at(idx));
                            }
                            results.add(that.model, {at: idx});
                        }
                        CSpinner.stop();
                        if (isNew) {
                            that.parentView.refreshPaginator(1);
                        }
                    },
                    error: function(model, response) {
                        CSpinner.stop();
    					that.defaultErrorHandler(model, response);
                    }
                });
            },

            exit : function() {

                if (this.isChanged()) {
                    var that = this;
                    var confirmView = new ChangedConfirmView();
                    confirmView.confirm({
                        message: "The current record is not saved yet, do you want to save or abandon the changes?",
                        falseCallback: function(){
                            var pv = that.parentView;
                            that.remove();
                            pv.$el.show("normal");
                        }
                    });
                } else {
                    var pv = this.parentView;
                    this.remove();
                    pv.$el.show("normal");
                }
            },
        });

        return BaseEditorView;
    }
);
