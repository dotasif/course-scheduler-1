$(document).ready(function() {

  function resetInputFields(parentElement) {

    parentElement.find(":input").each(function() {
      switch(this.type) {
        case "password":
        case "select-multiple":
        case "select-one":
        case "text":
        case "textarea":
          $(this).val("");
          break;
        case "checkbox":
        case "radio":
          this.checked = false;
      }
    });
  }

  // Dynamic form expansion for the course module form
  //
  // Add another equipment requirement input
  $(".add-equipment").click(function() {
    var $last = $(this).prev();
    var $newEquipment = $equipment.clone();
    $last.after($newEquipment);
    resetInputFields($newEquipment);
    var $counter = $(this).parent().find(".equipment-count");
    $counter.val(+$counter.val() + 1);
  });

  // Add another course sub-form
  $(".add-course").click(function() {
    var $last = $(".course").eq(-1);
    var $newCourse = $course.clone(true);
    resetInputFields($newCourse);
    $last.after($newCourse);
  });

  // Make copies of the initialized forms
  $equipment = $(".equipment").eq(0).clone();
  $course = $(".course").eq(0).clone(true);

});

