<div class="card">
	<div class="content">
		<h2 class="header">Adverse Event Outcomes</h2>
		<div id="chart"></div>
	</div>
</div>
<script>
	$(function() {
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
		var columns = Object.keys(data).reduce(function(cols, key) {
			cols.push(data[key]);
			return cols;
		}, []);
		var chart = c3.generate({
		    data: {
			    x: 'x',
		        columns: [
		  			['x'].concat(Object.keys(data)),
		  			['Outcomes'].concat(columns)
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