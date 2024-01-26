<#include "base_view.ftlh">
<#macro MacroViewHook>
    <div class="container">
        <div class="row">
            <div class="col">
                <h1 class="title">用户登录</h1>
            </div>
            <div class="col">
                <form class="row center" method="post" action="${req.contextPath}/do_login">
                    <div class="col-auto">
                        <label class="visually-hidden">用户名</label>
                        <input type="hidden" name="_csrf" class="form-control" value="${_csrf!''}">
                        <input type="text" class="form-control" name="username">
                    </div>
                    <div class="col-auto">
                        <label class="visually-hidden">密码</label>
                        <input type="password" name="password" class="form-control">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#macro>
<#macro MacroScriptHook>
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

        .center {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }

    </style>
</#macro>