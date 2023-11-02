<#if RequestParameters["error"]??>
    <h1>用户登出失败！</h1>
<#else>
    <h1>用户登出：</h1>
    <form id="form" method="post" action="${req.contextPath}/logout">
        <input type="hidden" name="_csrf" value="${(_csrf)!}" id="_csrf">
        <br>
        <button type="submit" id="submit">登出</button>
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
