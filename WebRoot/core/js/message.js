/*
  消息提示:
  options
  msg        : 内容,缺省为"";
  title      : 标题,缺省为"提示";
  closedelay : 自动关闭时间,缺省为10;
  autoclose  : 是否自动关闭,缺省为true;
  width      : 宽度,缺省为300;
  height     : 高度,缺省为200;
*/
( function( $ )
{
	var _options = null;
	var _delay   = 0;
	var id = "moocore_message_" + Math.ceil( Math.random() * 10000 );
	
	$.MessageSettings = function( options )
	{
		_options = getOptions( options );
	}

	$.Message = function( options )
	{
		_options = getOptions( options );
		createMessage( _options );
		element = $( "#" + id );
		element.show();
		element.css( "left" , ( $( document.documentElement ).width()  - element.width()  ) / 2 );
		element.css( "top"  , ( $( document.documentElement ).height() - element.height() ) / 2 );
		if( _options.autoclose == true )
		{
			_delay = _options.closedelay;
			setTimeout( "$.MessageHide();" , 1000 );
		}
		element.find( "#" + id + "_titlebar" ).drag( element );
	}

	$.MessageHide = function( directClose )
	{
		if( directClose == true )
		{
			$( "#" + id ).remove();
		}
		else
		{
			if( _options.autoclose == true )
			{
				var eDelay = $( "#" + id + "_delay" );
				if( _delay == 1 )
				{
					eDelay.html( _options.closedelay );
					$( "#" + id ).remove();
				}
				else
				{
					_delay--;
					eDelay.html( _delay );
					setTimeout( "$.MessageHide();" , 1000 );
				}
			}
			else
			{
				$( "#" + id ).remove();
			}
		}
	}
	
	function createMessage( options )
	{
		var s = "";
		s += "<div id='" + id + "' style='display:none;position:absolute;width:" + options.width + "px;height:" + options.height + "px;z-index:2000;'>";
		s += "<table cellspacing='0' cellpadding='0' border='0' style='width:" + options.width + "px;height:" + options.height + "px;'>";
		s += "<tr style='height:13px;'>";
		s += "<td style='width:19px;' class='moocore_bubble_top_left'></td><td class='moocore_bubble_top'></td><td style='width:19px' class='moocore_bubble_top_right'></td>";
		s += "<tr valign='top'>";
		s += "<td style='width:19px;' class='moocore_bubble_left'></td>";
		s += "<td style='font-size:12px;background-color:white;'>";
		
		s += "<div id='" + id + "_titlebar' style='height:16px;'>";
		s += "<div class='moocore_bubble_title_tip_image' style='float:left;'></div>";
		s += "<div style='float:left;margin-left:8px;line-height:18px;margin-right:8px;font-family:Verdana'><b id='" + id + "_title'>" + options.title + ( options.autoclose == true ? "(<label id='" + id + "_delay'>" + options.closedelay + "</label>秒后自动关闭)" : "" ) + "</b></div>";
		s += "<div class='moocore_bubble_title_close moocore_bubble_title_close_normal'";
		s += " onmouseup='$.MessageHide( true )'";
		s += " onmousedown='$( this ).removeClass( \"moocore_bubble_title_close_normal moocore_bubble_title_close_hover\" );$( this ).addClass( \"moocore_bubble_title_close_down\" );'";
		s += " onmouseover='$( this ).removeClass( \"moocore_bubble_title_close_normal moocore_bubble_title_close_down\" );$( this ).addClass( \"moocore_bubble_title_close_hover\" );'";
		s += " onmouseout='$( this ).removeClass( \"moocore_bubble_title_close_hover moocore_bubble_title_close_down\" );$( this ).addClass( \"moocore_bubble_title_close_normal\" );'";
		s += "></div>";
		s += "</div>";
		s += "<div style='padding-top:10px;text-align:left;'>";
		s += "<textarea id='" + id + "_text' style='overflow:auto;width:" + ( options.width - 40 ) + "px;height:" + ( options.height - 60 ) + "px;border:1px solid #A0C7FF;padding:3px;font-size:12px;' readonly='readonly'>";
		s += options.msg;
		s += "</textarea>";
		s += "</div>";
				
		s += "</td>";
		s += "<td style='width:19px' class='moocore_bubble_right'></td>";
		s += "<tr style='height:10px;'>";
		s += "<td style='width:19px;' class='moocore_bubble_bottom_left'></td><td class='moocore_bubble_bottom'></td><td style='width:19px' class='moocore_bubble_bottom_right'></td>";
		s += "</table>";
		s += "</div>";
		$( document.body ).append( s );
	}
	
	function getOptions( options )
	{
		return $.extend( {
			msg        : ""     ,
			title      : "提示" ,
			closedelay : 10     ,
			autoclose  : false  ,
			width      : 300    ,
			height     : 200
		} , options || {} );
	}

})( jQuery );