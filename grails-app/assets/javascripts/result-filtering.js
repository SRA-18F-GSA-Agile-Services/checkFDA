function FilterSet(dataset, tableSelector, labelSelector) {
	this.dataset = dataset;
	this.tableSelector = tableSelector;
	this.labelSelector = labelSelector;
	this.filters = [];
}

FilterSet.prototype = {
	getFilters: function(value, label) {
		var string = new Filter(null, null, value, label).toString();
		return this.filters.filter(function(filter) {
			return string == filter.toString();
		});
	},
	addFilter: function(accessorFn, value, label, allValues) {
		var self = this;
		if (allValues && allValues.length) {
			allValues.forEach(function(otherValue) {
				self.removeFilter(otherValue, label);
			});
		}
		if (this.getFilters(value, label).length == 0) {
			this.filters.push(new Filter(this.dataset, accessorFn, value, label));
		}
		return this.filters;
	},
	removeFilter: function(value, label) {
		var filters = this.getFilters(value, label);
		if (filters.length == 1) {
			this.filters.splice(this.filters.indexOf(filters[0]), 1);
		}
		return this.filters;
	},
	apply: function() {
		return this.filters.reduce(function(all, filter) {
			return filter.apply(all)
		}, results[this.dataset]);
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
		$(this.labelSelector).html(this.filters.map(function(filter) {
			return filter.renderLabel();
		}).join(''));
		jump(this.dataset + '-header');
	}
}

function Filter(dataset, accessorFn, value, label) {
	this.dataset = dataset;
	this.accessorFn = accessorFn;
	this.value = value;
	this.label = label;
}

Filter.prototype = {
	toString: function() {
		return this.label + ': ' + this.value;
	},
	apply: function(data) {
		var self = this;
		return data.map(function(item) {
			return item && self.accessorFn(item) == self.value ? item : false;
		});
	},
	renderLabel: function() {
		var html = '<a class="ui label" onclick="removeFilter(\'' + this.dataset + '\', \'' + this.value + '\', \'' + this.label + '\')">';
		html += this.toString();
		html += '<i class="icon close"></i>';
		html += '</a>';
		return html;
	}
}

var filterSets = {
	drugevents: new FilterSet('drugevents', '#drugevents', '#drugevents-labels')
};

function removeFilter(dataset, value, label) {
	var filterSet = filterSets[dataset];
	filterSet.removeFilter(value, label);
	filterSet.rerender();
}