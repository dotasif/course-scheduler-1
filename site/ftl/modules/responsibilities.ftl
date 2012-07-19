<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>Course Responsibilities</h1>
<div class="content">
        <p>Assign course responsibilities to a certain user.</p>
        <form class="well form-inline" action="" method="GET">
                <select name="user">
                        <#list lecturers as lecturer>
                        <option value="${lecturer.name}" <#if lecturer.name == user.name>selected</#if>>${lecturer.name}</option>
                        </#list>
                </select>
                <input class="btn" type="submit" value="Change User"/>
        </form>
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
</div>
</@macro.layout>
