var lat = null;
var lng = null;
var locationKey = 'location-Permission';

function searchInit() {
	$('#query').focus();
	$('#search').click(checkUserPermission);
	$(window).keyup(function(event) {
		if (event.keyCode == 13) {
			checkUserPermission();
		}
	});
}

function checkUserPermission () {
	retrieveUserPermission(locationKey) == null ? getLocationPermission(): getLocation()
}

function search() {
	var trimmed =  $.trim( $('#query').val());
	if (trimmed) {
		window.location.href = '/results?q=' + encodeURIComponent(trimmed) + '&lat=' + lat + '&lng=' + lng;
	}
}

function getLocationPermission() {
	$('.ui.modal').modal('show');
}

function getLocation() {
	if(retrieveUserPermission(locationKey)=='Yes'){
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(locationSuccess,locationError);
		} else {
			console.log("Geolocation is not supported by this browser");
			saveUserResponse(locationKey, 'Not Supported');
			search();
		}
	}else {
		search();
	}
}

function locationSuccess(location) {
	lat = location.coords.latitude;
	lng = location.coords.longitude;
	search();
}

function locationError() {
	search();
}

function saveUserResponse(key, resp) {
	window.localStorage.setItem(key, resp);
}

function retrieveUserPermission(key) {
	return window.localStorage.getItem(key);
}