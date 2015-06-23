<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<asset:javascript src="search.js" />
	</head>
	<body>
		<div class="ui large fluid action labeled input">
			<div class="ui label">
				Query
			</div>
			<g:field type="text" name="query" value="${ query }" />
			<div id="search" class="ui large primary right labeled icon button">
				Search
				<i class="right arrow icon"></i>
			</div>
		</div>
		<script>
			$(function() {
				searchInit();
			});
		</script>
	</body>
</html>
