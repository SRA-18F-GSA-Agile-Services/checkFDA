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
			.center-aligned {
				text-align: center;
			}
			a.basic {
				color: -webkit-link;
				text-decoration: underline;
			}
			@media (max-width: 1800px) {
				.ui.very.close.right.rail {
					width: 100%;
					margin: 0 auto;
					position: static;
					max-width: 400px;
				}
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
		<div class="main results" style="position: relative;">
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
						<g:message code="widget.results.recall.header" args="${ [currentRecalls.size()] }" /> <i>${ query }</i>
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
					<g:render template="/layouts/cards/recall-map" />
					<g:render template="/layouts/cards/recall-timeline" />
				</g:if>
				<g:set var="labels" value="${ results.labels.grep { it.openfda?.brand_name && it.openfda?.generic_name } }" />
				<g:if test="${ labels.size() != 0 }">
					<h1 id="labels-header" class="ui header">
						<g:message code="widget.results.label.header" args="${ [labels.size()] }" /> <i>${ query }</i>
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
				<g:if test="${ results.events.size() != 0 }">
					<h1 class="ui header">
						<g:message code="widget.results.event.header" args="${[results.events.size()]}"/>
					</h1>
					<div class="ui divider"></div>
					<g:if test="${ results.events.grep { it.dataset == 'device/event' }.size() > 0 }">
						<div class="ui one cards">
							<g:render template="/layouts/cards/event-outcomes" />
						</div>
					</g:if>
					<g:if test="${ results.events.grep { it.dataset == 'drug/event' }.size() > 0 }">
						<div class="ui two cards">
							<g:render template="/layouts/cards/event-gender" />
							<g:render template="/layouts/cards/event-ages" />
						</div>
					</g:if>
				</g:if>
			</g:if>

			<div class="ui very close right rail">
				<div class="ui three column centered grid">
					<div class="center aligned column">
						<a href="http://twitter.com/home?status=${ java.net.URLEncoder.encode('Check out what I found out about ' + query + ' on #checkFDA: ' + grailsApplication.config.grails.serverURL + '/results?q=' + query) }" class="ui large circular twitter icon button" target="_blank">
							<i class="twitter icon"></i>
						</a>
					</div>
					<div class="center aligned column">
						<a href="https://www.pinterest.com/pin/create/button/?url=${ java.net.URLEncoder.encode(grailsApplication.config.grails.serverURL + '/results?q=' + query) }&description=${ java.net.URLEncoder.encode('Check out what I found out about ' + query + ' on checkFDA') }" class="ui large circular red icon button" target="_blank">
							<i class="pinterest icon"></i>
						</a>
					</div>
					<div class="center aligned column">
						<a href="mailto:?subject=${ java.net.URLEncoder.encode(query + ' - ' + ' checkFDA') }&body${ java.net.URLEncoder.encode('Check out what I found out about ' + query + ' on #checkFDA: ' + grailsApplication.config.grails.serverURL + '/results?q=' + query) }" class="ui large circular facebook icon button">
							<i class="mail icon"></i>
						</a>
					</div>
				</div>
				<div class="ui segment">
					<p>
						Report your own adverse events with:
					</p>
					<ul>
						<li>Medicines, medical devices, and combination products</li>
						<li>Foods and beverages (including reports of serious allergic reactions)</li>
						<li>Cosmetics</li>
						<li>Special nutritional products</li>
					</ul>
					<div class="center-aligned">
						<a href="https://www.accessdata.fda.gov/scripts/medwatch/index.cfm?action=consumer.reporting1" class="ui big button" target="_blank">Begin a Report</a>
					</div>
					<div class="center-aligned">
						<a href="https://www.accessdata.fda.gov/scripts/medwatch/index.cfm?action=professional.reporting1" class="basic" target="_blank">Begin a report as health professional</a>
					</div>
				</div>
			</div>
		</div>
		<script>

		<g:applyCodec encodeAs="none">
			var recalls = ${ results ? recalls as JSON : "[]" };
			var labels = ${ results ? labels as JSON : "[]" };
			var events = ${ results ? results.events as JSON : "[]" };
			var homeState = ${ results ? results.state as JSON : "[]"  };
			var results = {recalls: recalls, labels: labels, events: events, state: homeState};
		</g:applyCodec>
			$(function() {
				searchInit();

				addRowListeners('.card-table.recalls');
				addRowListeners('.card-table.labels');
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
