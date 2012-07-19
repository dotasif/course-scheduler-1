<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>${module.name}</h1>
<div class="content">
        <p>Course Module Details</p>
        <table class="table">
                <thead>
                        <tr>
                                <td><strong>Type</strong></td>
                                <td><strong>Duration</strong></td>
                                <td><strong>Count</strong></td>
                                <td><strong>Requirements</strong></td>
                        </tr>
                </thead>
                <tbody>
                        <#list module.courses as course>
                        <tr>   
                                <td>${course.type}</td>
                                <td>${course.duration}</td>
                                <td>${course.count}</td>
                                <td>
                                        <#list course.equipment?keys as constraint>
                                        ${constraint} (${course.equipment[constraint]}x)
                                        </#list>
                                </td>
                        </a>
                </tr>
                </#list>
        </tbody>
</table>
<a href="/modules"><button class="btn">Back</button></a>
</div>  
</@macro.layout>
