<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>Users</h1>
<div class="navigation">
        <ol>
                <li><a href="/">Back</a></li>
                <li><a href="users/new">Add New User</a></li>
        </ol>
</div>
<div class="content">
        <table>
                <thead>
                        <tr>
                                <td>Name</td>
                                <td>Email</td>
                                <td>Student?</td>
                                <td>Lecturer?</td>
                                <td>Delete?</td>
                                <td>Edit?</td>
                        </tr>
                </thead>
                <tbody>
                        <#list users as user>
                        <tr>   
                                <td>${user.name}</td>
                                <td>${user.email}</td>
                                <td>${user.student?string("Yes","No")}</td>
                                <td>${user.lecturer?string("Yes","No")}</td>
                                <td><a href="users/delete/${user.name}">X</a></td>
                                <td><a href="users/edit/${user.name}">O</a></td>
                        </a>
                </tr>
                </#list>
        </tbody>
</table>
        </div>
        </@macro.layout>
