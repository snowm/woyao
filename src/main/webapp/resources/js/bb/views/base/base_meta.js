define([ 'bb/collections/meta/misps', 'bb/collections/meta/provinces', 'bb/views/base/base_component' ], 
	function(MISPS, Provinces, BaseComponent) {
		var BaseMetaInfo = BaseComponent.extend({
			misps : new MISPS(),
			mispsFetched : false,
			provinces : new Provinces(), 
			provincesFetched : false,
			
			fetchMISP: function(el) {
				if (this.mispsFetched) {
					this.refreshMISP(el);
					return;
				}
				this.fetchCol(this.misps, el);
			},

			fetchProvinces: function(el) {
				if (this.provincesFetched) {
					this.refreshProvince(el);
					return;
				}
				this.fetchCol(this.provinces, el);
			},

			fetchCol: function(col, el) {
				var that = this;
				col.fetch({
					type: "GET",
					success: (function (model, response) {
						if (el) {
							that.refreshSelect(el, model);
						}
					}),
					error: function(model, response) {
						that.defaultErrorHandler(model, response);
					},
				});
			},

			refreshMISP: function(el) {
				this.refreshSelect(el, this.misps);				
			},

			refreshProvince: function(el) {
				this.refreshSelect(el, this.provinces);
			},

			refreshSelect: function(el, col) {
				col.each(function (m){
					el.append("<option value='" + m.get('id') + "'>" + m.get('name') + "</option>");
				});
			},
		
		});
		return BaseMetaInfo;
	}
);
