<div class="card">
	<div class="content">
		<h2 class="header">Drug Event Patient Gender Distribution</h2>
		<div id="gender"></div>
	</div>
</div>
<script>
	var genders = {
		0: 'Unknown',
		1: 'Male',
		2: 'Female'
	};
	$(function() {
		var drugEvents = $.grep(results.events, function(event) {
			return !$.isArray(event.patient);
		});
		var genderMap = drugEvents.reduce(function(map, cur) {
			var gender = genders[cur.patient.patientsex];
			if (!map[gender]) {
				map[gender] = 0;
			}
			map[gender]++;
			return map;
		}, {});
		var columns = Object.keys(genderMap).reduce(function(cols, key) {
			var col = [key].concat(genderMap[key]);
			cols.push(col);
			return cols;
		}, []);
		var chart = c3.generate({
			bindto: '#gender',
		    data: {
		        columns: columns,
		        type : 'pie'
		    }
		});
	});
</script>