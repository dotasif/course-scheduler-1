<#import "./layout.ftl" as macro>
<@macro.layout>
<form class="well" action="j_security_check" method="post">
        <fieldset>
                <legend>Login</legend>
                <#if hasLoginFailed??>
                <div class="alert alert-error">
                        <h4>Login failed</h4>
                        Check your name and password.
                </div>
                </#if>
                <div class="control-group">
                        <label class="control-label" for="j_username">Name:</label>
                        <div class="controls">
                                <input type="text" name="j_username" id="name" value=""/>
                        </div>
                </div>
                <div class="control-group">
                        <label class="control-label" for="j_password">Password:</label>
                        <div class="controls">
                                <input type="password" name="j_password" id="password" value=""/>
                        </div>
                </div>
                <div class="form-actions">
                        <input class="btn btn-primary" type="submit" value="Sign In"/>
                </div>
        </fieldset>
</form>
</@macro.layout>
