<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title><g:message code="default.search.title" /> - ${ query }</title>
		<asset:javascript src="search.js" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.css" type="text/css">
	</head>
	<body>
		<div class="results">
			<div class="search">
				<g:render template="/layouts/search-box" />
			</div>
		</div>
		<div class="ui divider"></div>
		<g:each in="${ ['Ongoing', 'Pending'] }" var="status">
			<g:set var="recalls" value="${ results.recalls.grep { it.status == status } }" />
			<g:if test="${ recalls.size() != 0 }">
				<h1 class="ui header">
					<g:message code="widget.results.recall.${ status.toLowerCase() }.header" args="${ recalls.size() } " />
				</h1>
				<div class="ui two doubling cards">
					<g:each in="${ recalls }" var="recall">
						<g:render template="/layouts/cards/recall-alert" model="${ [recall: recall] }" />
					</g:each>
				</div>
			</g:if>
		</g:each>
		<h1 class="ui header">
			${ results.events.size() } Event Results
		</h1>
		<div class="ui two doubling cards">
			<g:render template="/layouts/cards/event_outcomes" />
			<g:render template="/layouts/cards/event-gender" />
			<g:render template="/layouts/cards/event_ages" />
		</div>
		<script>
			var results = ${ results as grails.converters.JSON };
			$(function() {
				searchInit();
				$('[data-content]').popup({
					position: 'top center'
				});
			});
		</script>
	</body>
</html>
