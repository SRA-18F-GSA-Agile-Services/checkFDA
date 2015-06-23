<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
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
				$('#query').focus();
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
