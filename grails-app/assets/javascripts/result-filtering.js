function FilterSet(tableSelector) {
	this.tableSelector = tableSelector;
	this.filters = [];
}

FilterSet.prototype = {
	addFilter: function(accessorFn, value) {
		this.filters.push(new Filter(accessorFn, value));
		return this.filters;
	},
	removeFilter: function(id) {
		var filters = this.filters.filter(function(f) { return f.id == id; });
		if (filters.length == 1) {
			this.filters.splice(this.filters.indexOf(filters[0]), 1);
		}
		return this.filters;
	},
	apply: function(data) {
		return this.filters.reduce(function(all, filter) {
			return filter.apply(all)
		}, data);
	},
	rerender: function(data) {
		var data = this.apply(data);
		$(this.tableSelector + ' tr').toArray().forEach(function(row, index) {
			if (data[index]) {
				$(row).show();
			} else {
				$(row).hide();
			}
		});
	}
}

function Filter(accessorFn, value) {
	this.accessorFn = accessorFn;
	this.value = value;
	this.id = id++;
}

Filter.prototype = {
	apply: function(data) {
		var self = this;
		return data.map(function(item) {
			return item && self.accessorFn(item) == self.value ? item : false;
		});
	}
}

var filterSets = {
	drugevents: new FilterSet('#drugevents')
}, id = 0;