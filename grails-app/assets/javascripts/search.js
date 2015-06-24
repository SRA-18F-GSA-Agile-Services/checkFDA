var lat = null
var lng = null
var locationKey = 'location-Permission'
function searchInit() {
	$('#query').focus();
	$('#search').click(search);
	$(window).keyup(function(event) {
		if (event.keyCode == 13) {
			search();
		}
	});	
	retrieveUserResponse (locationKey) == null ? setTimeout(getLocationPermission , 1000) :setTimeout(getGeolocation, 1000) 
}

function search() {
	window.location.href = '/results?q=' + $('#query').val() + '&lat=' +lat + '&lng='+lng;
}

function getLocationPermission () {
	var resp = confirm("Could checkFDA use your current location to give you more tailored information for your area?");
	if (resp){
		getGeolocation();
	}
}

function getGeolocation () {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(locationSuccess, locationError);
	}else {
		console.log("Geolocation is not supported by this browser");
		saveUserResponse ('Not Supported')
	}
}

function locationSuccess (location) {
	lat = location.coords.latitude
	lng = location.coords.longitude
	saveUserResponse (locationKey , 'YES')
}

function locationError () {
	saveUserResponse ('NO')
}

function saveUserResponse (key ,resp){
	window.localStorage.setItem(key, resp);
}

function retrieveUserResponse (key) {
	return window.localStorage.getItem(key)
}