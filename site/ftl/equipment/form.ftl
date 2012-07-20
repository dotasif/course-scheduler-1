<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>Equipment Settings</h1>
</div>
<div class="content">
        <p>Define the equipment available in your department, it will be available in the room and course creation.</p>
        <form class="well" action="" method="post">
                <fieldset>
                        <label for="equipment">One item per line</label>
                        <textarea rows="7" name="equipment"><#compress><#list equipment.items as item>${item}
                        </#list></#compress></textarea>
                </fieldset>
                <div class="form-actions">
                        <input class="btn btn-primary" type="submit" value="Submit"/>
                </div>
        </form>
</div>
</@macro.layout>
