<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>Course Modules</h1>
<div class="navigation">
        <ol>
                <li><a href="/">Back</a></li>
                <li><a href="modules/new">Add new course module</a></li>
                <li><a href="modules/responsibilities">Assign Lecture Responsibilities</a></li>
        </ol>
</div>
<div class="content">
        <table>
                <thead>
                        <tr>
                                <td><strong>Name</strong></td>
                                <td><strong>Credits</strong></td>
                                <td><strong>Assessment</strong></td>
                                <td><strong>Delete</strong></td>
                                <td><strong>Edit</strong></td>
                        </tr>
                </thead>
                <tbody>
                        <#list modules as module>
                        <tr>   
                                <td><a href="modules/${module.id}">${module.name}</a></td>
                                <td>${module.credits}</td>
                                <td>${module.assessment}</td>
                                <td><a href="modules/delete/${module.id}">X</a></td>
                                <td><a href="modules/edit/${module.id}">O</a></td>
                        </tr>
                        </#list>
                </tbody>
        </table>
</div>
</@macro.layout>
