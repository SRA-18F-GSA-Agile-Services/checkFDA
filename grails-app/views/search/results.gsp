<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>Results - ${ query }</title>
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
		<div class="ui divider"></div>
		<div class="ui four doubling cards">
			<g:each in="${ 0..7 }">
				<g:render template="/layouts/example-card" />
			</g:each>
		</div>
		<script>
			$(function() {
				$('#search').click(search);
				$(window).keyup(function(event) {
					if (event.keyCode == 13) {
						search();
					}
				});
			});

			function search() {
				window.location.href = '/results?q=' + $('#query').val();
			}
		</script>
	</body>
</html>
