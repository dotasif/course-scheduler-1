<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
                <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                <title>${title}</title>
        </head>
        <body>
                <h1>Timeframe Setting</h1>
                <div class="content">
                        <form id="timeframe-form" action="" method="post">
                                <ol>
                                        <li>
                                        <label for="days">Number of Days:</label>
                                        <input type="text" name="days" id="days" value="${days?c}"/>
                                        </li>
                                        <li>
                                        <label for="timeSlots">Number of Time Slots:</label>
                                        <input type="text" name="timeSlots" id="timeSlots" value="${timeSlots?c}"/>
                                        </li>
                                        <li>
                                        <label for="weekdays">Weekdays:</label>
                                        <textarea name="weekdays"><#compress><#list weekdays as weekday>${weekday}
                                        </#list></#compress></textarea>
                                        </li>
                                        <li>
                                        <input type="submit" name="submit" value="Submit"/>
                                        </li>
                                </ol>
                                </li>
                        </ol>
                </form>
        </div>
</body>
</html>