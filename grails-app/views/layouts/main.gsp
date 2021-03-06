<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<asset:stylesheet href="main.css" />
		<asset:stylesheet href="mobile.css" />
		<asset:stylesheet href="chosen.css" />
		<asset:stylesheet href="custom.css" />
		<g:layoutHead/>
		<style>
			#logo > .ui.image {
				max-height: 80px;
			}
		</style>
	</head>
	<body>
		<div id="logo" role="banner">
			<img class="ui image" src="https://s3.amazonaws.com/stb-content/RAD.png" />
		</div>
		<div id="loginHeader">
			<sec:ifLoggedIn>
				<sec:username/>
				[${link(action:"local",controller:"saml"){"Logout"}}]
			</sec:ifLoggedIn>
			<sec:ifNotLoggedIn>
				[${link(action:"auth",controller:"login"){"Login"}}]
			</sec:ifNotLoggedIn>
		</div>
		<g:layoutBody/>
		<div class="footer right-aligned" role="contentinfo">
			<b><g:message code="default.layout.app" /> ${ grailsApplication.metadata['app.version'] }</b>
		</div>
		<g:javascript library="application"/>
	</body>
</html>
