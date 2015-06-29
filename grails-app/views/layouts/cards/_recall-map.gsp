<div class="ui fluid card" style="min-height:550px;">
	<div class="content">
		<h2 class="header"><g:message code="widget.recall.map.header"/></h2>
		<div id="container" style="background-color: #F5FAFF;position: relative;   width: 90%;height: 450px; border: 1px solid #ccc; padding:0px;margin : 0px;"></div>
	</div>
</div>
<script>
	var map
	$(function() {
		var recallStates = $.grep(results.recalls, function(event) {
			return $.inArray( event.status , ['Ongoing', 'Pending'] ) > -1 && event.distribution_states.length > 0;
		});
		var stateValuesMap = recallStates.reduce(function(map, cur) {
			var List = cur.distribution_states.reduce(function(key, d) {
				if(!map[d]){
					map[d] = {"recalls" : 0,"status":[] }
				}
				if(!map[d][cur.status]) {
					map[d][cur.status] = 0 ;
				}	
			    map[d]["recalls"]++
				map[d][cur.status]++
				if (map[d]["status"].indexOf(cur.status) == -1) map[d]["status"].push(cur.status);
			}, []);	
			return map;
		}, {});
		var stateColorsMap = Object.keys(stateValuesMap).reduce(function(states, key) {
			if(stateValuesMap[key].status.length > 1){
				stateValuesMap[key].fillKey = "Mixed"
			}else {
				stateValuesMap[key].fillKey= stateValuesMap[key].status[0]
			}
			states[key]= stateValuesMap[key]
			return states;
		}, {});
		map = new Datamap({
			element: document.getElementById('container'),
			scope: 'usa',
			responsive: true,
			projection: 'mercator',
			fills: {
				Neutral: '#CCC',
				Ongoing : '#db2828',
				Pending : '#CC0',
				Mixed : '#FFA500',
				defaultFill: '#CCC'
			},
			data: stateColorsMap ,
	        geographyConfig: {
		        borderWidth: 1,
	            borderColor: '#FDFDFD',
	            highlightOnHover: false,
	            popupOnHover: true,
	            popupTemplate: function(geo, data) {	           
		        	if(data!=null){
	                   return '<div class="hoverinfo">' + geo.properties.name 
	                   									+ '<br>Ongoing: ' + data.Ongoing 
	                   									+ '<br>Pending: ' + ((data.Pending)? data.Pending : '0')
	                   									+ '<br>Total Recalls : ' + data.recalls  +
	                   		  '</div>';
		            }
	            }
	        },
	        done: function(datamap) {
	        },       
		 });	
		 map.labels();
		 map.legend();
	});
</script>