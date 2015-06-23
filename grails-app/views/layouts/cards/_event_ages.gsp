<div class="card">
	<div class="content">
		<h2 class="header">Adverse Event Patient Ages</h2>
		<div id="ages"></div>
	</div>
</div>
<script>
	$(function() {
		var drugEvents = $.grep(results.events, function(event) {
			return !$.isArray(event.patient);
		});
		var ages = drugEvents.reduce(function(all, cur) {
			all.push(cur.patient.patientonsetage);
			return all;
		}, []);
		var data = ages.reduce(function(map, cur) {
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
			bindto: '#ages',
		    data: {
			    x: 'x',
		        columns: [
					['x'].concat(Object.keys(data)),
					['Ages'].concat(columns)
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