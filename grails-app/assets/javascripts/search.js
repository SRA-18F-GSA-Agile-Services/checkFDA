var lat = null;
var lng = null;
var locationKey = 'location-Permission';

function searchInit() {
	$('#query').focus();
	$('#search').click(search);
	$(window).keyup(function(event) {
		if (event.keyCode == 13) {
			search();
		}
	});
	retrieveUserPermission(locationKey) == null ? setTimeout(getLocationPermission, 1000) : setTimeout(getLocation, 1000);
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
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(locationSuccess, locationError);
	} else {
		console.log("Geolocation is not supported by this browser");
		saveUserResponse(locationKey, 'Not Supported');
	}
}

function locationSuccess(location) {
	lat = location.coords.latitude;
	lng = location.coords.longitude;
	saveUserResponse(locationKey, 'YES');
}

function locationError() {
	saveUserResponse(locationKey, 'NO');
}

function saveUserResponse(key, resp) {
	window.localStorage.setItem(key, resp);
}

function retrieveUserPermission(key) {
	return window.localStorage.getItem(key);
}