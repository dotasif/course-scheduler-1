<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Course Modules</h1>
</div>
<div class="navigation">
        <ol class="nav nav-pills nav-stacked">
                <li><a href="modules/new">New Course Module</a></li>
                <li><a href="modules/responsibilities">Lecture Responsibilities</a></li>
        </ol>
</div>
<div class="content">
        <table class="table">
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
                                <td><a href="modules/delete/${module.id}"><button class="btn btn-danger">Delete</button></a></td>
                                <td><a href="modules/edit/${module.id}"><button class="btn btn-info">Edit</button></a></td>
                        </tr>
                        </#list>
                </tbody>
        </table>
</div>
</@macro.layout>
