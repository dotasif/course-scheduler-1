<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
                <link rel="stylesheet" type="text/css" href="${css}"/>
        </head>
        <body>
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
</body>
</html>
