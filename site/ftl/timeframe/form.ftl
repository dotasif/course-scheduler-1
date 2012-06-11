<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>Timeframe Setting</h1>
<div class="content">
        <form id="timeframe-form" action="" method="post">
                <ol>
                        <li>
                        <label for="days">Number of Days:</label>
                        <input type="text" name="days" id="days" value="${days?c}"/>
                        </li>
                        <li>
                        <label for="timeSlots">Number of Time Slots:</label>
                        <input type="text" name="timeSlots" id="timeSlots" value="${timeSlots?c}"/>
                        </li>
                        <li>
                        <label for="startHours">Start Hour:</label>
                        <input type="text" name="startHour" value="${startHour?c}"/>
                        </li>
                        <li>
                        <label for="weekdays">Weekdays:</label>
                        <textarea name="weekdays"><#compress><#list weekdays as weekday>${weekday}
                                </#list></#compress></textarea>
                                </li>
                                <li>
                                <input type="submit" name="submit" value="Submit"/>
                                </li>
                        </ol>
                        </li>
                </ol>
        </form>
</div>
</@macro.layout>
