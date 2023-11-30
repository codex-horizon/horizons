<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link rel="shortcut icon" href="${req.contextPath}/favicon.ico" type="image/x-icon">
</head>
<body>
<script type="text/javascript" src="${req.contextPath}/jquery-3.7.1.js"></script>
<form method="post" action="${req.contextPath}/do_logout">
    <input name="_csrf" id="logout_csrf">
    <button type="submit">登出</button>
</form>
<script type="text/javascript">
    $(function () {
        // 登出逻辑
        const csrf = decodeURIComponent(
            document.cookie.replace(
                new RegExp(
                    "(?:(?:^|.*;)\\s*" +
                    encodeURIComponent('XSRF-TOKEN').replace(/[-.+*]/g, "\\$&") +
                    "\\s*\\=\\s*([^;]*).*$)|^.*$",
                ),
                "$1",
            ),
        ) || '';
        $("#logout_csrf").val(csrf);
    });
</script>
<br/>

<input type="text" id="code">
<button type="button" id="fetchToken">获取token</button>
<script type="text/javascript">
    $(function () {
        // 获取token
        const params = new URLSearchParams(window.location.href.split("?")[1]);
        $("#code").val(params.get("code"));
        $('#fetchToken').on('click', function (message) {
            $.post(`${req.contextPath}` + '/oauth/token?grant_type=authorization_code&code=' + $("#code").val() + '&client_id=0d55e4c5-aeb2-4e00-9b67-80c8f31918d8&client_secret=123456&redirect_uri=http://127.0.0.1:1649/infrastructureAuthorityCentralResource/indirect',
                {},
                function (data, status) {
                    if (status === 'success') {
                        $('#token').val(data.body['access_token']);
                        $('#aToken').val(data.body['access_token']);
                        $('#rToken').val(data.body['refresh_token']);
                    } else {
                        alert("获取code失败！", "red");
                    }
                }
            );
        });
    });
</script>
<br/>

<input type="text" id="aToken">
<input type="text" id="rToken">
<button type="button" id="refreshToken">刷新token</button>
<script type="text/javascript">
    $(function () {
        // 刷新token
        $('#refreshToken').on('click', function () {
            $.post(`${req.contextPath}` + '/oauth/token?refresh_token=' + $("#rToken").val() + '&grant_type=refresh_token&client_id=0d55e4c5-aeb2-4e00-9b67-80c8f31918d8&client_secret=123456',
                {},
                function (data, status) {
                    if (status === 'success') {
                        $('#token').val(data.body['access_token']);
                        $('#aToken').val(data.body['access_token']);
                        $('#rToken').val(data.body['refresh_token']);
                    } else {
                        alert("获取code失败！", "red");
                    }
                }
            );
        });
    });
</script>
<br/>
<form method="post" action="${req.contextPath}/do_revoke_token">
    <input name="_csrf" id="revoke_csrf">
    <input type="text" name="Access-Token" id="token">
    <button type="submit">吊销</button>
</form>
<script type="text/javascript">
    $(function () {
        // 吊销token
        const csrf = decodeURIComponent(
            document.cookie.replace(
                new RegExp(
                    "(?:(?:^|.*;)\\s*" +
                    encodeURIComponent('XSRF-TOKEN').replace(/[-.+*]/g, "\\$&") +
                    "\\s*\\=\\s*([^;]*).*$)|^.*$",
                ),
                "$1",
            ),
        ) || '';
        $("#revoke_csrf").val(csrf);
    });
</script>
</body>
</html>