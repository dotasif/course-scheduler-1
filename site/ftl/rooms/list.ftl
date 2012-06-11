<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>Rooms</h1>
<div class="navigation">
        <ol>
                <li><a href="/">Back</a></li>
                <li><a href="rooms/new">Add new room</a></li>
        </ol>
</div>
<div class="content">
        <table>
                <thead>
                        <tr>
                                <td>Number</td>
                                <td>Name</td>
                                <td>Delete?</td>
                                <td>Edit?</td>
                        </tr>
                </thead>
                <tbody>
                        <#list rooms as room>
                        <tr>   
                                <td>${room.number}</td>
                                <td>${room.name}</td>
                                <td><a href="rooms/delete/${room.id}">X</a></td>
                                <td><a href="rooms/edit/${room.id}">O</a></td>
                        </a>
                </tr>
                </#list>
        </tbody>
</table>
        </div>
        </@macro.layout>
