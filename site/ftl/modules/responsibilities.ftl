<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>Course Responsibilities</h1>
<div class="navigation">
        <ol>
                <li><a href="/modules">Back</a></li>
        </ol>
</div>
<div class="content">
        <form action="" method="GET">
                <select name="user">
                        <#list lecturers as lecturer>
                        <option value="${lecturer.name}" <#if lecturer.name == user.name>selected</#if>>${lecturer.name}</option>
                        </#list>
                </select>
                <input type="submit" value="Change User"/>
        </form>
        <form action="" method="post"> 
                <table>
                        <thead>
                                <tr>
                                        <td>Responsibility</td>
                                        <td>Name</td>
                                        <td>Credits</td>
                                        <td>Assessment</td>
                                </tr>
                        </thead>
                        <tbody>
                                <#list modules as module>
                                <tr>   
                                        <td></td>
                                        <td><strong>${module.name}</strong></td>
                                        <td>${module.credits}</td>
                                        <td>${module.assessment}</td>
                                </tr>
                                <#list module.courses as course>
                                <tr>
                                        <#assign isResponsible = ""/>
                                        <#if user.responsibleCourses?seq_contains(course)>
                                        <#assign isResponsible = "checked"/>
                                        </#if>
                                        <td><input name="responsibility" value="${course.id}" type="checkbox" ${isResponsible}/></td>
                                        <td>${course.type}</td>
                                </tr>
                                </#list>
                                </#list>
                        </tbody>
                </table>
                <input type="submit" value="Save"/>
        </form>
</div>
</@macro.layout>
