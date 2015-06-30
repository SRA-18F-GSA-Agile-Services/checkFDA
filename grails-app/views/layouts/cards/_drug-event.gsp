<div class="ui fluid card drugevent">
	<div class="content">
		<div class="ui grid">
			<div class="four wide column">
				<div class="ui tiny header">
					<g:message code="widget.drug.event.report" />
				</div>
				${ event.safetyreport }
			</div>
			<div class="four wide column">
				<div class="ui tiny header">
					<g:message code="widget.drug.event.receivedate" />
				</div>
				<g:if test="${ event.receivedate }">
					${ new Date().parse('yyyyMMdd', event.receivedate).format('M/d/yyyy') }
				</g:if>
			</div>
			<div class="four wide column">
				<div class="ui tiny header">
					<g:message code="widget.drug.event.patientsex" />
				</div>
				<g:message code="widget.drug.event.patientsex${ event.patient.patientsex }" />
			</div>
			<div class="four wide column">
				<div class="ui tiny header">
					<g:message code="widget.drug.event.onsetage" />
				</div>
				${ event.patient.patientonsetage }
			</div>
		</div>
	</div>
</div>
