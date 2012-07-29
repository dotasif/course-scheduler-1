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
        <#if feedback??>
                <#if feedback.successful>
                        <div class="alert alert-success">
                                <button class="close" data-dismiss="alert">&times;</button>
                                <h4>Success!</h4>
                                A new course schedule was created.
                        </div>
                <#else>
                        <div class="alert alert-error">
                                <button class="close" data-dismiss="alert">&times;</button>
                                <h4>Error</h4>
                                <p>Courses are not scheduleable, because of the following reason(s):</p>
                                <ol>
                                        <#if feedback.lackingRooms>
                                        <li>There are <strong>no rooms</strong> available for the courses</li>
                                        </#if>
                                        <#if feedback.timeframeIneligible>
                                        <li>The courses require more time than available through the <strong>timeframe</strong>.</li>
                                        </#if>
                                        <#if feedback.coursesLackingLecturer?size gt 0>
                                        <li>There are courses with <strong><a href="/modules/responsibilities">no responsible lecturer</a></strong>:</li>
                                        <ul>
                                                <#list feedback.coursesLackingLecturer as course>
                                                <li>${course.module.name} (${course.type})</li>
                                                </#list>
                                        </ul>
                                        </#if>
                                </ol>
                        </div>
                </#if>
        </#if>
        <#if schedules??>
        <#list schedules as schedule>
        <table class="schedule table table-striped table-bordered">
                <caption>Room: ${schedule.room}</caption>
                <thead>
                        <tr>
                                <th></th>
                                <#list timeframe.weekdays as weekday>
                                <th>${weekday}</th>
                                </#list>
                        </tr>
                        <#assign hour="${timeframe.startHour}"?number/>
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
