<div class="card">
	<div class="content">
		<h2 class="header"><g:message code="widget.event.ages.header"/></h2>
		<div id="ages"></div>
	</div>
</div>
<script>
	$(function() {
		var xAxisLegend = '<g:message code="widget.event.ages.legend.xAxis"/>'
		var drugEvents = $.grep(results.events, function(event) {
			return !$.isArray(event.patient);
		});
		var ages = drugEvents.reduce(function(all, cur) {
			all.push(cur.patient.patientonsetage);
			return all;
		}, []);
		var ageMap = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9].reduce(function(all, cur) {
			all[(cur * 10) + '-' + (cur * 10 + 9)] = 0;
			return all;
		}, {});
		var data = ages.reduce(function(map, cur) {
			var floor = Math.floor(cur / 10) * 10;
			var age = cur ? floor + '-' + (floor + 9) : '<g:message code="widget.event.ages.unknown"/>';
			if (!map[age]) {
				map[age] = 0;
			}
			map[age]++;
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
		        type : 'bar'
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
					padding: {top:0, bottom:0},
					tick: {
						count: d3.max(columns) + 1
					}
				}
			}
		});
	});
</script>