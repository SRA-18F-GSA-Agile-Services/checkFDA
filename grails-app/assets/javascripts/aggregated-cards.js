function addRowListeners(tableIdentifier) {
	$(tableIdentifier + ' tr').click(function() {
		selectDetailCard(this);
	});
	selectDetailCard($(tableIdentifier + ' tr').first());
}

function selectDetailCard(me) {
	$('.recall-table tr.active').removeClass('active');
	$(me).addClass('active');
	var rowId = $(me).attr('id');
	$('.recall-alerts > .card:visible').hide();
	$('#' + rowId + '-card').show();
}