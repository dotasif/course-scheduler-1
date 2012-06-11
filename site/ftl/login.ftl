<#import "./layout.ftl" as macro>
<@macro.layout>
<form id="login-form" action="" method="post">
        <#if hasLoginFailed??>
        <div class="alert alert-error">
                Login failed.
        </div>
        </#if>
        <fieldset>
                <legend>Login</legend>
                <ol>
                        <li>
                        <label for="name">Name:</label>
                        <input type="text" name="name" id="name" value=""/>
                        </li>
                        <li>
                        <label for="password">Password:</label>
                        <input type="password" name="password" id="password" value=""/>
                        </li>
                        <li>
                        <input type="submit" value="Sign In"/>
                        </li>
                </ol>
        </fieldset>
</form>
</@macro.layout>
