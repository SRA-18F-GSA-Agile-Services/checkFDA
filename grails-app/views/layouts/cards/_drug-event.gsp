<div class="ui fluid card drugevent">
	<div class="content">
		<div class="extra content">
			<g:if test="${ event.receivedate }">
				<span data-content="${ message(code: 'widget.drug.event.receivedate') }">
					<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', event.receivedate).format('M/d/yyyy') }
				</span>
			</g:if>

			<span data-content="${ message(code: 'widget.drug.event.report') }">
				<i class="icon file text outline"></i> ${ event.safetyreportid }
			</span>

			<span data-content="${ message(code: 'widget.event.row.patient.drug') }">
				<i class="icon ${ message(code: 'widget.event.row.patientsex.icon' + event.patient.patientsex) }" data-content="${ message(code: 'widget.event.row.patient.' + type) }"></i>
				${ event.patient.patientonsetage }
			</span>

			<g:if test="${ event.occurcountry }">
				<span data-content="${ message(code: 'widget.drug.event.country') }">
					<i class="icon globe"></i> ${ event.occurcountry }
				</span>
			</g:if>
		</div>

		<div class="ui tiny header">
			<g:message code="widget.drug.event.outcomes" />
		</div>
		<p>${ ['hospitalization', 'lifethreatening', 'death', 'disabling', 'congenitalanomali'].grep { event['seriousness' + it] }.collect { message(code: 'widget.drug.event.seriousness.' + it) }.join(', ') }</p>

		<div class="ui divider"></div>

		<g:if test="${ event.patient.drug && event.patient.drug.size() > 0 }">
			<div class="ui small header">
				<g:message code="widget.drug.event.drugs" />
			</div>
			<table class="ui small compact unstackable striped table">
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
			<table class="ui small compact unstackable striped table">
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
