
$(function()
{
    $(".site-nav-links li").hover(function () 
	{
	    $(this).css("background","#0EAAE9");
	},
	function () 
	{
	    $(this).css("background","#6ECFF5");
	}
	);

	$(".site-nav-links li").click(function()
	{
		$(this).addClass("on");
		$(".on").removeClass("on");
		
	});
});
