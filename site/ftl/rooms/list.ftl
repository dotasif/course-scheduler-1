<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Rooms</h1>
</div>
<div class="navigation">
        <ol class="nav nav-pills nav-stacked">
                <li><a href="rooms/new">New Room</a></li>
        </ol>
</div>
<div class="content">
        <table class="table">
                <thead>
                        <tr>
                                <th>Number</th>
                                <th>Name</th>
                                <th>Delete?</th>
                                <th>Edit?</th>
                        </tr>
                </thead>
                <tbody>
                        <#list rooms as room>
                        <tr>   
                                <td>${room.number}</td>
                                <td>${room.name}</td>
                                <td><a href="rooms/delete/${room.id}"><button class="btn btn-danger">Delete</button></a></td>
                                <td><a href="rooms/edit/${room.id}"><button class="btn btn-info">Edit</button></a></td>
                        </a>
                </tr>
                </#list>
        </tbody>
</table>
        </div>
        </@macro.layout>
