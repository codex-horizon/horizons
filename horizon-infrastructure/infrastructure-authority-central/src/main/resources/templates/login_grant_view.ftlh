<#include "base_view.ftlh">
<#macro MacroViewHook>
    <div>
        <h1>授权批准：</h1>
        <p>您是否授权 ${clientId!''} 访问您受保护的资源？</p>
        <form method="post" action="${req.contextPath}/oauth/authorize">
            <label>
                <input name="_csrf" type="hidden" value="${_csrf!''}">
            </label>
            <label>
                <input name="user_oauth_approval" value="true" type="hidden"/>
            </label>
            <#list scopes as scope>
                <br>
                <label>
                    <input type="radio" name="scope.${scope}" value="true">批准
                </label>
                <br>
                <label>
                    <input type="radio" name="scope.${scope}" value="false" checked>拒绝
                </label>
                <br>
            </#list>
            <label>
                <input name="authorize" value="Authorize：同意/授权" type="submit">
            </label>
        </form>
    </div>
</#macro>
<#macro MacroScriptHook>
</#macro>
<#macro MacroStyleHook>
    <style>
    </style>
</#macro>