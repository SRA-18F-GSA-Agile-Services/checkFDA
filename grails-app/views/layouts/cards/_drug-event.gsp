<div class="ui fluid card drugevent">
	<div class="content">
		<div class="ui grid">
			<div class="four wide column">
				<div class="ui tiny header">
					<g:message code="widget.drug.event.report" />
				</div>
				${ event.safetyreportid }
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
			<div class="eight wide column">
				<div class="ui tiny header">
					<g:message code="widget.drug.event.outcomes" />
				</div>
				${ ['hospitalization', 'lifethreatening', 'death', 'disabling', 'congenitalanomali'].grep { event['seriousness' + it] }.collect { message(code: 'widget.drug.event.seriousness.' + it) }.join(', ') }
			</div>
			<div class="four wide column">
				<div class="ui tiny header">
					<g:message code="widget.drug.event.country" />
				</div>
				${ event.occurcountry }
			</div>
		</div>

		<div class="ui divider"></div>

		<g:if test="${ event.patient.drug.size() > 0 }">
			<div class="ui small header">
				<g:message code="widget.drug.event.drugs" />
			</div>
			<table class="ui small compact selectable unstackable stripped table">
				<thead>
					<tr>
						<th><g:message code="widget.drug.event.drug.product" /></th>
						<th><g:message code="widget.drug.event.drug.characterization" /></th>
						<th><g:message code="widget.drug.event.drug.action" /></th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${ event.patient.drug.sort { it.drugcharacterization } }" var="drug">
						<tr>
							<td>${ drug.medicinalproduct }</td>
							<td><g:message code="widget.drug.event.drug.characterization${ drug.drugcharacterization }" /></td>
							<td>
								<g:if test="${ drug.actiondrug }">
									<g:message code="widget.drug.event.drug.action${ drug.actiondrug }" />
								</g:if>
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</g:if>

		<g:if test="${ event.patient.reaction.size() > 0 }">
			<div class="ui small header">
				<g:message code="widget.drug.event.reactions" />
			</div>
			<table class="ui small compact selectable unstackable stripped table">
				<thead>
					<tr>
						<th><g:message code="widget.drug.event.reaction.term" /></th>
						<th><g:message code="widget.drug.event.reaction.outcome" /></th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${ event.patient.reaction }" var="reaction">
						<tr>
							<td>${ reaction.reactionmeddrapt }</td>
							<td>
								<g:if test="${ reaction.reactionoutcome }">
									<g:message code="widget.drug.event.reaction.outcome${ reaction.reactionoutcome }" />
								</g:if>
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</g:if>
	</div>
</div>
