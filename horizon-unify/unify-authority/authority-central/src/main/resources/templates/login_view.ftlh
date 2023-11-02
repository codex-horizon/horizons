<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页</title>
    <link rel="shortcut icon" href="${req.contextPath}/favicon.ico" type="image/x-icon">
</head>
<body>
<#if RequestParameters["error"]??>
    <h1>用户登录失败！</h1>
    <p>请检查用户名、密码是否正确！</p>
<#else>
    <h1>用户登录：</h1>
    <form id="form" method="post" action="${req.contextPath}/login">
        <input type="hidden" name="_csrf" value="${(_csrf)!}" id="_csrf">
        <br>
        <input type="text" name="username" placeholder="用户名" value="super"/>
        <br>
        <input type="password" name="password" placeholder="密码" value="123456"/>
        <br>
        <button type="submit" id="submit">登录</button>
    </form>
</#if>
<script type="text/javascript" src="${req.contextPath}/jquery-3.7.1/jquery-3.7.1.js"></script>
<script type="text/javascript">
    $(function () {
        $("#_csrf").val(decodeURIComponent(
            document.cookie.replace(
                new RegExp(
                    "(?:(?:^|.*;)\\s*" +
                    encodeURIComponent('XSRF-TOKEN').replace(/[-.+*]/g, "\\$&") +
                    "\\s*\\=\\s*([^;]*).*$)|^.*$",
                ),
                "$1",
            ),
        ) || '');
    });
</script>
</body>
</html>