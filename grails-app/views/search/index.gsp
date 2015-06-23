<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<asset:javascript src="search.js" />
		<asset:stylesheet src="landing.css" />
	</head>
	<body>
		<div class="body">
			<div class="search">
				<g:render template="/layouts/search-box" />
			</div>
		</div>
		<script>
			$(function() {
				searchInit();
			});
		</script>
	</body>
</html>
