<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Search FDA"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<asset:javascript src="jquery.min.js" />
		<asset:javascript src="semantic.min.js" />
		<asset:javascript src="jquery.blockUI.min.js" />
		<asset:stylesheet href="semantic.min.css" />
		<asset:stylesheet href="custom.css" />
		<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.6.3/d3-tip.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/0.5.0/sweet-alert.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/0.5.0/sweet-alert.css" type="text/css"> -->

		<g:layoutHead/>
	</head>

	<body>
		<div class="ui segment header">
			<div class="ui two column grid">
				<div class="column">
					<a href="${createLink(uri: '/')}">
						<img class="ui image" src="https://s3.amazonaws.com/stb-content/RAD.png" />
					</a>
				</div>
				<div class="column right-aligned">
					<img class="ui image small inline" src="https://s3.amazonaws.com/stb-content/sralogo-blue.png" />
				</div>
			</div>
		</div>

		<div class="content">
			<g:layoutBody/>
		</div>

		<div class="menu-wrapper footer">
			<div class="ui segment">
				<b><g:message code="default.layout.app" /> ${grailsApplication.metadata['app.version']}</b>
			</div>
		</div>

		<g:javascript library="application"/>
		<script>
			var activeMenu = function(id) {
				if ($('.menu-' + id).is('a')) {
					$('.menu-' + id).addClass('active');
				} else {
					$('.menu-' + id).addClass('selected');
				}
			};
			var flag = false;
			var tab = 'tab0';
			function goToURL(url) {
				window.location = url+'&tab='+tab;
			 }
			$(function() {
				$('.ui.dropdown').dropdown({
					on: 'hover'
				});
				$('#nav > ul > li > a').click(function() {
					$('#nav li').removeClass('active');
				});
			});

			function switchMenu(id) {
				$('#'+id).closest('li').addClass('active');
			}
		</script>
	</body>
</html>
