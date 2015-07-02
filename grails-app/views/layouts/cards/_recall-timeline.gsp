<div class="ui fluid card">
	<div class="content">
		<h2 class="header"><g:message code="widget.recall.timeline.header"/></h2>
		<div id="recall-timeline"></div>
	</div>
</div>
<script>
	$(function() {
		var xAxisLegend = '<g:message code="widget.recall.timeline.legend.xAxis"/>';
		var yAxisLegend = '<g:message code="widget.recall.timeline.legend.yAxis"/>';
		var typeKeys = ['Food', 'Drugs', 'Devices'];
		var recallInitDates = recalls.map(function(recall) {
			return {
				date: getDate(recall),
				type: recall.product_type
			};
		});
		var aggregatedDatesMap = recallInitDates.reduce(function(map, cur) {
			if (!map[cur.date]) {
				map[cur.date] = typeKeys.reduce(function(keyMap, d) {
					keyMap[d] = 0;
					return keyMap;
				}, {});
			}
			map[cur.date][cur.type]++;
			return map;
		}, {});
		var dateKeys = Object.keys(aggregatedDatesMap);
		var dataColumns = typeKeys.reduce(function(cols, type) {
			var col = [type];
			$.each(dateKeys, function(i, date) {
				col.push(aggregatedDatesMap[date][type]);
			});
			cols.push(col);
			return cols;
		}, [['x'].concat(dateKeys)]);
		var chart = c3.generate({
			bindto: '#recall-timeline',
			data: {
				x: 'x',
				columns: dataColumns,
				types: typeKeys.reduce(function(keyMap, d) {
					keyMap[d] = 'area';
					return keyMap;
				}, {}),
				groups: [typeKeys],
				onclick: function(d, element) {
					var filterSet = filterSets['recalls'];
					filterSet.addFilter(function(item) {
						return getDate(item);
					}, dateKeys.sort()[d.index], 'Date', dateKeys);
					filterSet.rerender();
				}
			},
			color: {
				pattern: ['<g:message code="color.green"/>','<g:message code="color.blue"/>','<g:message code="color.orange"/>']
			},
			axis: {
				x: {
					type: 'timeseries',
					tick: {
						format: '%Y'
					},
					label: {
						text: xAxisLegend,
						position: 'outer-center'
					}
				},
				y: {
					label: {
						text: yAxisLegend,
						position: 'outer-middle'
					}
				}
			}
		});
	});

	function getDate(recall) {
		var date = recall.recall_initiation_date;
		return new Date(date.substring(0, 4) + '/' + date.substring(4, 6) + '/' + date.substring(6, 8)).getFullYear() + '-01-01';
	}
</script>