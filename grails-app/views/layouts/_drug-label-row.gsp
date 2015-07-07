<g:set var="effectiveTime" value="${ new Date().parse('yyyyMMdd', label.effective_time).format('M/d/yyyy') }" />
<tr id="druglabel-${ id }">
	<td>
		<span data-content="${ message(code: 'widget.drug.label.calendar') }">
			<i class="icon calendar outline"></i> ${ effectiveTime }
		</span>
	</td>
	<td class="overflow">
		<b>${ label.openfda.brand_name.join(', ').replaceAll('"', '') }</b>
	</td>
</tr>