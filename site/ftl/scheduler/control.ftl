<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
                <link rel="stylesheet" type="text/css" href="${css}"/>
        </head>
        <body>
                <h1>Scheduler</h1>
                <div class="menu">
                        <ol>
                                <li><a href="/">Back</a></li>
                                <li><a href="scheduler/start">Schedule Course Data</a></li>
                        </ol>
                </div>
                <div class="content">
                        <#if hasSchedule>
                        <#list schedules as schedule>
                        <table>
                                <caption>Room: ${schedule.room}</caption>
                                <thead>
                                        <tr>
                                                <td/>
                                                <#list weekdays as weekday>
                                                <td>${weekday}</td>
                                                </#list>
                                        </tr>
                                        <#assign hour="${startHour}"?number/>
                                        <#list schedule.timeRows as row>
                                        <tr>
                                                <td>${hour?string("00")}:00</td>
                                                <#assign hour=hour+1/>
                                                <#list row as cell>
                                                <td><#if cell??>${cell.course} with ${cell.lecturer.name}</#if></td>
                                                </#list>
                                        </tr>
                                        </#list>
                                </thead>
                        </table>
                        </#list>
                        <#else>
                        <p>
                        There is no course schedule yet.
                        </p>
                        </#if>
                </div>
        </body>
</html>
