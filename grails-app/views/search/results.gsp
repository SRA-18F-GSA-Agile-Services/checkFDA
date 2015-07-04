<%@ page import="grails.converters.JSON" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title><g:message code="default.search.title" /> - ${ query }</title>
		<asset:javascript src="search.js" />
		<asset:javascript src="aggregated-cards.js" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.4.1/jquery.timeago.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.css" type="text/css">
		<asset:stylesheet href="map.css" />
		<style>
			.card-table.events tr > td:nth-child(1) {
				width: 12em;
			}
			.card-table.events tr > td:nth-child(2) {
				width: 8em;
			}
			.card-table.events tr > td:nth-child(3) {
				width: 4em;
			}
		</style>
	</head>
	<body>
		<div class="header">
			<div class="ui bottom aligned centered grid">
				<div class="row">
					<div class="sixteen wide mobile sixteen wide tablet six wide computer column">
						<a href="${createLink(uri: '/')}" class="logo">checkFDA</a>
					</div>
					<div class="sixteen wide mobile sixteen wide tablet ten wide computer column">
						<div class="results search">
							<g:render template="/layouts/search-box" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="main results">
			<g:if test="${flash.error}">
				<div class="ui negative message">
					<i class="close icon"></i>
					<div class="header">Error</div>
					<p>${flash.error}</p>
				</div>
			</g:if>
			<g:if test="${flash.message}">
				<div class="ui message">
					<i class="close icon"></i>
					${flash.message}
				</div>
			</g:if>
			<g:if test="${beanWithErrors}">
				<div class="ui message negative">
					<i class="close icon"></i>
					<g:renderErrors bean="${beanWithErrors}" as="list"/>
				</div>
			</g:if>

			<g:if test="${results}">
				<g:set var="recalls" value="${ results.recalls.sort { Map map1, Map map2 -> map1.classification <=> map2.classification ?: map2.recall_initiation_date <=> map1.recall_initiation_date } }" />
				<g:set var="currentRecalls" value="${ recalls.grep { it.status in ['Ongoing', 'Pending'] } }" />
				<g:if test="${ recalls.size() != 0 }">
					<h1 id="recalls-header" class="ui header">
						<g:message code="widget.results.recall.header" args="${ [query, currentRecalls.size()] }" />
					</h1>
					<div class="ui divider"></div>
					<div id="recalls-card"></div>
					<div class="card-table-wrapper">
						<table id="recalls" class="ui small compact complex selectable unstackable table card-table recalls">
							<tbody>
								<g:each in="${ recalls }" var="recall" status="id">
									<g:if test="${ recall.status in ['Ongoing', 'Pending'] }">
										<g:render template="/layouts/recall-alert-row" model="${ [recall: recall, id: id] }" />
									</g:if>
								</g:each>
							</tbody>
						</table>
					</div>
					<g:set var="states" value="${ results.recalls.grep { it.distribution_states} }" />
					<g:if test="${ states.size() != 0 }">
						<g:render template="/layouts/cards/recall-map" />
					</g:if>
					<g:render template="/layouts/cards/recall-timeline" />
				</g:if>

				<g:set var="drugEvents" value="${ results.events.grep { it.dataset == 'drug/event' } }" />
				<g:set var="deviceEvents" value="${ results.events.grep { it.dataset == 'device/event' } }" />
				<g:if test="${ results.events.size() != 0 }">
					<h1 class="ui header">
						<g:message code="widget.results.event.header" args="${ [query, results.events.size()] }"/>
					</h1>
					<div class="ui divider"></div>
					<g:if test="${ results.events.grep { it.dataset == 'drug/event' }.size() > 0 }">
						<h2 id="drugevents-header" class="ui header">
							<g:message code="widget.results.event.drug.header" args="${ [drugEvents.size()] }" />
						</h2>

						<div id="drugevents-card"></div>
						<div class="card-table-wrapper">
							<table id="drugevents" class="ui small compact selectable unstackable table card-table drugevents">
								<tbody>
									<g:each in="${ drugEvents }" var="event" status="id">
										<g:render template="/layouts/event-row" model="${ [event: event, id: id, isDrug: true, type: 'drug'] }" />
									</g:each>
								</tbody>
							</table>
						</div>
						<div class="ui two cards">
							<g:render template="/layouts/cards/event-gender" />
							<g:render template="/layouts/cards/event-ages" />
						</div>
					</g:if>

					<g:if test="${ results.events.grep { it.dataset == 'device/event' }.size() > 0 }">
						<h2 id="deviceevents-header" class="ui header">
							<g:message code="widget.results.event.device.header" args="${ [deviceEvents.size()] }" />
						</h2>
						<div id="deviceevents-card"></div>
						<div class="card-table-wrapper">
							<table id="deviceevents" class="ui small compact selectable unstackable table card-table deviceevents">
								<tbody>
									<g:each in="${ deviceEvents }" var="event" status="id">
										<g:render template="/layouts/event-row" model="${ [event: event, id: id, isDrug: false, type: 'device'] }" />
									</g:each>
								</tbody>
							</table>
						</div>
						<div class="ui divider"></div>
						<div class="ui one cards">
							<g:render template="/layouts/cards/event-outcomes" />
						</div>
					</g:if>
				</g:if>

				<g:set var="labels" value="${ results.labels.grep { it.openfda?.brand_name && it.openfda?.generic_name } }" />
				<g:if test="${ labels.size() != 0 }">
					<h1 id="labels-header" class="ui header">
						<g:message code="widget.results.label.header" args="${ [query, labels.size()] }" />
					</h1>
					<div class="ui divider"></div>
					<div id="labels-card"></div>
					<div class="card-table-wrapper">
						<table id="labels" class="ui small compact selectable unstackable table card-table labels">
							<tbody>
								<g:each in="${ labels }" var="label" status="id">
									<g:render template="/layouts/drug-label-row" model="${ [label: label, id: id] }" />
								</g:each>
							</tbody>
						</table>
					</div>
				</g:if>
			</g:if>
		</div>
		<script>

		<g:applyCodec encodeAs="none">		
			var recalls = ${ results ? recalls as JSON : "[]" };
			var labels = ${ results ? labels as JSON : "[]" };
			var events = ${ results ? results.events as JSON : "[]" };
			var homeState = ${ results ? results.state as JSON : "[]"  };
			var results = {recalls: recalls, labels: labels, events: events, drugevents: events.filter(function(e) { return e.dataset == 'drug/event'; }), deviceevents: events.filter(function(e) { return e.dataset == 'device/event'; }), state: homeState};	
		</g:applyCodec>
			$(function() {
				searchInit();

				addRowListeners('.card-table.recalls');
				addRowListeners('.card-table.labels');
				addRowListeners('.card-table.drugevents');
				addRowListeners('.card-table.deviceevents');
				$('.timeago').timeago();
				$('.message .close').on('click', function() {
					$(this).closest('.message').transition('fade');
				});

				$('[data-content]').popup({
					position: 'top center'
				});
			});
		</script>
	</body>
</html>
