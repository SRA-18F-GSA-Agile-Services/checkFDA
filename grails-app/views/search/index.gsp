<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<asset:javascript src="search.js" />
	</head>
	<body>
		<div class="header">
			<a href="${createLink(uri: '/')}" class="logo">checkFDA</a>
		</div>
		<div class="ui middle aligned centered grid main landing">
			<div class="row search">
				<div class="sixteen wide mobile twelve wide tablet eight wide computer column">
					<g:render template="/layouts/search-box" />
					<div class="guidance">
						<g:message code="default.search.guidance" />
					</div>
				</div>
			</div>
		</div>
		<script>
			$(function() {
				searchInit();
				setTimeout(geolocation, 1000)
			});
		</script>
	</body>
</html>
