<div class="ui fluid card deviceevent">
	<div class="content">
		<div class="extra content">
			<g:if test="${ event.date_received }">
				<span data-content="${ message(code: 'widget.device.event.receivedate') }">
					<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', event.date_received).format('M/d/yyyy') }
				</span>
			</g:if>

			<span data-content="${ message(code: 'widget.device.event.report') }">
				<i class="icon file text outline"></i> ${ event.report_number }
			</span>
		</div>

		<div class="ui tiny header">
			<g:message code="widget.device.event.outcomes" />
		</div>
		<p>${ event.patient*.sequence_number_outcome*.collect { it.split(', ').last() }*.join(', ').join('<br />') }</p>

		<g:each in="${ event.device }" var="device">
			<div class="ui divider"></div>

			<div class="ui tiny header">
				<g:message code="widget.device.event.brand" />
			</div>
			<div class="ui medium header nomargin">${ device.brand_name }</div>

			<div class="ui tiny header">
				<g:message code="widget.device.event.generic" />
			</div>
			<div class="ui small header nomargin">${ device.generic_name }</div>

			<div class="ui four column grid">
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
