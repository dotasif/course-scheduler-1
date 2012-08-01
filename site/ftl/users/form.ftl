<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>${requestHeadline}</h1>
</div>
<div class="content">
        <form class="well form-horizontal" action="" method="post" autocomplete="off" autocomplete="off">
                <fieldset>
                        <div class="control-group">
                                <label class="control-label" for="login">Login:</label>
                                <div class="controls">
                                        <input type="text" name="login" value="<#if user??>${user.login}</#if>"/>
                                </div>
                        </div>
                        <#if isNewEntity>
                        <div class="control-group">
                                <label class="control-label" for="password">Password:</label>
                                <div class="controls">
                                        <input type="password" name="password" id="password" value=""/>
                                </div>
                        </div>
                        <#else> 
                        <div class="control-group">
                                <label class="control-label" for="oldPassword">Old Password:</label>
                                <div class="controls">
                                        <input type="password" name="oldPassword" id="password" value=""/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="newPassword">New Password:</label>
                                <div class="controls">
                                        <input type="password" name="newPassword" id="newPassword" value=""/>
                                </div>
                        </div>
                        </#if>
                        <div class="control-group">
                        <label class="control-label" for="name">Name:</label>
                        <div class="controls">
                                        <input type="text" name="name" value="<#if user??>${user.name}</#if>"/>
                                </div>
                        </div>
                        <div class="control-group">
                        <label class="control-label" for="email">Email:</label>
                        <div class="controls">
                                        <input type="text" name="email" value="<#if user??>${user.email}</#if>"/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="student">Student:</label>
                                <div class="controls">
                                        <#if user?? && user.student><#assign checked = "checked"/><#else><#assign checked = ""/></#if> 
                                        <input type="checkbox" name="student" value="isStudent" ${checked}>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="lecturer">Lecturer:</label>
                                <div class="controls">
                                        <#if user?? && user.lecturer><#assign checked = "checked"/><#else><#assign checked = ""/></#if> 
                                        <input type="checkbox" name="lecturer" value="isLecturer" ${checked}>
                                </div>
                        </div>
                </fieldset>
                <div class="form-actions">
                        <input class="btn btn-primary" type="submit" value="Submit"/>
                </div>
        </form>
</div>
</@macro.layout>
