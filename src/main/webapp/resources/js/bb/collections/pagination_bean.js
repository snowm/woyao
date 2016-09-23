define([ 'backbone', 'snowm' ], function(Backbone) {
    var PaginationBean = Backbone.Collection.extend({
        sync  : mySyncFunction,
        totalCount : -1,
        totalPageCount : -1,
        pageSize : 20,
        pageNumber : 1,
        firstResult : 1,
        lastResult : 1,
        calculated : false,

		initialize : function() {
			if (!this.pageSize || this.pageSize<1) {
				throw new Error("-2","Number per page could not be less than 1!");
			}
		},

		parse: function(response) {
			this.totalCount = 0;
			if (!_s.isUn(response.totalCount)) {
				this.totalCount = response.totalCount;
			}
			this.calculate();
		    return response.results;
		},

		setPageNumber : function(pageNumber) {
			this.calculated = false;
			this.pageNumber = pageNumber;
		},

		calculate : function() {
			if (this.totalCount == undefined || this.totalCount<0) {
				throw new Error("-2","No results loaded!");
			}
			this.totalPageCount = Math.ceil(this.totalCount / this.pageSize);
			if (this.pageNumber && (this.pageNumber > this.totalPageCount)) {
				this.pageNumber = this.totalPageCount;
			}
			this.calculated = true;
		},

		getFirstResult : function() {
			if (this.pageNumber == 0) {
				return 0;
			}
			this.firstResult = (this.pageNumber - 1) * this.pageSize + 1;
			return this.firstResult;
		},

		getLastResult : function() {
			if (this.pageNumber == 0) {
				return 0;
			}
			this.lastResult = this.getFirstResult() + this.length - 1;
			return this.lastResult;
		},

		getUrlPrefix : function() {
			return '-p' + this.pageNumber + '-np' + this.pageSize;
		}
    });

	
    function mySyncFunction(method, model, options) {
    	if (!_s.isUn(options.url)) {
            return Backbone.sync(method, model, options);
    	}
        options.url = this.url;
        return Backbone.sync(method, model, options);
    };
    
    return PaginationBean;
});
