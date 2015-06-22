<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title><g:message code="default.search.title" /> - ${ query }</title>
	</head>
	<body>
		<div class="ui large fluid action labeled input">
			<div class="ui label">
				<g:message code="default.search.query" />
			</div>
			<g:field type="text" name="query" value="${ query }" />
			<div id="search" class="ui large primary right labeled icon button">
				<g:message code="default.search.search" />
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
