<g:set var="initiationDate" value="${ new Date().parse('yyyyMMdd', recall.recall_initiation_date).format('yyyy-MM-dd') }" />
<tr id="recall-${ id }">
	<td class="${ message(code: "widget.recall.alert.${ recall.classification.split(' ').join('') }.cls") }"></td>
	<td>
		${ initiationDate }<br />
		<abbr class="timeago" title="${ initiationDate }"></abbr>
	</td>
	<td class="overflow">
		<b>${ recall.product_description }</b><br />
		${ recall.distribution_pattern }
	</td>
</tr>