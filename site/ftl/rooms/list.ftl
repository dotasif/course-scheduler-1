<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
                <link rel="stylesheet" type="text/css" href="${css}"/>
        </head>
        <body>
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
</body>
</html>
