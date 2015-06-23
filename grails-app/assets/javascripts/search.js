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
	window.location.href = '/results?q=' + $('#query').val();
}