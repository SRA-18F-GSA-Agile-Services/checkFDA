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
		var data = ages.reduce(function(map, cur) {
			var floor = Math.floor(cur / 10) * 10;
			var age = cur ? floor + '-' + (floor + 9) : '<g:message code="widget.event.ages.unknown"/>';
			if (!map[age]) {
				map[age] = 0;
			}
			map[age]++;
			return map;
		}, {});
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
		    axis: {
			    x: {
					type: 'category'
				},
				rotated: true
			}
		});
	});
</script>