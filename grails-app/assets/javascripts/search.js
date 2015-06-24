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
	var trimmed =  $.trim( $('#query').val());
	if(trimmed) {
		window.location.href = '/results?q=' + encodeURIComponent(trimmed);
	}
}