<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link rel="shortcut icon" href="${req.contextPath}/favicon.ico" type="image/x-icon">
</head>
<body>
<form id="form" method="post" action="${req.contextPath}/do_logout">
    <input type="hidden" name="_csrf" value="${(_csrf)!}" id="_csrf">
    <br>
    <button type="submit" id="submit">登出</button>
</form>
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