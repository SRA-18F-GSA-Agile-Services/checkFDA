function FilterSet(dataset, tableSelector, labelSelector) {
	this.dataset = dataset;
	this.tableSelector = tableSelector;
	this.labelSelector = labelSelector;
	this.filters = [];
}

FilterSet.prototype = {
	addFilter: function(accessorFn, value, label) {
		this.filters.push(new Filter(this.dataset, accessorFn, value, label));
		return this.filters;
	},
	removeFilter: function(id) {
		var filters = this.filters.filter(function(f) { return f.id == id; });
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
	}
}

function Filter(dataset, accessorFn, value, label) {
	this.dataset = dataset;
	this.accessorFn = accessorFn;
	this.value = value;
	this.label = label;
	this.id = id++;
}

Filter.prototype = {
	apply: function(data) {
		var self = this;
		return data.map(function(item) {
			return item && self.accessorFn(item) == self.value ? item : false;
		});
	},
	renderLabel: function() {
		var html = '<a class="ui label" onclick="removeFilter(\'' + this.dataset + '\', ' + this.id + ')">';
		html += this.label + ': ' + this.value;
		html += '<i class="icon close"></i>';
		html += '</a>';
		return html;
	}
}

var filterSets = {
	drugevents: new FilterSet('drugevents', '#drugevents', '#drugevents-labels')
}, id = 0;

function removeFilter(dataset, id) {
	var filterSet = filterSets[dataset];
	filterSet.removeFilter(id);
	filterSet.rerender();
}