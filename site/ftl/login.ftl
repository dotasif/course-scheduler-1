<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
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
        </body>
</html>
