$(document).ready(function() {

  // Dynamic form expansion for the course module form
  //
  // Add another equipment requirement input
  $(".add-equipment").click(function() {
    var $last = $(this).prev();
    $last.after($requirement.clone());
  });

  // Add another course sub-form
  $(".add-course").click(function() {
    var $last = $(".course").eq(-1);
    $last.after($course.clone(true));
  });

  // Make copies of the initialized forms
  $requirement = $(".requirement").clone();
  $course = $(".course").clone(true);

});
