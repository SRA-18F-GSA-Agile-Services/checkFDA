<div class="card">
	<div class="content">
		<h2 class="header">Adverse Event Outcomes</h2>
		<div id="chart"></div>
	</div>
</div>
<script>
	$(function() {
		var json = ${ results.events as grails.converters.JSON };
		var outcomes = json.reduce(function(all, cur) {
			var list = cur.patient.reduce(function(allP, curP) {
				return allP.concat(curP.sequence_number_outcome);
			}, []);
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
				}
			}
		});
	});
</script>