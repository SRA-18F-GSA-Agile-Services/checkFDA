<div class="ui large action left icon input">
	<i class="search icon"></i>
	<g:set var="placeholderText" value="${ message(code:'default.search.placeholder')}" />
	<g:field type="text" name="query" placeholder="${ placeholderText }" value="${ query }" />
	<div class="ui large button"><g:message code="default.search.search" /></div>
</div>