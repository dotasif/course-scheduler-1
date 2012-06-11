<#macro layout>
<!DOCTYPE html>
<html>
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
                <title>${title}</title>
                <link rel="stylesheet" type="text/css" href="${css}">
        </head>
        <body>
                <div class="navbar">
                        <div class="navbar-inner">
                                <div class="container">
                                        <a class="brand">Course Scheduler</a>
                                </div>
                        </div>
                </div>
                <div class="container">
                        <#nested/>
                </div>
        </body>
</html>
</#macro>
