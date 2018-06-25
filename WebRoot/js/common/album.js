
jQuery(document).ready(function () {
	jQuery("dl#tabs3").addClass("enabled").timetabs({defaultIndex:0, interval:2500, continueOnMouseLeave:true, animated:"curtain", animationSpeed:500});

 // animation preview
	jQuery("input[name=animation]").click(function () {
		$this = jQuery(this);
		jQuery.fn.timetabs.switchanimation($this.val());
	});
});

