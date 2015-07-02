<div class="ui fluid card" >
	<div class="content">
		<h2 class="header"><g:message code="widget.recall.map.header"/></h2>
		<div id="container" class="map"></div>
	</div>
</div>
<script>
	var map
	var userLocation
	$(function() {
		var recallStates = $.grep(results.recalls, function(event) {
			return $.inArray( event.status , ['Ongoing', 'Pending'] ) > -1 && event.distribution_states!=null && event.distribution_states.length > 0;
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
			if(results.state.name == key){
				userLocation =[{ name:results.state.name,
								latitude: results.state.latitude , 
								longitude:results.state.longitude , 
								radius: 10, 
								fillKey: 'Home'}]
			}	
			states[key]= stateValuesMap[key]
			return states;
		}, {});
		map = new Datamap({
			element: document.getElementById('container'),
			scope: 'usa',
			responsive: true,
			fills: {
				Ongoing : '<g:message code="widget.recall.map.Ongoing.color"/>' ,
				Pending : '<g:message code="widget.recall.map.Pending.color"/>' ,
				Mixed : '<g:message code="widget.recall.map.Mixed.color"/>' , 
				Home : '<g:message code="widget.recall.map.Home.color"/>' ,
				defaultFill: '<g:message code="widget.recall.map.Defualt.color"/>'
			},
			data: stateColorsMap ,
	        geographyConfig: {
		        borderWidth: 1,
	            borderColor:'#EDEDED',
	            highlightBorderColor: '#bada55',
	            highlightFillColor: '#add8e6',
	            highlightOnHover: true,
	            popupOnHover: true,
	            popupTemplate: function(geo, data) {
		            var html = '<div class="hoverinfo popupInfo" ><h4>' + geo.properties.name + '</h4>' ;
		            if(data){ 
			            html +=	'<div class="ui red message maphover"><i class="<g:message code="widget.recall.alert.Ongoing.icon"/> icon"></i> Ongoing: ' + ((data.Ongoing)? data.Pending : '0')  + '</div>'+
								'<div class="ui attached yellow message maphover"><i class="<g:message code="widget.recall.alert.Pending.icon"/> icon"></i> Pending: ' + ((data.Pending)? data.Pending : '0') +'</div>'+
	                   			'<h4>Total Recalls : ' + data.recalls  +'</h4>';                   		   
		            } else {
			            html +='<h4>Total Recalls : 0</h4>';	 	
			        }
			        return html+ '</div>';
	            }
	        },
	        done: function(datamap) {
	        	datamap.svg.selectAll('.datamaps-subunit').on('click', function(geo) {
	               addSearchFilter (geo.id )
	            });
	            function redraw() {
	                 datamap.svg.selectAll("g").attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
	            }
	        },       
		 });	
		 map.labels({fontSize: 11 }) 
		 map.addPlugin("customLegend", addMapLegend);	    
		 map.customLegend({})
		 if(userLocation){
			 map.bubbles(userLocation,  {
			    popupTemplate:function (geo, data) { 
		            return ['<div class="hoverinfo popupInfo"><h4> User Location: ' +  data.name + '</h4></div>'].join('');
			    }
		    });
		}
		window.addEventListener('resize', function(event){
             map.resize();
		});		  
	});
	
	function addMapLegend(layer, data, options) {
		  data = data || {};
		  if ( !this.options.fills ) {
	      	return;
	      }
		var html = '<dl>';
		var label = '';
		if ( data.legendTitle ) {
			html = '<h1 class="ui header"> ' + data.legendTitle + '</h1>' + html;
		}
		for ( var fillKey in this.options.fills ) {	
  
		    	if (['defaultFill', 'Home'].indexOf(fillKey) > -1) {
		        	if (! data.defaultFillName  ) {
		          		continue;
		        	}
		        	label = data.defaultFillName;
		    	} else {
		        	if (data.labels && data.labels[fillKey]) {
		          		label = data.labels[fillKey];
		        	} else {
		          		label= fillKey + ' ';
		        	}
		      	}
  
    			html += '<dd class="legend ' +  fillKey + '">&nbsp;</dd>';
			html += '<dt><h3>' + label + '</h3></dt>';
  	
		}
		html += '</dl>';
		var hoverover = d3.select( this.options.element ).append('div')
			.attr('class', 'datamaps-legend')
			.html(html);
	}
	
	function  addSearchFilter (state ){
		$('#query').val(results.query+ " "+state) ;
 		checkUserPermission();
	}
</script>