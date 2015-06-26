<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>404</title>
	</head>
	<body>
		<div class="header">
			<a href="${createLink(uri: '/')}" class="logo">checkFDA</a>
		</div>
		<div class="ui middle aligned centered grid main">

			<div class="column error">
				<h1 class="ui header icon">
					<i class="bug icon"></i>
					<div class="content">
						<g:message code="default.missing.header" />
					</div>
				</h1>
				<p>
					<g:message code="default.missing.message" />
					<a href="/">
						<g:message code="default.missing.link" />
					</a>
				</p>
			</div>

		</div>
	</body>
</html>