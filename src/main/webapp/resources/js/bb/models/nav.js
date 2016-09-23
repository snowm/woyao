define([ 'backbone'], 
	function() {
		var _Navs = Backbone.Collection;
	
		var Nav = Backbone.Model.extend({
			idAttribute: name,
	        defaults : function() {
	            return {
	            	isRoot : false,
	                name : undefined,
	                cselector : undefined,
	                aselector : undefined,
	                click : undefined,
	                parent : undefined,
	                children : new _Navs(),
	                forceRemove: false,
	                routePath : undefined,
	                isActive : false,
	            };
	        },

	        isRoot : function() {
	        	return this.get('isRoot');
	        },

	        isActive : function() {
	        	return this.get('isActive');
	        },

	        addChildren : function(children) {
	        	if (!this.get('children')) {
	        		this.set('children', new _Navs());
	        	}
	        	if (_.isArray(children)) {
	        		for (var i = 0;i < children.length;i++) {
	        			var child = children[i];
	        			child.parent = this;
	        			this.get('children').push(children[i]);
	        		}
	        	} else if (_.isObject(children)) {
	        		this.get('children').push(children);
	        	}
	        },

	        removeChildren : function(children) {
	        	if (!this.get('children')){
	        		return;
	        	}

				var singular = !_.isArray(children);
				children = singular ? [children] : children;
				for (var i = 0;i < children.length;i++) {
	        		var child = children[i];
	        		child.parent = undefined;
				}
	        	this.get('children').remove(children);
	        },

	        getAncestries : function() {
	        	var ancestries = new _Navs();
	        	var parent = this.get('parent');
	        	while (parent) {
	        		ancestries.push(parent);
	        		parent = parent.get('parent');
	        	}
	        	return ancestries;
	        },

	        forEachNavs : function(handler) {
	        	_forEachNavs(this, handler);
	        },

	        activeToggle : function(name) {
	        	var nav = this.getNav(name);
	        	nav.set('isActive', true);
	        	return nav;
	        },

	        queryNavs : function(criteira, comparator) {
				return _queryNavs(this, name, comparator);
	        },

	        getActiveNavs : function() {
				return _queryNavs(this, name, function comparator(nav, name){
			        return (nav.get('isActive') && nav.get('isActive') == true);
				});
	        },

	        getNav : function(name) {
				var rs = _queryNavs(this, name, function comparator(nav, name){
			        return nav.get('name') == name;
				});
				if (rs.length > 0){
					return rs.models[0];
				}
				return undefined;
	        },

		});

		function _queryNavs(nav, criteira, comparator, rs) {
			rs || (rs = new _Navs())
		    if (comparator(nav, criteira)) {
		    	rs.push(nav);
		    }
			var children = nav.get('children');
			for (var i = 0;i < children.length;i++){
				_queryNavs(children.models[i], criteira, comparator, rs);
			}
			return rs;
		};
		function _forEachNavs(nav, handler) {
			handler(nav);
			var children = nav.get('children');
			children.each(function (child) {
				_forEachNavs(child, handler);
			});
		};
		return Nav;

});
