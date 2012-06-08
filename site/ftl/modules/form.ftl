<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
                <link rel="stylesheet" type="text/css" href="${css}"/>
        </head>
        <body>
                <h1>${requestHeadline}</h1>
                <div class="content">
                        <form action="" method="post">
                                <ol>
                                        <li>
                                        <label for="name">Name:</label>
                                        <input type="text" name="name" value="<#if module.name??>${module.name}</#if>"/>
                                        </li>
                                        <li>
                                        <label for="credits">Credits:</label>
                                        <input type="text" name="credits" value="<#if module.credits != -1>${module.credits}</#if>"/>
                                        </li>
                                        <li>
                                        <label for="assessment">Assessment:</label>
                                        <input type="text" name="assessment" value="<#if module.assessment??>${module.assessment}</#if>"/>
                                        </li>
                                        <li>Courses:
                                        <ul>
                                                <#list module.courses as course>
                                                <li>
                                                <ol>
                                                        <li>
                                                        <label for="type">Type:</label>
                                                        <input type="text" name="type" value="<#if course.type??>${course.type}</#if>"/>
                                                        </li>
                                                        <li>
                                                        <label for="duration">Duration:</label>
                                                        <input type="text" name="duration" value="<#if course.duration != -1>${course.duration}</#if>"/>
                                                        </li>
                                                        <li>
                                                        <label for="count">Count:</label>
                                                        <input type="text" name="count" value="<#if course.count != -1>${course.count}</#if>"/>
                                                        </li>
                                                        <#list equipment.items as item>
                                                        <li>
                                                        <label for="item">${item}:</label>
                                                        <input type="text" name="item" value="<#if course.equipment[item]??>${course.equipment[item]}<#else>0</#if>">
                                                        </li>
                                                        </#list>

                                                </ol>
                                                </li>
                                                </#list>
                                        </ul>
                                        <li>
                                        <input type="submit" name="submit-reason" value="New Course"/>
                                        </li>
                                        <li>
                                        <input type="submit" name="submit-reason" value="Create"/>
                                        </li>
                                </ol>
                        </form>
                </div>
        </body>
</html>
