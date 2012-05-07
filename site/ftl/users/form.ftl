<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
                <h1>${requestHeadline}</h1>
                <div class="content">
                        <form id="user-form" action="" method="post">
                                <ol>
                                        <li>
                                        <label for="name">Name:</label>
                                        <input type="text" name="name" id="name" value="<#if user??>${user.name}</#if>"/>
                                        </li>
                                        <#if isNewEntity>
                                        <li>
                                        <label for="password">Password:</label>
                                        <input type="password" name="password" id="password" value=""/>
                                        </li>
                                        <#else> 
                                        <li>
                                        <label for="oldPassword">Old Password:</label>
                                        <input type="password" name="oldPassword" id="password" value=""/>
                                        </li>
                                        <li>
                                        <label for="newPassword">New Password:</label>
                                        <input type="password" name="newPassword" id="newPassword" value=""/>
                                        </li>
                                        </#if>
                                        <li>
                                        <label for="email">Email:</label>
                                        <input type="text" name="email" id="email" value="<#if user??>${user.email}</#if>"/>
                                        </li>
                                        <li>
                                        <#if user??><#assign isChecked = user.student/><#else><#assign isChecked = false/></#if>
                                        <input type="checkbox" name="isStudent" id="isStudent" value="isStudent" ${isChecked?string("checked","")}>Is Student?</input>
                                        </li>
                                        <li>
                                        <#if user??><#assign isChecked = user.lecturer/><#else><#assign isChecked = false/></#if>
                                        <input type="checkbox" name="isLecturer" id="isLecturer" value="isLecturer" ${isChecked?string("checked","")}>Is Lecturer?</input>
                                        </li>
                                        <li>
                                        <input type="submit" name="submit-reason" value="Create"/>
                                        </li>
                                </ol>
                                </li>
                        </ol>
                </form>
        </div>
</body>
</html>
