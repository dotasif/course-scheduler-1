<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Users</h1>
</div>
<div class="navigation">
        <ol class="nav nav-pills nav-stacked">
                <li><a href="users/new">New User</a></li>
        </ol>
</div>
<div class="content">
        <table class="table">
                <thead>
                        <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Student?</th>
                                <th>Lecturer?</th>
                                <th>Delete?</th>
                                <th>Edit?</th>
                        </tr>
                </thead>
                <tbody>
                        <#list users as user>
                        <tr>   
                                <td>${user.name}</td>
                                <td>${user.email}</td>
                                <td>${user.student?string("Yes","No")}</td>
                                <td>${user.lecturer?string("Yes","No")}</td>
                                <td><a href="users/delete/${user.login}"><button class="btn btn-danger">Delete</button></a></td>
                                <td><a href="users/edit/${user.login}"><button class="btn btn-info">Edit</button></a></td>
                        </a>
                </tr>
                </#list>
        </tbody>
</table>
        </div>
        </@macro.layout>
