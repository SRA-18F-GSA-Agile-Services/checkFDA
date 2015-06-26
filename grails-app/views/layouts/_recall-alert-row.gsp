<g:set var="initiationDate" value="${ new Date().parse('yyyyMMdd', recall.recall_initiation_date).format('M/d/yyyy') }" />
<tr id="recall-${ id }">
	<td class="${ message(code: "widget.recall.alert.${ recall.classification.split(' ').join('') }.cls") }">
		<i class="${ message(code: "widget.recall.alert.${ recall.status }.icon") } icon" data-content="${ recall.status }"></i>
	</td>
	<td>
		${ initiationDate }<br />
		<abbr class="timeago" title="${ initiationDate }"></abbr>
	</td>
	<td class="overflow">
		<b>${ recall.product_description }</b><br />
		<span>${ recall.distribution_pattern }</span>
	</td>
</tr>