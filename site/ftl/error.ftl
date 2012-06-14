<#import "./layout.ftl" as macro>
<@macro.layout>
        <div class="content">
                <h1>Error</h1>
                <ol>
                        <li><a href="/">Back</a></li>
                </ol>
                <p>
                ${reason}
                </p>
        </div>
</@macro.layout>
