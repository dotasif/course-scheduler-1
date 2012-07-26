<#macro layout>
<!DOCTYPE html>
<html lang="en">
        <head profile="http://www.w3.org/2005/10/profile">
                <link rel="icon" type="image/ico" href="/resources/img/favicon.ico">
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
                <title>${title}</title>

                <!-- Stylesheets -->
                <link rel="stylesheet" type="text/css" href="${bootstrap}">
                <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-extension.css">

                <!-- Scripts -->  
                <script type="text/javascript" src="${jquery}"></script>
                <script type="text/javascript" src="/resources/js/vendor/bootstrap.js"></script>
                <script type="text/javascript" src="/resources/js/forms.js"></script>
                <script type="text/javascript" src="/resources/js/scheduler.js"></script>

        </head>
        <body>
                <div class="navbar">
                        <div class="navbar-inner">
                                <div class="container">
                                        <a class="brand" href="/">Course Scheduler</a>
                                        <div class="nav-collapse">
                                                <ul class="nav">
                                                        <li><a href="/modules">Course Modules</a></li>
                                                        <li><a href="/rooms">Rooms</a></li>
                                                        <li><a href="/equipment">Equipment</a></li>
                                                        <li><a href="/users">Users</a></li>
                                                        <li><a href="/timeframe">Timeframe</a></li>
                                                        <li><a href="/scheduler">Scheduler</a></li>
                                                </ul>
                                                <div class="pull-right">
                                                        <ul class="nav">
                                                                <li><a href="/logout">Logout</a></li>
                                                        </ul>
                                                </div>
                                        </div>
                                </div>
                        </div>
                </div>
                <div class="container">
                        <#nested/>
                </div>
        </body>
</html>
</#macro>
