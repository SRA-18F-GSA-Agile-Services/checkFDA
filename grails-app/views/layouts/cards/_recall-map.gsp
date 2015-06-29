<div class="ui fluid card maps" >
	<div class="content">
		<h2 class="header"><g:message code="widget.recall.map.header"/></h2>
		<div id="container" class="map"></div>
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
				Neutral : '<g:message code="widget.recall.map.Defualt.color"/>' ,
				Ongoing : '<g:message code="widget.recall.map.Ongoing.color"/>' ,//'#EB5E66',//'#db2828',
				Pending : '<g:message code="widget.recall.map.Pending.color"/>' ,//'#CC0',
				Mixed : '<g:message code="widget.recall.map.Mixed.color"/>' , //'#FC8D59',
				defaultFill: '<g:message code="widget.recall.map.Defualt.color"/>'//'#ABDDA4'
			},
			data: stateColorsMap ,
	        geographyConfig: {
		        borderWidth: 1,
	            borderColor:'#FDFDFD',
	            highlightOnHover: false,
	            popupOnHover: true,
	            popupTemplate: function(geo, data) {	           
		        	if(data!=null){
	                   return '<div class="hoverinfo">' + geo.properties.name 
	                   									+ '<br><i class="<g:message code="widget.recall.alert.Ongoing.icon"/> icon"></i> Ongoing: ' + data.Ongoing 
	                   									+ '<br><i class="<g:message code="widget.recall.alert.Pending.icon"/> icon"></i> Pending: ' + ((data.Pending)? data.Pending : '0')
	                   									+ '<br>Total Recalls : ' + data.recalls  
	                   		  '</div>';
		            }
	            }
	        },
	        done: function(datamap) {
	        	 // datamap.svg.selectAll('.datamaps-subunit').on('click', function(geography) {
	               /* selectedState = geography.id;
	                $('#state').val(selectedState);
	                $('#state').trigger("chosen:updated");
	                getData();
	                $("#foodGroup").trigger("change");*/
	           // });
	           // datamap.svg.call(d3.behavior.zoom().on("zoom", redraw));

	            function redraw() {
	                 datamap.svg.selectAll("g").attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
	            }
	        },       
		 });	
		 map.labels();
		 map.legend();
	});
</script>