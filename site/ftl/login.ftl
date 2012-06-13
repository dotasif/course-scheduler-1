<#import "./layout.ftl" as macro>
<@macro.layout>
<form id="login-form" action="" method="post">
        <fieldset>
                <legend>Login</legend>
                <#if hasLoginFailed??>
                <div class="alert alert-error">
                        Login failed.
                </div>
                </#if>
                <div class="control-group">
                        <div class="controls">
                                <label class="control-label" for="name">Name:</label>
                                <input type="text" name="name" id="name" value="${name!}"/>
                                <label class="control-label" for="password">Password:</label>
                                <input type="password" name="password" id="password" value=""/>
                        </div>
                        <input class="btn btn-primary" type="submit" value="Sign In"/>
                </div>
        </fieldset>
</form>
</@macro.layout>
