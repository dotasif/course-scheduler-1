<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
                <h1>New Course Module</h1>
                <div class="content">
                        <form id="new-module-form" action="" method="post">
                                <ol>
                                        <li>
                                        <label for="name">Name:</label>
                                        <input type="text" name="name" id="name" value=""/>
                                        </li>
                                        <li>
                                        <label for="credits">Credits:</label>
                                        <input type="text" name="credits" id="credits" value=""/>
                                        </li>
                                        <li>
                                        <label for="assessment">Assessment:</label>
                                        <input type="text" name="assessment" id="assessment" value=""/>
                                        </li>
                                        <li>Courses:
                                        <ol>
                                                <li>
                                                <ol>
                                                        <#if courses??>
                                                        <#list courses as course>
                                                        <li>
                                                        <label for="course-name">Name:</label>
                                                        <input type="text" name="course-name" id="course-name" value="${course.name}"/>
                                                        </li>
                                                        <li>
                                                        <label for="course-duration">Duration:</label>
                                                        <input type="text" name="course-duration" id="course-duration" value="${course.duration}"/>
                                                        </li>
                                                        <li>
                                                        <label for="course-count">Count:</label>
                                                        <input type="text" name="course-count" id="course-count" value="${course.count}"/>
                                                        </li>
                                                        <li>
                                                        <label for="course-type">Type:</label>
                                                        <input type="text" name="course-type" id="course-type" value="${course.type}"/>
                                                        </li>
                                                        </#list>
                                                        </#if>
                                                        <li>
                                                        <label for="course-name">Name:</label>
                                                        <input type="text" name="course-name" id="course-name" value=""/>
                                                        </li>
                                                        <li>
                                                        <label for="course-duration">Duration:</label>
                                                        <input type="text" name="course-duration" id="course-duration" value=""/>
                                                        </li>
                                                        <li>
                                                        <label for="course-count">Count:</label>
                                                        <input type="text" name="course-count" id="course-count" value=""/>
                                                        </li>
                                                        <li>
                                                        <label for="course-type">Type:</label>
                                                        <input type="text" name="course-type" id="course-type" value=""/>
                                                        </li>
                                                </ol>
                                                </li>
                                                <li>
                                                <input type="submit" name="submit-reason" value="New Course"/>
                                                </li>
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
