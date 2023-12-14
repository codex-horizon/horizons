<#include "base_view.ftlh">
<#macro MacroViewHook>
    <div class="container">
        <div class="row">
            <div class="col">
                <h1 class="title">用户登录</h1>
                <div class="stack">
                    <div class="paper" id="paper1"></div>
                    <div class="paper" id="paper2"></div>
                    <div class="paper" id="paper3">
                        <form method="post" action="${req.contextPath}/do_login">
                            <div class="form-group">
                                <label>用户名</label>
                                <input type="hidden" name="_csrf" class="form-control" value="${_csrf!''}">
                                <input type="text" name="username" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>密&nbsp;码</label>
                                <input type="password" name="password" class="form-control">
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary btn-block">登&nbsp;录</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>
<#macro MacroScriptHook>
</#macro>
<#macro MacroStyleHook>
    <style>
        .title{
            padding-top: 4%;
        }
        .stack {
            display: flex;
        }

        .paper {
            position: relative;
            flex-shrink: 0;
            width: 100%;
            background: white;
            border-radius: 3px;
            box-shadow: 1px 2px 4px 1px rgba(0, 0, 0, .15);
            padding: 100px;
        }

        #paper1 {
            top: 45px;
            left: 50px;
        }

        #paper2 {
            top: 20px;
            left: -100%;
        }

        #paper3 {
            top: 25px;
            transform: rotate(-4deg);
            left: -200%;
        }
    </style>
</#macro>