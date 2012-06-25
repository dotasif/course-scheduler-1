<#import "./layout.ftl" as macro>
<@macro.layout>
<form id="login-form" action="j_security_check" method="post">
        <fieldset>
                <legend>Login</legend>
                <#if hasLoginFailed??>
                <div class="alert alert-error">
                        Login failed.
                </div>
                </#if>
                <div class="control-group">
                        <div class="controls">
                                <label class="control-label" for="j_username">Name:</label>
                                <input type="text" name="j_username" id="name" value=""/>
                                <label class="control-label" for="j_password">Password:</label>
                                <input type="password" name="j_password" id="password" value=""/>
                        </div>
                        <input class="btn btn-primary" type="submit" value="Sign In"/>
                </div>
        </fieldset>
</form>
</@macro.layout>
