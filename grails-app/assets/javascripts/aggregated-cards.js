function addRowListeners(tableIdentifier) {
	$(tableIdentifier + ' tr').click(function() {
		selectDetailCard(this);
	});
}

function selectFirstCard(tableIdentifier) {
	if ($(tableIdentifier + ' tr:visible').length) {
		selectDetailCard($(tableIdentifier + ' tr:visible').first(), true);
	}
}

function selectDetailCard(me, initializing) {
	$(me).parent().children('tr.active').removeClass('active');
	$(me).addClass('active');
	var rowId = $(me).attr('id');
	$('#' + rowId + '-card').show();
	var arrayIndex = parseInt(rowId.split('-')[1]);
	var type = $(me).parents('table').attr('id');
	var element = results[type][arrayIndex];
	$.ajax({
		url: '/search/renderCard',
		method: 'POST',
		data: {
			json: JSON.stringify(element),
			type: type
		},
		success: function(data) {
			$('#' + type + '-card').html(data);
			$('#' + type + '-card [data-content]').popup({
				position: 'top center'
			});
		}
	});
}

function jump(h) {
	var top = document.getElementById(h).offsetTop;
	window.scrollTo(0, top);
}
