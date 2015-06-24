var lat = null
var lng = null
function searchInit() {
	$('#query').focus();
	$('#search').click(search);
	$(window).keyup(function(event) {
		if (event.keyCode == 13) {
			search();
		}
	});
}

function search() {
	window.location.href = '/results?q=' + $('#query').val() + '&lat=' +lat + '&lng='+lng;
}

function geolocation () {
	var resp = confirm("Could checkFDA use your current location to give you more tailored information for your area?");
	if (resp){
		getGeolocation();
	}
}
function getGeolocation () {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(locationSuccess, locationError);
	}
	else {
		console.log("Geolocation is not supported by this browser");
	}
}
function locationSuccess (location) {
	lat = location.coords.latitude
	lng = location.coords.longitude
}
function locationError () {
}