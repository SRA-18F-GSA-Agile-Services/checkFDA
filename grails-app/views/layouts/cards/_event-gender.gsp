<div class="card">
	<div class="content">
		<h2 class="header"><g:message code="widget.event.gender.header"/></h2>
		<div id="gender"></div>
	</div>
</div>
<script>
	var genders = {
		0: '<g:message code="gender.unknown"/>',
		1: '<g:message code="gender.male"/>',
		2: '<g:message code="gender.female"/>'
	};
	$(function() {
		var drugEvents = $.grep(events, function(event) {
			return !$.isArray(event.patient);
		});
		var genderMap = drugEvents.reduce(function(map, cur) {
			var gender = genders[cur.patient.patientsex] || genders[0];
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