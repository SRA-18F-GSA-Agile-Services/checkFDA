function addRowListeners(tableIdentifier) {
	$(tableIdentifier + ' tr').click(function() {
		selectDetailCard(this);
	});
	if ($(tableIdentifier + ' tr').length) {
		selectDetailCard($(tableIdentifier + ' tr').first());
	}
}

function selectDetailCard(me) {
	$(me).parent().children('tr.active').removeClass('active');
	$(me).addClass('active');
	var rowId = $(me).attr('id');
	$('#' + rowId + '-card').show();
	var arrayIndex = parseInt(rowId.split('-')[1]);
	var type = $(me).parents('table').attr('id');
	var element = results[type][arrayIndex];
	$.ajax({
		url: '/search/renderCard',
		data: {
			json: JSON.stringify(element),
			type: type
		},
		success: function(data) {
			$('#' + type + '-card').html(data);
			jump(rowId + '-card');
		}
	});
}

function jump(h) {
	var top = document.getElementById(h).offsetTop;
	window.scrollTo(0, top);
}
