<#import "../layout.ftl" as macro>
<@macro.layout>
<div class="page-header">
        <h1>${requestHeadline}</h1>
</div>
<div class="content">
        <p>
        A <strong>Course module</strong> covers a set of <strong>courses</strong> which will eventually be scheduled. For instance a course module could consist of a <strong>lecture</strong> and a <strong>seminar</strong>. Please specify at least <strong>one</strong> course in this course module. If <strong>equipment</strong> will be required select the corresponding item and choose a quantity.
        </p>
        <form class="well form-horizontal" action="" method="post">
                <fieldset>
                        <h3>General</h3>
                        <div class="control-group">
                                <label class="control-label" for="name">Name:</label>
                                <div class="controls">
                                        <input type="text" name="name" value="<#if module.name??>${module.name}</#if>"/>
                                </div>
                        </div>
                        <div class="control-group">
                                <label class="control-label" for="credits">Credits:</label>
                                <div class="controls">
                                        <input class="input-small "type="text" name="credits" value="<#if module.credits != -1>${module.credits}</#if>"/>
                                </div>
                        </div>

                        <div class="control-group">
                                <label class="control-label" for="assessment">Assessment Type:</label>
                                <div class="controls">
                                        <input class="input-small" type="text" name="assessment" value="<#if module.assessment??>${module.assessment}</#if>"/>
                                </div>
                        </div>

                        <#list module.courses as course>
                        <div class="course">
                                <hr>
                                <h3>Course</h3>
                                <div class="control-group">
                                        <label class="control-label" for="type">Type:</label>
                                        <div class="controls">
                                                <input  type="text" name="type" value="<#if course.type??>${course.type}</#if>"/>
                                        </div>
                                </div>
                                <div class="control-group">
                                        <label class="control-label" for="duration">Duration:</label>
                                        <div class="controls">
                                                <input class="input-small" type="text" name="duration" value="<#if course.duration != -1>${course.duration}</#if>"/>
                                        </div>
                                </div>
                                <div class="control-group">
                                        <label class="control-label" for="count">Count:</label>
                                        <div class="controls">
                                                <input class="input-small" type="text" name="count" value="<#if course.count != -1>${course.count}</#if>">
                                        </div>
                                </div>
                                <h4>Requirements</h4>
                                <input class="equipment-count" type="hidden" name="equipment-count" value="1">
                                <#macro requirement constraints = { "" : -1 }>
                                <#list constraints?keys as constraint>
                                <div class="equipment">
                                        <div class="control-group">
                                                <label class="control-label" for="equipment">Equipment:</label>
                                                <div class="controls">
                                                        <select class="input-medium" name="equipment">
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
                                <@requirement course.equipment/>
                                <#if module.credits = -1 || !course.equipment?has_content>
                                        <@requirement/>
                                </#if>
                                <button class="btn btn-info add-equipment" type="button">Add Equipment</button>
                        </div>
                        </#list>
                </fieldset>
                <div class="form-actions">
                        <input class="btn btn-primary" type="submit" value="Submit"/>
                        <button class="btn btn-info add-course" type="button">+ Add Course</button>
                        <a href="/modules"><button class="btn btn-danger" type="button">Cancel</button></a>
                </div>
        </form>
</div>
</@macro.layout>
