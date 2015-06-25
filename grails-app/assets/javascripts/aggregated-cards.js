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
	var cls = rowId.split('-')[0];
	$('.hidden-cards > .' + cls + '.card:visible').hide();
	$('#' + rowId + '-card').show();
}