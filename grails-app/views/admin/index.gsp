<!doctype html>
<html>
<head>
    <meta name="layout" content="semantic"/>
    <title><g:message code="admin.index.title"/></title>
</head>
<body>
<div class="header">
    <div class="ui bottom aligned centered grid">
        <div class="row">
            <div class="sixteen wide mobile sixteen wide tablet six wide computer column">
                <a href="${createLink(uri: '/')}" class="logo">checkFDA</a>
            </div>
            <div class="sixteen wide mobile sixteen wide tablet ten wide computer column">
                <div class="results search">
                    <g:render template="/layouts/search-box" />
                </div>
            </div>
        </div>
    </div>
</div>
<div class="main results">
    <h3><g:message code="admin.index.heading"/></h3>
    <ul>
        <li><g:link uri="/monitoring"><g:message code="admin.index.link.monitoring"/></g:link></li>
        <li><g:link controller="dataSet" action="index"><g:message code="admin.index.link.dataSet"/></g:link></li>
        <li><g:link controller="user" action="index"><g:message code="admin.index.link.user"/></g:link></li>
    </ul>
</div>
</body>
</html>
