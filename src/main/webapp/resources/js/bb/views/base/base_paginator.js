define([ 'text!bb/templates/base/paginator.html', 
	'backbone', 'snowm', 'bootstrap-paginator'], 
		function(PaginatorTemplate) {

	var BasePaginatorView = Backbone.View.extend({
		tagName : 'div',
		default_opt : {
			size : 'small',
			numberOfPages : 5,
			page_select : false,
			info_enable : false,
			client_page : false,
		},
		$paginator_select : undefined,
		$paginator : undefined,
		$info_container : undefined,
		$info_from : undefined,
		$info_to : undefined,
		$info_totalResultCount : undefined,

	
		initialize : function() {
			if (arguments.length > 0) {
				var options = arguments[0];
				this.model = options.paginationBean;
				if (!_s.isUn(options.ext_opt)) {
					this.opt = _.defaults(options.ext_opt, this.default_opt);
				} else {
					this.opt = _.clone(this.default_opt);
				}
			} else {
				this.opt = _.clone(this.default_opt);
			}
		},

		render : function() {
			this.$el.html(_.template(PaginatorTemplate));
			this.$paginator_container = this.$('div[name="paginator-container"]');
			this.$paginator_select = this.$paginator_container.find('select[name="paginator_select"]');
			this.$paginator = this.$paginator_container.find('ul[name="paginator"]');
			this.$info_container = this.$('div[name="info_container"]');
			this.$info_from = this.$info_container.find('span[name="info_from"]');
			this.$info_to = this.$info_container.find('span[name="info_to"]');
			this.$info_totalResultCount = this.$info_container.find('span[name="info_totalResultCount"]');
			_s.bindEvent(this.$paginator_select, 'change', this.navPage, this);
			if (!this.opt.page_select) {
				this.$paginator_select.hide();
			}
			if (!this.opt.info_enable) {
				this.$info_container.hide();
			}
			if (!_s.isUn(this.model)) {
				this.refresh();
			}
			return this;
		},

		refresh : function(paginationBean){
			if (paginationBean) {
				this.model = paginationBean;
			}
			this.refreshInfo();
			this.refreshPaginator();
		},

		refreshInfo: function(paginationBean) {
			if (!this.opt.info_enable) {
				return;
			}
			if (paginationBean) {
				this.model = paginationBean;
			}
			this.$info_from.html(this.model.getFirstResult());
			this.$info_to.html(this.model.getLastResult());
			this.$info_totalResultCount.html(this.model.totalCount);
		},

		refreshPaginator: function(paginationBean) {
			if (paginationBean) {
				this.model = paginationBean;
			}
			if (this.model.totalCount == 0){
				this.$paginator_select.hide();
				this.$paginator.hide();
				return;
			}
			var that = this;
			if (this.opt.page_select) {
				this.$paginator_select.empty();
				for (var i = this.model.totalPageCount;i > 0;i--) {
					var option = $('<option/>');
					option.val(i);
					option.append(i);
					if (i == this.model.pageNumber) {
						option.prop('selected', true);
					}
					option.prependTo(this.$paginator_select);
				}
			}
			this.$paginator.bootstrapPaginator({
				size: this.opt.size,
				bootstrapMajorVersion: 3,
				useBootstrapTooltip: true,
				currentPage: this.model.pageNumber,
				totalPages: this.model.totalPageCount,
				numberOfPages: this.opt.numberOfPages,
				onPageClicked: function(e, originalEvent, type, page) {
				 	that.model.setPageNumber(page);
					that.clickPage(that.model);
				},
				tooltipTitles: function (type, page, current) {
                    switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上页";
                    case "next":
                        return "下页";
                    case "last":
                        return "末页";
                    case "page":
                        return "第" + page + "页";
                    }
                }
			});
		},
		
		navPage : function(event) {
			var target = event.currentTarget;
			var page = this.$(target).val();
			this.model.setPageNumber(page);
			this.clickPage(this.model);
		},

		clickPage : function(model) {},
	
	});

	return BasePaginatorView;
});
