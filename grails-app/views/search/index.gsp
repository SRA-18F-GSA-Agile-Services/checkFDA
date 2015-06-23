<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<asset:javascript src="search.js" />
	</head>
	<body>
		<g:render template="/layouts/search-box" />
		<script>
			$(function() {
				searchInit();
			});
		</script>
	</body>
</html>
