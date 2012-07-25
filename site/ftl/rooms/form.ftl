<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>${requestHeadline}</h1>
</div>
<div class="content">
        <form class="well form-horizontal" action="" method="post">
                <fieldset>
                        <h3>General</h3>
                        <div class="control-group">
                                <label class="control-label" for="name">Number:</label>
                                <div class="controls">
                                        <input class="input-small" type="text" name="number" id="number" value="<#if room.number??>${room.number}</#if>"/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="name">Name:</label>
                                <div class="controls">
                                        <input type="text" name="name" id="name" value="<#if room.name??>${room.name}</#if>"/>
                                </div>
                        </div>
                        <h3>Features</h3>
                        <#macro feature constraints = { "" : -1 }>
                        <#list constraints?keys as constraint>
                        <div class="equipment">
                                <div class="control-group">
                                        <label class="control-label" for="item">Equipment:</label>
                                        <div class="controls">
                                                <select class="input-medium" name="item">
                                                        <option></option>
                                                        <#list equipment.items as item>
                                                        <#if item = constraint && constraints[constraint] != -1>
                                                                <option value="${item}" selected="selected">${item}</option>
                                                        <#else>
                                                                <option value="${item}">${item}</option>
                                                        </#if>
                                                        </#list>
                                                </select>
                                        </div>
                                </div>
                                <div class="control-group">
                                        <div class="controls docs-input-sized">
                                                <#if constraints[constraint] != -1>
                                                        <#assign value = "value=\"${constraints[constraint]}\"">
                                                <#else>
                                                        <#assign value = "">
                                                </#if>
                                                <input class="input-mini" type="text" name="quantity" placeholder="Quantity" ${value}>
                                        </div>
                                </div>
                        </div>
                        </#list>
                        </#macro>
                        <@feature room.equipment/>
                        <#if !room.number?? || !room.equipment?has_content>
                                <@feature/>
                        </#if>
                        <button class="btn btn-info add-equipment" type="button">Add Equipment</button>
                </fieldset>
                <div class="form-actions">
                        <input class="btn btn-primary" type="submit" value="Submit">
                </div>
        </ol>
        </li>
</ol>
        </form>
</div>
</@macro.layout>
