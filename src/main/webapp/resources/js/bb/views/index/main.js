define([ 'text!bb/templates/index/main.html', 'bb/views/base/base_container_content' ], function(
    IndexMainTemplate, BaseContainerContentView) {

  var IndexMainView = BaseContainerContentView.extend({

    init_ext : function() {
      console.log('index main page initialized');
      this.force_refresh_data = true;
      // this.exceptionOrderView = new ExceptionOrderMainView();
      // this.exceptionOrderView.name = 'indexExceptionOrderGrid';
      // this.exceptionOrderView.parentView = this;
      // this.errorAliOrderView = new ErrorAliOrderMainView();
      // this.errorAliOrderView.name = 'indexErrorAliOrderGrid';
      // this.errorAliOrderView.parentView = this;
    },

    render : function() {
      this.$el.append(_.template(IndexMainTemplate));
      // this.$('#exception-order-p').append(this.exceptionOrderView.$el);
      // this.exceptionOrderView.render();
      // this.$('#error-ali-order-p').append(this.errorAliOrderView.$el);
      // this.errorAliOrderView.render();
      // var d = this.wait1();
      // $.when(this.wait1()).done(function(arg1) {
      // console.log('all done!'+arg1)
      // });
      
      var m = new Backbone.Model();
      var h1 = function(){
        console.log('handler1:');
        console.log(arguments);
      };
      m.on('self', h1);
      var h2 = function(){
        console.log('handler2:');
        console.log(arguments);
      };
      m.on('self', h2);
      m.trigger('self','arg1', 'arg2');
      
      m.off('self', h2);
      m.trigger('self', 'removed h2');
      
      console.log(m.isNew());
      m.set({'attr1':'attr1-value'});
      console.log("after set:"+m.isNew());
      m.set({'id':123});
      console.log("after set id:"+m.isNew());
      
      var wait = function(dfr, name, delay) {
        // var dfr = $.Deferred();
        setTimeout(function() {
          console.log(name + " done!");
          dfr.resolve(name);
        }, delay);
        return dfr.promise();
      };
      var dfr1 = $.Deferred();
      var dfr2 = $.Deferred();
      // $.when(wait(dfr1, 'wait1', 1000), wait(dfr2, 'wait2', 2000)).done(function(arg1, arg2) {
      // console.log(arg1);
      // console.log(arg2);
      // console.log('all done!');
      // });

      return this;
    },

    showed : function() {
    // this.exceptionOrderView.showed();
    // this.errorAliOrderView.showed();
    },
    hidden : function() {
    // this.exceptionOrderView.hidden();
    // this.errorAliOrderView.hidden();
    },

  });

  return IndexMainView;

});
