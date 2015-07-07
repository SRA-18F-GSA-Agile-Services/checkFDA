<div class="ui fluid card recall">
	<div class="ui attached ${ message(code: "widget.recall.alert.${ recall.classification.split(' ').join('') }.cls") } message">
		<i class="${ message(code: "widget.recall.alert.${ recall.status }.icon") } icon"></i>
		${ recall.status }, <g:message code="widget.recall.alert.${ recall.classification.split(' ').join('') }.message" args="${ [recall.classification] }" />
	</div>
	<div class="content">
		<i class="circular help icon" data-content="${ message(code: 'widget.recall.alert.help', args: [query]) }"></i>
		<div class="extra content">
			<span data-content="${ message(code: 'widget.recall.alert.calendar') }">
				<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', recall.recall_initiation_date).format('M/d/yyyy') }
			</span>
		</div>

		<div class="ui small header">${ recall.product_description }</div>

		<div class="ui tiny header">
			<g:message code="widget.recall.alert.distribution" />
		</div>
		<p>${ recall.distribution_pattern }</p>

		<div class="ui divider"></div>

		<div class="extra content">
			<span data-content="${ message(code: 'widget.recall.alert.voluntary') }">
				<i class="icon flag outline"></i> ${ recall.voluntary_mandated }
			</span>

			<span data-content="${ message(code: 'widget.recall.alert.firm') }">
				<i class="icon building outline"></i> ${ recall.recalling_firm }
			</span>
		</div>

		<div class="ui tiny header">
			<g:message code="widget.recall.alert.reason" />
		</div>
		<p>${ recall.reason_for_recall }</p>

		<div class="ui tiny header">
			<g:message code="widget.recall.alert.code" />
		</div>
		<p>${ recall.code_info }</p>

		<div class="ui tiny header">
			<g:message code="widget.recall.alert.quantity" />
		</div>
		<p>${ recall.product_quantity }</p>
	</div>
</div>
