<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>授权</title>
</head>
<body>
<div>
    <h1>授权批准：</h1>
    <p>您是否授权“${clientId}”访问您受保护的资源？</p>
    <form method="post" action="${req.contextPath}/oauth/authorize">
        <input name="_csrf" type="hidden" value="${_csrf}">
        <input name="user_oauth_approval" value="true" type="hidden"/>
        <#list scopes as scope>
            <br>
            <input type="radio" name="scope.${scope}" value="true">批准
            <br>
            <input type="radio" name="scope.${scope}" value="false" checked>拒绝
            <br>
        </#list>
        <input name="authorize" value="Authorize：同意/授权" type="submit">
    </form>
</div>
</body>
</html>