<div class="card">
	<div class="content">
		<h2 class="header">
			<i class="circular help icon" data-content="${ message(code: 'widget.event.ages.help', args: [query]) }"></i>
			<g:message code="widget.event.ages.header"/>
		</h2>
		<div id="ages"></div>
	</div>
</div>
<script>
	$(function() {
		var xAxisLegend = '<g:message code="widget.event.ages.legend.xAxis"/>'
		var drugEvents = $.grep(events, function(event) {
			return !$.isArray(event.patient);
		});
		var ages = drugEvents.reduce(function(all, cur) {
			all.push(cur.patient.patientonsetage);
			return all;
		}, []);
		var ageMap = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10].reduce(function(all, cur) {
			all[(cur * 10) + 's'] = 0;
			return all;
		}, {'<g:message code="widget.event.ages.unknown"/>': 0});
		var data = ages.reduce(function(map, cur) {
			var floor = Math.floor(cur / 10) * 10;
			var age = cur ? floor + 's' : '<g:message code="widget.event.ages.unknown"/>';
			if (map[age] != undefined) {
				map[age]++;
			}
			return map;
		}, ageMap);
		var columns = Object.keys(data).reduce(function(cols, key) {
			cols.push(data[key]);
			return cols;
		}, []);
		var chart = c3.generate({
			bindto: '#ages',
		    data: {
			    x: 'x',
		        columns: [
					['x'].concat(Object.keys(data)),
					[xAxisLegend].concat(columns)
		        ],
		        type : 'bar',
				onclick: function(d, element) {
					var filterSet = filterSets['drugevents'];
					filterSet.addFilter(function(item) {
						var age = item.patient.patientonsetage;
						var floor = Math.floor(age / 10) * 10;
						return age ? floor + 's' : '<g:message code="widget.event.ages.unknown"/>';
					}, Object.keys(ageMap)[d.index], 'Age', Object.keys(ageMap));
					filterSet.rerender();
				}
		    },
		    color: {
				pattern: ['<g:message code="color.blue"/>']
			},
		    legend: {
				show: false
			},
		    axis: {
			    x: {
					type: 'category',
					label: {
						text: 'Age Group',
						position: 'outer-center'
					}
				},
				y: {
					label: {
						text: 'Number of Adverse Events',
						position: 'outer-middle'
					},
					max: d3.max(columns),
					padding: {top:0, bottom:0}
				}
			}
		});
	});
</script>