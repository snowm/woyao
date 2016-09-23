define([ 'backbone' ], function() {
  var Category = Backbone.Model.extend({
    urlRoot : "category",
    defaults : function() {
      return {
        id : null,
        name : null,
        code : null,
        parent : null,
        creationDate : null,
        lastModifiedDate : null,
        enabled : false,
      };
    },

    parse : function(data, options) {
      var jsonParent = data.parent;
      if (jsonParent) {
        data.parent = new Category(jsonParent);
      }
      return data;
    },

    toJSON : function() {
      var json = _.clone(this.attributes);
      for ( var attr in json) {
        if ((json[attr] instanceof Backbone.Model) || (json[attr] instanceof Backbone.Collection)) {
          json[attr] = json[attr].toJSON();
        }
      }
      return json;
    },

    sync : function(method, model, options) {
      if (!_s.isUn(options.url)) {
        return Backbone.sync(method, model, options);
      }
      if (method == 'enable') {
        options.url = this.urlRoot + '/enable/' + this.id;
        options.type = 'PUT';
      } else if (method == 'disable') {
        options.url = this.urlRoot + '/disable/' + this.id;
        options.type = 'PUT';
      }
      return Backbone.sync(method, model, options);
    },
  });

  return Category;
});
