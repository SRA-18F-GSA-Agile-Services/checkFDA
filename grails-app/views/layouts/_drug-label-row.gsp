<g:set var="effectiveTime" value="${ new Date().parse('yyyyMMdd', label.effective_time).format('yyyy-MM-dd') }" />
<tr id="druglabel-${ id }">
	<td class="${ message(code: "widget.drug.label.${ label.openfda.product_type[0].split(' ').join('') }.cls") }"><i class="calendar outline icon"></i></td>
	<td>
		${ effectiveTime }
	</td>
	<td class="overflow">
		<b>${ label.openfda.brand_name.join(', ').replaceAll('"', '') }</b>
	</td>
</tr>