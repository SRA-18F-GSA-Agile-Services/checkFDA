<!doctype html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>Results - ${ query }</title>
	</head>
	<body>
		<div class="ui segment">
			<h1 class="ui header">Results - ${ query }</h1>
		</div>
		<div class="ui four doubling cards">
			<g:each in="${ 0..7 }">
				<g:render template="/layouts/example-card" />
			</g:each>
		</div>
	</body>
</html>
