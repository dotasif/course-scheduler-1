<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
                <h1>Scheduler</h1>
                <div class="menu">
                        <ol>
                                <li><a href="scheduler/start">Schedule Course Data</a></li>
                        </ol>
                </div>
                <div class="content">
                        <#if hasSchedule>
                        <table>
                                <caption>Room: ${room}</caption>
                                <thead>
                                        <tr>
                                                <#list weekdays as weekday>
                                                <td>${weekday}</td>
                                                </#list>
                                        </tr>
                                </thead>
                        </table>
                        <#else>
                        <p>
                        There is no course schedule yet.
                        </p>
                        </#if>
                </div>
</body>
</html>
