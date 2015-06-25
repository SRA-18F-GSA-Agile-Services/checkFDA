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
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.css" type="text/css">
		<asset:stylesheet src="results.css" />
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
				<g:set var="recalls" value="${ results.recalls.grep { it.status in ['Ongoing', 'Pending'] }.sort { it.classification } }" />
				<g:if test="${ recalls.size() != 0 }">
					<h1 class="ui header">
						<g:message code="widget.results.recall.header" args="${ [recalls.size()] }" /> <i>${ query }</i>
					</h1>
					<div class="ui divider"></div>
					<div class="recall-alerts">
						<g:each in="${ recalls }" var="recall" status="id">
							<g:render template="/layouts/cards/recall-alert" model="${ [recall: recall, id: id] }" />
						</g:each>
					</div>
					<table class="ui small compact table recall-table">
						<tbody>
							<g:each in="${ recalls }" var="recall" status="id">
								<g:render template="/layouts/recall-alert-row" model="${ [recall: recall, id: id] }" />
							</g:each>
						</tbody>
					</table>
					<div class="ui two doubling cards">
						<g:render template="/layouts/cards/recall-timeline" />
					</div>
				</g:if>

				<h1 class="ui header">
					<g:message code="widget.results.event.header" args="${[results.events.size()]}"/>
				</h1>
				<div class="ui divider"></div>
				<div class="ui two doubling cards">
					<g:render template="/layouts/cards/event_outcomes" />
					<g:render template="/layouts/cards/event-gender" />
					<g:render template="/layouts/cards/event_ages" />
				</div>
			</g:if>
		</div>
		<script>

		<g:applyCodec encodeAs="none">
			var results = ${results ? results as JSON : "undefined"};
		</g:applyCodec>

			$(function() {
				searchInit();

				addRowListeners('.recall-table');
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
