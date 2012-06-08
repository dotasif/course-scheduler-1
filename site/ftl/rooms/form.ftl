<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
                <h1>${requestHeadline}</h1>
                <div class="content">
                        <form id="new-room-form" action="" method="post">
                                <ol>
                                        <li>
                                        <label for="name">Number:</label>
                                        <input type="text" name="number" id="number" value="<#if room.number??>${room.number}</#if>"/>
                                        </li>
                                        <li>
                                        <label for="name">Name:</label>
                                        <input type="text" name="name" id="name" value="<#if room.name??>${room.name}</#if>"/>
                                        </li>
                                        <#list equipment.items as item>
                                        <li>
                                        <label for="item">${item}</label>
                                        <input type="text" name="item" id="item" value="<#if room.equipment[item]??>${room.equipment[item]}<#else>0</#if>"/>
                                </label>
                                </li>
                                </#list>
                                <li>
                                <input type="submit" name="submit-reason" value="Create"/>
                                </li>
                        </ol>
                        </li>
                </ol>
        </form>
</div>
</body>
</html>
