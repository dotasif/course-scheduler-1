<#import "../layout.ftl" as macro>
<@macro.layout>
<h1>${name}</h1>
<div class="navigation">
        <ol>
                <li><a href="/modules">Back</a></li>
        </ol>
</div>
<div class="content">
        <table>
                <thead>
                        <tr>
                                <td>Type</td>
                                <td>Duration</td>
                                <td>Count</td>
                        </tr>
                </thead>
                <tbody>
                        <#list courses as course>
                        <tr>   
                                <td>${course.type}</td>
                                <td>${course.duration}</td>
                                <td>${course.count}</td>
                        </a>
                </tr>
                </#list>
        </tbody>
</table>
</div>  
</@macro.layout>
