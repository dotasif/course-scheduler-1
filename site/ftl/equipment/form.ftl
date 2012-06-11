<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>Equipment Setting</h1>
<div class="content">
        <form action="" method="post">
                <ol>
                        <li>
                        <label for="equipment">Equipment</label>
                        <textarea name="equipment"><#compress><#list equipment.items as item>${item}
                                </#list></#compress></textarea>
                                </li>
                                <li>
                                <input type="submit" value="Submit"/>
                                </li>
                        </ol>
                        </li>
                </ol>
        </form>
</div>
</@macro.layout>
