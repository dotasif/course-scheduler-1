<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Scheduler</h1>
</div>
<div class="navigation">
        <ol class="nav nav-pills nav-stacked">
                <li><a href="/scheduler/start">Schedule Courses</a></li>
        </ol>
</div>
<div class="content">
        <#if hasSchedule>
        <#list schedules as schedule>
        <table class="table table-striped table-bordered">
                <caption>Room: ${schedule.room}</caption>
                <thead>
                        <tr>
                                <th></th>
                                <#list weekdays as weekday>
                                <th>${weekday}</th>
                                </#list>
                        </tr>
                        <#assign hour="${startHour}"?number/>
                        <#list schedule.timeRows as row>
                        <tr>
                                <td>${hour?string("00")}:00</td>
                                <#assign hour=hour+1/>
                                <#list row as cell>
                                <td><#if cell??>${cell.course}</#if></td>
                                </#list>
                        </tr>
                        </#list>
                </thead>
        </table>
        </#list>
        </#if>
        <#if reason??>
                <div class="alert alert-error">
                        <h4>Error</h4>
                        ${reason}
                </div>
        </#if>
</div>
</@macro.layout>
