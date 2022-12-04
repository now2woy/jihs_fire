
$(document).ready(function () {
	$BODY = $('body');
	$MENU_TOGGLE = $('#menu_toggle');
	$SIDEBAR_MENU = $('#sidebar-menu');
	$SIDEBAR_FOOTER = $('.sidebar-footer');
	$LEFT_COL = $('.left_col');
	$RIGHT_COL = $('.right_col');
	$NAV_MENU = $('.nav_menu');
	$FOOTER = $('footer');
	
	$('.collapse-link').on('click', function () {
		var $BOX_PANEL = $(this).closest('.x_panel'), $ICON = $(this).find('i'), $BOX_CONTENT = $BOX_PANEL.find('.x_content');
		
		// fix for some div with hardcoded fix class
		if ($BOX_PANEL.attr('style')) {
			$BOX_CONTENT.slideToggle(200, function () {
				$BOX_PANEL.removeAttr('style');
			});
		} else {
			$BOX_CONTENT.slideToggle(200);
			$BOX_PANEL.css('height', 'auto');
		}
		
		$ICON.toggleClass('fa-chevron-up fa-chevron-down');
	});
	
	var openUpMenu = function () {
		$SIDEBAR_MENU.find('li').removeClass('active active-sm');
		$SIDEBAR_MENU.find('li ul').slideUp();
	}
	
	// 상단 메뉴접기 버튼
	$MENU_TOGGLE.on('click', function () {
		if ($BODY.hasClass('nav-md')) {
			$SIDEBAR_MENU.find('li.active ul').hide();
			$SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
		} else {
			$SIDEBAR_MENU.find('li.active-sm ul').show();
			$SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
		}
		
		$BODY.toggleClass('nav-md nav-sm');
		
		//setContentHeight();
		
		$('.dataTable').each(function () { $(this).dataTable().fnDraw(); });
	});
	
	// 왼쪽 메뉴 접기
	$SIDEBAR_MENU.find('a').on('click', function (ev) {
		var $li = $(this).parent();
		
		if ($li.is('.active')) {
			$li.removeClass('active active-sm');
			$('ul:first', $li).slideUp(function () {
				//setContentHeight();
			});
		} else {
			// prevent closing menu if we are on child menu
			if (!$li.parent().is('.child_menu')) {
				openUpMenu();
			} else {
				if ($BODY.is('nav-sm')) {
					if (!$li.parent().is('child_menu')) {
						openUpMenu();
					}
				}
			}
		
			$li.addClass('active');
			
			$('ul:first', $li).slideDown(function () {
				//setContentHeight();
			});
		}
	});
});