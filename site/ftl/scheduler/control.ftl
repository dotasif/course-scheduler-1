<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Scheduler</h1>
</div>
<div class="navigation">
        <ol class="nav nav-pills nav-stacked">
                <li><a href="/scheduler/start">Schedule Courses</a></li>
                <li><a href="/scheduler/delete">Delete Course Schedule</a></li>
        </ol>
</div>
<div class="content">
        <#if failed??>
                <div class="alert alert-error">
                        <button class="close" data-dismiss="alert">&times;</button>
                        <h4>Error</h4>
                        Courses are not scheduleable.
                </div>
        </#if>
        <#if successful??>
                <div class="alert alert-success">
                        <button class="close" data-dismiss="alert">&times;</button>
                        <h4>Success!</h4>
                        A new course schedule was created.
                </div>
        </#if>
        <#if hasSchedule>
        <#list schedules as schedule>
        <table class="schedule table table-striped table-bordered">
                <caption>Room: ${schedule.room}</caption>
                <thead>
                        <tr>
                                <th></th>
                                <#list weekdays as weekday>
                                <th>${weekday}</th>
                                </#list>
                        </tr>
                        <#assign hour="${startHour}"?number/>
                        <#assign i = 0>
                        <#list schedule.timeRows as row>
                        <tr>
                                <td>${hour?string("00")}:00</td>
                                <#assign hour=hour+1/>
                                <#list row as cell>
                                <#if cell??>
                                        <#if i == 0>
                                        <#assign i = cell.course.duration - 1>
                                        <td class="course" rowspan="${cell.course.duration}"><a rel="tooltip" data-content="Type: ${cell.course.type}<br>Lecturer: ${cell.lecturer.name}" title="${cell.course.module.name}">${cell.course.module.name}</a></td>
                                        <#else>
                                        <#assign i = i - 1>
                                        </#if>
                                <#else>
                                        <td></td>
                                </#if>
                                </#list>
                        </tr>
                        </#list>
                </thead>
        </table>
        </#list>
        </#if>
</div>
</@macro.layout>
