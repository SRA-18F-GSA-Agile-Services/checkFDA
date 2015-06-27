<div class="ui fluid card">
	<div class="content">
		<h2 class="header"><g:message code="widget.recall.map.header"/></h2>
		<div id="container" style="background-color: #F5FAFF;position: relative;   width: 100%;height: 500px; border: 1px solid #ccc; padding:0px;margin : 0px;"></div>
	</div>
</div>
<script>
	$(function() {
		var map
		map = new Datamap({
			element: document.getElementById('container'),
			scope: 'usa',
			fills: {
				NEUTRAL: '#CCC',
				RECALL: '#CC0',//'red',
				defaultFill: '#ccc'
			},
			data: {},
	        geographyConfig: {
		        borderWidth: 1,
	            borderColor: '#FDFDFD',
	            highlightOnHover: false,
	            popupOnHover: true,
	            popupTemplate: function(geo, data) {	           
		        	if(data!=null){
	                   return '<div class="hoverinfo">' + geo.properties.name +'<br>'+ data.Recalls +"</div>";
		            }
	            }
	        },
	        done: function(datamap) {
	            datamap.svg.selectAll('.datamaps-subunit').on('click', function(geography) {
	               /* selectedState = geography.id;
	                $('#state').val(selectedState);
	                $('#state').trigger("chosen:updated");
	                getData();
	                $("#foodGroup").trigger("change");*/
	            });
	        },       
		 });	
	});
</script>