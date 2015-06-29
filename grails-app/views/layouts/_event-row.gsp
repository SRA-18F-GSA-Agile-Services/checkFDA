<tr id="event-${ id }">
	<td>
		<span data-content="${ message(code: 'widget.event.row.report') }">
			${ isDrug ? event.safetyreport : event.report_number }
		</span>
	</td>
	<td>
		<span data-content="${ message(code: 'widget.event.row.calendar.' + type) }">
			<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', isDrug ? event.receivedate : event.date_received).format('M/d/yyyy') }
		</span>
	</td>
	<td>
		<span data-content="${ message(code: 'widget.event.row.patient.' + type) }">
			<g:if test="${ isDrug }">
				<i class="icon ${ message(code: 'widget.event.row.patientsex.icon' + event.patient.patientsex) }"></i>
			</g:if>
			 ${ isDrug ? event.patient.patientonsetage : event.patient.size() }
		 </span>
	</td>
	<td class="overflow">
		<g:if test="${ !isDrug }">
			<span data-content="${ message(code: 'widget.event.row.outcomes') }">
				${ event.patient*.sequence_number_outcome*.collect { it.split(', ').last() }*.join(', ').join('<br />') }
			</span>
		</g:if>
	</td>
</tr>