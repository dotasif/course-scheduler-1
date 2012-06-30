$(document).ready(function() {

  // Make copies of the initialized forms
  $requirement = $(".requirement").clone();
  $course = $(".course").clone();

  // Dynamic form expansion for the course module form
  $(".add-equipment").click(function() {
    var $last = $(this).prev();
    $last.after($requirement.clone());
  });

  $(".add-course").click(function() {
    var $last = $(".course").eq(-1);
    $last.after($course);
  });

});
