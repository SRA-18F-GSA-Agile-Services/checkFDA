<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="semantic">
		<g:if env="development"><link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css"></g:if>
	</head>
	<body>
		<g:if env="development">
			<div class="ui segment">
				<g:renderException exception="${exception}" />
			</div>
		</g:if>
		<g:else>
			<div class="ui segment center aligned">
				<h1 class="ui header icon">
					<i class="bug icon"></i>
					<div class="content">
						<g:message code="default.error.header" />
					</div>
				</h1>
				<p>
					<g:message code="default.error.message" />
					<a href="/">
						<g:message code="default.error.link" />
					</a>
				</p>
			</div>
		</g:else>
	</body>
</html>
