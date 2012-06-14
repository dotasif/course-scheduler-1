<#import "../layout.ftl" as macro>
<@macro.layout>
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
        <table class="table">
                <caption>Room: ${schedule.room}</caption>
                <thead>
                        <tr>
                                <td></td>
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
</@macro.layout>
