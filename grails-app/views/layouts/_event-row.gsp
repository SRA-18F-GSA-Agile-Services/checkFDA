<tr id="event-${ id }">
	<td>
		<span data-content="${ message(code: 'widget.event.row.report') }">
			${ isDrug ? event.safetyreportid : event.report_number }
		</span>
	</td>
	<td>
		<span data-content="${ message(code: 'widget.event.row.calendar.' + type) }">
			<g:if test="${ event.receivedate || event.date_received }">
				<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', isDrug ? event.receivedate : event.date_received).format('M/d/yyyy') }
			</g:if>
		</span>
	</td>
	<td>
		<span data-content="${ message(code: 'widget.event.row.patient.' + type) }">
			<g:if test="${ isDrug }">
				<i class="icon ${ message(code: 'widget.event.row.patientsex.icon' + event.patient.patientsex) }"></i>
			</g:if>
			 ${ isDrug ? event.patient.patientonsetage : (event.patient.size() + ' ' + message(code: 'widget.event.row.patient.device.patients')) }
		 </span>
	</td>
	<td class="overflow">
		<span data-content="${ message(code: 'widget.event.row.outcomes') }">
			<g:if test="${ !isDrug }">
				${ event.patient*.sequence_number_outcome*.collect { it.split(', ').last() }*.join(', ').join('<br />') }
			</g:if>
			<g:else>
				${ ['hospitalization', 'lifethreatening', 'death', 'disabling', 'congenitalanomali'].grep { event['seriousness' + it] }.collect { message(code: 'widget.drug.event.seriousness.' + it) }.join(', ') }
			</g:else>
		</span>
	</td>
</tr>