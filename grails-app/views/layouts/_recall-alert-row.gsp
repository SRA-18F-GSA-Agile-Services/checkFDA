<g:set var="initiationDate" value="${ new Date().parse('yyyyMMdd', recall.recall_initiation_date).format('yyyy-MM-dd') }" />
<tr id="recall-${ id }">
	<td class="${ message(code: "widget.recall.alert.${ recall.classification.split(' ').join('') }.cls") }"></td>
	<td>
		${ initiationDate }<br />
		<abbr class="timeago" title="${ initiationDate }"></abbr>
	</td>
	<td>
		<b>${ recall.product_description[0..Math.min(50, recall.product_description.size() - 1)] }</b><br />
		${ recall.distribution_pattern[0..Math.min(50, recall.distribution_pattern.size() - 1)] }
	</td>
</tr>