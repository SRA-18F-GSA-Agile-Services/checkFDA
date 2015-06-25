<tr id="recall-${ id }">
	<td class="${ message(code: "widget.recall.alert.${ recall.classification.split(' ').join('') }.cls") }"></td>
	<td>${ recall.product_description[0..Math.min(40, recall.product_description.size() - 1)] }</td>
	<td>${ recall.distribution_pattern[0..Math.min(40, recall.distribution_pattern.size() - 1)] }</td>
</tr>