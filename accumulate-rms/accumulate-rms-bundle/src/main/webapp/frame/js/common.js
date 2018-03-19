$(function() {
	var grid = $(".listTable");
	if (grid.length && $.fn.colorize) {
		var param = {
			altColor : '#F2F9FD',
			hoverColor : '#FFFFBB',
			hiliteColor : '#FFFFBB',
			banRows : [],
			oneClick : true
		};
		var m = /({.*})/.exec(grid.attr("class"));
		if (m)
			$.extend(param, $.parseJSON(m[1]));
		grid.colorize(param);
	}

	$(".wwFormTable :submit,.editGrid :submit").before($("<input>", {
		type : "button",
		value : "back"
	}).click(function() {
		history.back();
	}));

	$(".editGrid dt i").html("*");
});
