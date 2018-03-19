$(function() {
	$("a").focus(function() {
		this.blur();
	});
	$("a[id^=header]").click(function() {
		var $a = $(this);
		$a.parents("#topmenu").children("li").removeClass("navon");
		$a.parents("li").addClass("navon");
		$("#leftmenu > ul").addClass("none");
		$("#menu_" + this.id.slice(7)).removeClass("none")
	});
	$("#header_index").click();
});