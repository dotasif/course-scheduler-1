<#import "layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Sign Up</h1>
</div>
<div class="content">
        <form class="well form-horizontal" action="" method="post" autocomplete="off">
                <fieldset>
                        <div class="control-group">
                                <label class="control-label" for="name">Full Name:</label>
                                <div class="controls">
                                        <input type="text" name="name">
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="email">Email:</label>
                                <div class="controls">
                                        <input type="text" name="email">
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="login">Login:</label>
                                <div class="controls">
                                        <input type="text" name="login" value="<#if user??>${user.login}</#if>"/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="password">Password:</label>
                                <div class="controls">
                                        <input type="password" name="password" id="password" value=""/>
                                </div>
                        </div>
                </fieldset>
                <div class="form-actions">
                        <input class="btn btn-primary" type="submit" value="Create My Account"/>
                </div>
        </form>
</div>
</@macro.layout>
