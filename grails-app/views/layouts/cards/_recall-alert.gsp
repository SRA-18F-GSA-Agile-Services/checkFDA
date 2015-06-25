<div class="ui fluid card recall" id="recall-${ id }-card">
	<div class="ui attached ${ message(code: "widget.recall.alert.${ recall.classification.split(' ').join('') }.${ recall.status }.cls") } message">
		<g:message code="widget.recall.alert.${ recall.classification.split(' ').join('') }.message" args="${ [recall.classification] }" />
	</div>
	<div class="content">
		<h2 class="header">${ recall.product_description }</h2>
		<div class="meta">
			<span data-content="${ message(code: 'widget.recall.alert.calendar') }">
				<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', recall.recall_initiation_date).format('M/d/yyyy') }
			</span>
			<span data-content="${ message(code: 'widget.recall.alert.firm') }">
				<i class="icon building outline"></i> ${ recall.recalling_firm }
			</span>
		</div>
		<div class="description">
			${ recall.reason_for_recall }
		</div>
	</div>
</div>