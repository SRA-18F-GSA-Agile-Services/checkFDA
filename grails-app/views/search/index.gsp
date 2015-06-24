<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<asset:javascript src="search.js" />
	</head>
	<body>
		<div class="landing">
			<div class="search">
				<div class="container">
					<g:render template="/layouts/search-box" />
					<div class="guidance">
						<g:message code="default.search.guidance" />
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<!-- <g:message code="default.layout.app" /> ${grailsApplication.metadata['app.version']} -->
		</div>
		<script>
			$(function() {
				searchInit();
			});
		</script>
	</body>
</html>
