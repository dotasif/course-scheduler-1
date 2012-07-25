<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Timeframe Setting</h1>
</div>
<div class="content">
        <form class="well form-horizontal" action="" method="post">
                <fieldset>
                        <div class="control-group">
                                <label class="control-label" for="days">Number of Days:</label>
                                <div class="controls">
                                        <input type="text" name="days" id="days" value="${days?c}"/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="timeSlots">Number of Time Slots:</label>
                                <div class="controls">
                                        <input type="text" name="timeSlots" id="timeSlots" value="${timeSlots?c}"/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="startHours">Start Hour:</label>
                                <div class="controls">
                                        <input type="text" name="startHour" value="${startHour?c}"/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="weekdays">Weekdays:</label>
                                <div class="controls">
                                        <textarea rows="5" name="weekdays"><#compress><#list weekdays as weekday>${weekday}</#list></#compress></textarea>
                                </div>
                        </div>
                </fieldset>
                <div class="form-actions">
                        <input class="btn btn-primary" type="submit" name="submit" value="Submit"/>
                </div>
        </form>
</div>
</@macro.layout>
