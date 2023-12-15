<#include "base_view.ftlh">
<#macro MacroViewHook>
    <div class="container">
        <div class="row">
            <div class="col">
                <h1 class="title">用户登录</h1>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <input type="text" id="code" class="form-control">
                <button type="button" id="fetchToken" class="btn btn-primary">获取token</button>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <input type="text" id="aToken" class="form-control">
                <input type="text" id="rToken" class="form-control">
                <button type="button" id="refreshToken" class="btn btn-primary">刷新token</button>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <form id="logoutForm" method="GET" action="${req.contextPath}/do_logout">
                    <input name="_csrf" value="${_csrf!''}" class="form-control">
                    <button type="submit" class="btn btn-primary">登出</button>
                </form>
            </div>
        </div>
    </div>
</#macro>
<#macro MacroScriptHook>
    <script type="text/javascript">
        $(function () {
            // 获取token
            const params = new URLSearchParams(window.location.href.split("?")[1]);
            $("#code").val(params.get("code"));
            $('#fetchToken').on('click', function (message) {
                $.post(`${req.contextPath}` + '/oauth/token?grant_type=authorization_code&code=' + $("#code").val() + '&client_id=0d55e4c5-aeb2-4e00-9b67-80c8f31918d8&client_secret=123456&redirect_uri=http://127.0.0.1:1649/infrastructureAuthorityCentralResource/indirect/',
                    {},
                    function (data, status) {
                        if (status === 'success') {
                            $('#aToken').val(data.body['access_token']);
                            $('#rToken').val(data.body['refresh_token']);
                        } else {
                            alert("获取code失败！", "red");
                        }
                    }
                );
            });

            // 刷新token
            $('#refreshToken').on('click', function () {
                $.post(`${req.contextPath}` + '/oauth/token?refresh_token=' + $("#rToken").val() + '&grant_type=refresh_token&client_id=0d55e4c5-aeb2-4e00-9b67-80c8f31918d8&client_secret=123456',
                    {},
                    function (data, status) {
                        if (status === 'success') {
                            $('#aToken').val(data.body['access_token']);
                            $('#rToken').val(data.body['refresh_token']);
                        } else {
                            alert("获取code失败！", "red");
                        }
                    }
                );
            });

            // 登出
            $("#logoutForm").submit(function (e) {
                $.ajax({
                    headers: {
                        "Access-Token": $('#aToken').val()
                    },
                    url: $(this).attr("action"),
                    method: $(this).attr("method"),
                    data: $(this).serialize(),
                    success: function (data, status, xhr) {
                        debugger;
                        console.log(data, status, xhr)
                        location.reload(true)
                    },
                    error: function (error, status, xhr) {
                        debugger;
                        console.log(error, status, xhr)
                    }
                });
                e.preventDefault();
            });
        });
    </script>
</#macro>
<#macro MacroStyleHook>
    <style>
        body {
            background: url(${req.contextPath}/wallpaper/wallpaper.jpg) no-repeat;
            background-size: cover;
        }

        .title {
            padding-top: 4%;
        }
    </style>
</#macro>