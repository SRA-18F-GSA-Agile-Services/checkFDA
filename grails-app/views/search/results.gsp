<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title><g:message code="default.search.title" /> - ${ query }</title>
		<asset:javascript src="search.js" />
	</head>
	<body>
		<g:render template="/layouts/search-box" />
		<div class="ui divider"></div>
		<div class="ui four doubling cards">
			<g:each in="${ 0..7 }">
				<g:render template="/layouts/example-card" />
			</g:each>
		</div>
		<script>
			$(function() {
				searchInit();
			});
		</script>
	</body>
</html>
