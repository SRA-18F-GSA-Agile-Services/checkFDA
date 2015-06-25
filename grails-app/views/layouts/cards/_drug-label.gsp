<div class="ui fluid card" id="label-${ id }-card">
	<div class="content">
		<div class="meta">
			<span data-content="${ message(code: 'widget.drug.label.calendar') }">
				<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', label.effective_time).format('M/d/yyyy') }
			</span>
		</div>
		<h2 class="header">${ label.openfda.brand_name.join(', ').replaceAll('"', '') } (${ label.openfda.generic_name.join(', ').replaceAll('"', '') })</h2>
		<div class="meta">
			<span class="date">${ label.purpose.join(', ').replaceAll('"', '') }</span>
		</div>
		<div class="description">
			<h4 class="ui sub header"><g:message code="widget.drug.label.usage" /></h4>
			<p>${ label.indications_and_usage.join(', ').replaceAll('"', '')  }</p>
			<h4 class="ui sub header"><g:message code="widget.drug.label.dosage" /></h4>
			<p>${ label.dosage_and_administration.join(', ').replaceAll('"', '')  }</p>
			<h4 class="ui sub header"><g:message code="widget.drug.label.active" /></h4>
			<p>${ label.active_ingredient.join(', ').replaceAll('"', '')  }</p>
			<h4 class="ui sub header"><g:message code="widget.drug.label.inactive" /></h4>
			<p>${ label.inactive_ingredient.join(', ').replaceAll('"', '')  }</p>
		</div>
	</div>
</div>