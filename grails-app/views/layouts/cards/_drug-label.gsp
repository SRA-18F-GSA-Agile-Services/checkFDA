<div class="ui fluid card druglabel" id="druglabel-${ id }-card">
	<div class="content">
		<div class="extra content">
			<span data-content="${ message(code: 'widget.drug.label.calendar') }">
				<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', label.effective_time).format('M/d/yyyy') }
			</span>
		</div>

		<div class="ui small header">
			${ label.openfda.brand_name.join(', ').replaceAll('"', '') }
			<span class="generic">(${ label.openfda.generic_name.join(', ').replaceAll('"', '') })</span>
		</div>

		<div class="ui sub header">
			${ label.purpose.join(', ').replaceAll('"', '') }
		</div>

		<g:if test="${ label.indications_and_usage }">
			<div class="ui tiny header">
				<g:message code="widget.drug.label.usage" />
			</div>
			<p>${ label.indications_and_usage.join(', ').replaceAll('"', '') }</p>
		</g:if>
		<g:if test="${ label.dosage_and_administration }">
			<div class="ui tiny header">
				<g:message code="widget.drug.label.dosage" />
			</div>
			<p>${ label.dosage_and_administration.join(', ').replaceAll('"', '')  }</p>
		</g:if>
		<g:if test="${ label.active_ingredient }">
			<div class="ui tiny header">
				<g:message code="widget.drug.label.active" />
			</div>
			<p>${ label.active_ingredient.join(', ').replaceAll('"', '')  }</p>
		</g:if>
		<g:if test="${ label.inactive_ingredient }">
			<div class="ui tiny header">
				<g:message code="widget.drug.label.inactive" />
			</div>
			<p>${ label.inactive_ingredient.join(', ').replaceAll('"', '')  }</p>
		</g:if>

		<div class="ui divider"></div>

		<div class="ui tiny header">
			<g:message code="widget.drug.label.warnings" />
		</div>
		<g:if test="${ label.warnings }">
			<p>${ label.warnings.join(', ').replaceAll('"', '')  }</p>
		</g:if>
		<g:if test="${ label.pregnancy_or_breast_feeding }">
			<p><b>${ label.pregnancy_or_breast_feeding.join(', ').replaceAll('"', '')  }</b></p>
		</g:if>
		<g:if test="${ label.keep_out_of_reach_of_children }">
			<p><b>${ label.keep_out_of_reach_of_children.join(', ').replaceAll('"', '')  }</b></p>
		</g:if>

		<div class="ui divider"></div>

		<div class="extra content">
			<span data-content="${ message(code: 'widget.drug.label.manufacturer') }">
				<i class="icon building outline"></i> ${ label.openfda.manufacturer_name.join(', ').replaceAll('"', '')  }
			</span>
		</div>
	</div>
</div>