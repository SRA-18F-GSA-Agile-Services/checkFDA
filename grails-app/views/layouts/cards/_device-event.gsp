<div class="ui fluid card event">
	<div class="content">
		<div class="ui grid">
			<div class="eight wide column">
				<div class="ui tiny header">
					<g:message code="widget.device.event.report" />
				</div>
				${ event.report_number }
			</div>
			<div class="eight wide column">
				<div class="ui tiny header">
					<g:message code="widget.device.event.receivedate" />
				</div>
				${ new Date().parse('yyyyMMdd', event.date_received).format('M/d/yyyy') }
			</div>
		</div>
	</div>
</div>
