<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
                <h1>Course Modules</h1>
                <div class="navigation">
                        <ol>
                                <li><a href="add">Add new course module</a></li>
                        </ol>
                </div>
                <div class="content">
                        <table>
                                <thead>
                                        <tr>
                                                <td>ID</td>
                                                <td>Name</td>
                                                <td>Credits</td>
                                                <td>Assessment</td>
                                        </tr>
                                </thead>
                                <tbody>
                                        <#list modules as module>
                                        <tr>
                                                <td>${module.id}</td>
                                                <td>${module.name}</td>
                                                <td>${module.credits}</td>
                                                <td>${module.assessment}</td>
                                        </tr>
                                        </#list>
                                </tbody>
                        </table>
                </div>
        </body>
</html>
