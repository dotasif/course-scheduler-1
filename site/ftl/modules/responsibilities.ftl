<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Course Responsibilities</h1>
</div>
<div class="content">
        <p>Assign course responsibilities to a certain user.</p>
        <form class="well form-inline" action="" method="GET">
                <select name="user">
                        <option>Choose a lecturer...</option>
                        <#list lecturers as lecturer>
                        <#if (user?? && user.login == lecturer.login)>
                                <#assign selected = "selected"/>
                        <#else>
                                <#assign selected = ""/>
                        </#if>
                        <option value="${lecturer.login}" ${selected}>${lecturer.name}</option>
                        </#list>
                </select>
                <input class="btn" type="submit" value="Change User"/>
        </form>
        <#if user??>
                <form action="" method="post"> 
                        <table class="table">
                                <thead>
                                        <tr>
                                                <th>Responsibility</th>
                                                <th>Name</th>
                                                <th>Credits</th>
                                                <th>Assessment</th>
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
                                                <td></td>
                                                <td></td>
                                        </tr>
                                        </#list>
                                        </#list>
                                </tbody>
                        </table>
                        <a href="/modules"><button class="btn">Back</button></a>
                        <input type="submit" class="btn btn-info" value="Save"/>
                </form>
        </#if>
</div>
</@macro.layout>
