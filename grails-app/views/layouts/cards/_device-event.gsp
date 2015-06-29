<div class="ui fluid card deviceevent">
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
			<div class="sixteen wide column">
				<div class="ui tiny header">
					<g:message code="widget.device.event.outcomes" />
				</div>
				${ event.patient*.sequence_number_outcome*.collect { it.split(', ').last() }*.join(', ').join('<br />') }
			</div>
		</div>

		<g:each in="${ event.device }" var="device">
			<div class="ui divider"></div>
	
			<div class="ui tiny header">
				<g:message code="widget.device.event.brand" />
			</div>
			<div class="ui header">${ device.brand_name }</div>
			<div class="ui tiny header">
				<g:message code="widget.device.event.generic" />
			</div>
			<div class="ui small header">${ device.generic_name }</div>
			<div class="ui three column grid">
				<div class="column">
					<div class="ui tiny header">
						<g:message code="widget.device.event.manufacturer" />
					</div>
					<p>${ device.manufacturer_d_name }</p>
				</div>
				<div class="column">
					<div class="ui tiny header">
						<g:message code="widget.device.event.implanted" />
					</div>
					<p>${ device.implant_flag ? 'Y' : 'N' }</p>
				</div>
				<div class="column">
					<div class="ui tiny header">
						<g:message code="widget.device.event.age" />
					</div>
					<p>${ device.device_age_text }</p>
				</div>
			</div>
			<div class="ui tiny header">
				<g:message code="widget.device.event.operator" />
			</div>
			<p>${ device.device_operator }</p>
			<g:if test="${ device.mdr_text?.text }">
				<div class="ui tiny header">
					<g:message code="widget.device.event.description" />
				</div>
				<p>${ device.mdr_text?.text }</p>
			</g:if>
		</g:each>
	</div>
</div>
