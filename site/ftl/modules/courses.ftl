<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
                <h1>${name}</h1>
                <div class="navigation">
                        <ol>
                                <li><a href="/modules">Back</a></li>
                        </ol>
                </div>
                <div class="content">
                        <table>
                                <thead>
                                        <tr>
                                                <td>Type</td>
                                                <td>Duration</td>
                                                <td>Count</td>
                                        </tr>
                                </thead>
                                <tbody>
                                        <#list courses as course>
                                        <tr>   
                                                <td>${course.type}</td>
                                                <td>${course.duration}</td>
                                                <td>${course.count}</td>
                                        </a>
                                </tr>
                                </#list>
                        </tbody>
                </table>
        </div>
</body>
</html>
