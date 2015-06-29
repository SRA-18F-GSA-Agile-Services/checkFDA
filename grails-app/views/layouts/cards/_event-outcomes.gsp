<div class="card">
	<div class="content">
		<h2 class="header"><g:message code="widget.event.outcomes.header"/></h2>
		<div id="chart"></div>
	</div>
</div>
<script>
	$(function() {
		var xAxisLegend = '<g:message code="widget.event.outcomes.legend.xAxis"/>'
		var deviceEvents = $.grep(results.events, function(event) {
			return $.isArray(event.patient);
		});
		var outcomes = deviceEvents.reduce(function(all, cur) {
			var list = cur.patient ? cur.patient.reduce(function(allP, curP) {
				var outcomeList = curP.sequence_number_outcome.map(function(d, i) {
					return d.split(', ')[2 * i + 1];
				});
				return allP.concat(outcomeList);
			}, []) : [];
			return all.concat(list);
		}, []);
		var data = outcomes.reduce(function(map, cur) {
			if (!map[cur]) {
				map[cur] = 0;
			}
			map[cur]++;
			return map;
		}, {});
		var keys = Object.keys(data).sort(function(a, b) {
			return data[b] - data[a];
		});
		var columns = keys.reduce(function(cols, key) {
			cols.push(data[key]);
			return cols;
		}, []);
		var chart = c3.generate({
		    data: {
			    x: 'x',
		        columns: [
		  			['x'].concat(keys),
					[xAxisLegend].concat(columns)
  		        ],
		        type : 'bar'
		    },
		    legend: {
				show: false
			},
		    axis: {
			    x: {
			    	type: 'category'
				},
				y: {
					label: {
						text: 'Number of Outcomes',
						position: 'outer-center'
					}
				},
				rotated: true
			}
		});
	});
</script>